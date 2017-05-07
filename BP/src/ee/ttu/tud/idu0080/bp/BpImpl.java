
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package ee.ttu.tud.idu0080.bp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;

import org.example.pakkumus.Pakkumus;
import org.example.pakkumus.PakkumusParing;
import org.example.pakkumus.PakkumusVastus;
import org.example.pakkumus.Pakkumus_Service;
import org.example.pakkumus.TellimusType;

import ee.ttu.tud.idu0080.kuller.Kuller;
import ee.ttu.tud.idu0080.kuller.Kuller_Service;
import ee.ttu.tud.idu0080.kuller.Kuller_Type;
import ee.ttu.tud.idu0080.transport.Transport;
import ee.ttu.tud.idu0080.transport.Transport_Service;
import logging.BusinessLogger;
import ttu.idu0080.order.server.Order;
import ttu.idu0080.order.server.OrderService;
import ttu.idu0080.order.server.OrderServiceService;
import ttu.idu0080.order.server.OrderShipmentService;
import ttu.idu0080.order.server.OrderShipmentServiceService;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2017-04-21T14:29:08.439+03:00
 * Generated source version: 2.7.18
 * 
 */

@javax.jws.WebService(
                      serviceName = "bp",
                      portName = "bpSOAP",
                      targetNamespace = "http://idu0080.tud.ttu.ee/bp/",
                      wsdlLocation = "file:/C:/Projektid/IDU0080_4/BP/WebContent/bp.wsdl",
                      endpointInterface = "ee.ttu.tud.idu0080.bp.Bp")
                      
public class BpImpl implements Bp {
	
    private static final QName ORDER_SERVICE_NAME = new QName("http://server.order.idu0080.ttu/", "OrderServiceService");
    private static final QName KULLER_SERVICE_NAME = new QName("http://idu0080.tud.ttu.ee/kuller/", "kuller");
    private static final QName PAKKUMUS_SERVICE_NAME = new QName("http://www.example.org/pakkumus/", "pakkumus");
    private static final QName TRANSPORT_SERVICE_NAME = new QName("http://idu0080.tud.ttu.ee/transport/", "transport");
    private static final QName ORDER_SHIPMENT_SERVICE_NAME = new QName("http://server.order.idu0080.ttu/", "OrderShipmentServiceService");
    
    /* (non-Javadoc)
     * @see ee.ttu.tud.idu0080.bp.Bp#tellimuseEsitamine(ee.ttu.tud.idu0080.bp.MinuTellimus  parameters )*
     */
    public MinuTellimuseVastus tellimuseEsitamine(MinuTellimus parameters) { 
    	BusinessLogger.log("Start BusinessProcess");
        try {                         
            //Samm 1, tellimuse andmete küsimine tund.ttu.ee lehelt
            OrderServiceService orderSs = new OrderServiceService(new URL("http://localhost:8080/BPServer/services/OrderServicePort?wsdl"), ORDER_SERVICE_NAME);
            OrderService orderPort = orderSs.getOrderServicePort();
                        
            int tellimusId = parameters.getId().intValue();
            ttu.idu0080.order.server.Order tellimusVastus = orderPort.getOrdersByOrderId(tellimusId);
            BusinessLogger.log("Order: [" + tellimusId + "] Price: [" + tellimusVastus.getPriceTotal() + "]");

            //2. samm, enda kirjutatud veebiteenuselt kullerite andmete küsimine
            Kuller_Service kullerSs = new Kuller_Service(new URL("http://localhost:8080/BPTeenused/services/kullerSOAP?wsdl"), KULLER_SERVICE_NAME);
            Kuller kullerPort = kullerSs.getKullerSOAP();
            ee.ttu.tud.idu0080.kuller.KulleridVastus kullerid = kullerPort.getKullerid();
            
            if (kullerid.getKuller().isEmpty()) {
				MinuTellimuseVastus result = new MinuTellimuseVastus();
				result.setTeade("No couriers available.");
				BusinessLogger.log("End BusinessProcess ");
				return result;
			}
            
            BusinessLogger.log("Received " + kullerid.getKuller().size() + " offers");
            
            //3. samm, pakkumise küsimine
            Pakkumus_Service pakkumusSs = new Pakkumus_Service(new URL("http://localhost:8080/BPTeenused/services/pakkumusSOAP?wsdl"), PAKKUMUS_SERVICE_NAME);
            Pakkumus pakkumusPort = pakkumusSs.getPakkumusSOAP();

            class Offer {
                private Double goodness;
                private PakkumusVastus pakkumusVastus;
                private Kuller_Type kuller;

                public Offer(Double headus, PakkumusVastus pakkumusVastus, Kuller_Type kuller) {
                    this.goodness = headus;
                    this.pakkumusVastus = pakkumusVastus;
                    this.kuller = kuller;
                }

                public Double getGoodness() {
                    return goodness;
                }

                public PakkumusVastus getPakkumusVastus() {
                    return pakkumusVastus;
                }

                public Kuller_Type getKuller() {
                    return kuller;
                }
            }
            List<Offer> pakkumusVastused = new ArrayList<Offer>();
            for (Kuller_Type kuller : kullerid.getKuller()) {
                PakkumusParing pakkumusParing = buildPakkumusParing(kuller, tellimusVastus);
                PakkumusVastus pakkumusVastus = pakkumusPort.teePakkumus(pakkumusParing);
                double headus = pakkumusVastus.getHind() * 0.01 * pakkumusVastus.getPaevadeArv();
                Offer headusPakkumusVastus = new Offer(headus, pakkumusVastus, kuller);
                pakkumusVastused.add(headusPakkumusVastus);
            }
            pakkumusVastused.sort(Comparator.comparing(Offer::getGoodness));
            
            for (Offer pakkumusVastus : pakkumusVastused) {
            	Calendar approxDelivDate = Calendar.getInstance();
				approxDelivDate.add(Calendar.DATE, pakkumusVastus.getPakkumusVastus().getPaevadeArv());
            	
				BusinessLogger.log("Offer:\n"
    					+ "\tRank: [" + pakkumusVastus.getGoodness() + "]\n"
						+ "\tCourier: [" + pakkumusVastus.getKuller().getNimi() + "]\n"
						+ "\tTransport offer price: [" + pakkumusVastus.getPakkumusVastus().getHind() + "]\n"
						+ "\tDays: [" + pakkumusVastus.getPakkumusVastus().getPaevadeArv() + "]\n"
						+ "\tDelivery: [" + approxDelivDate.getTime());
            }
            
            Offer bestOffer = pakkumusVastused.get(0);

            Transport_Service transportServiceService = new Transport_Service(
                    new URL("http://localhost:8080/BPTeenused/services/transportSOAP?wsdl"), TRANSPORT_SERVICE_NAME);
            Transport transportPort = transportServiceService.getTransportSOAP();

            String trackingNumber = transportPort.transport(bestOffer.getPakkumusVastus().getOfferId());


            OrderShipmentServiceService orderShipmentServiceService = new OrderShipmentServiceService(
                    new URL("http://localhost:8080/BPServer/services/OrderShipmentServicePort?wsdl"), ORDER_SHIPMENT_SERVICE_NAME);
            OrderShipmentService orderShipmentServicePort = orderShipmentServiceService.getOrderShipmentServicePort();
            
            String ryhmName = "GJOHANNSON";
            int insertedId = orderShipmentServicePort.insertOrderShipment(
                    tellimusId,
                    ryhmName,
                    trackingNumber,
                    bestOffer.getKuller().getNimi(),
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(
                            GregorianCalendar.from(
                                    LocalDate.now().plusDays(bestOffer.getPakkumusVastus().getPaevadeArv())
                                            .atStartOfDay(ZoneId.systemDefault()))),
                    BigDecimal.valueOf(bestOffer.getPakkumusVastus().getHind()).setScale(2, RoundingMode.HALF_EVEN).floatValue());
            
            BusinessLogger.log("\nSelected offer:\n"
            		+ "\tCourier: [" + bestOffer.getKuller().getNimi() + "]\n"
    				+ "\tOffer: [" + bestOffer.getPakkumusVastus().getOfferId() + "]\n"
					+ "\tPrice: [" + BigDecimal.valueOf(bestOffer.getPakkumusVastus().getHind()).setScale(2, RoundingMode.HALF_EVEN) + "]\n"
					+ "\tTracking nr: [" + trackingNumber + "]\n"
					+ "\tOrderShipment: [" + insertedId + "]");

            MinuTellimuseVastus result = new ee.ttu.tud.idu0080.bp.MinuTellimuseVastus();
            result.setTeade("Order [" + insertedId + "] inserted successfully. Order tracking number: [" + trackingNumber + "]");
            
            BusinessLogger.log("End BusinessProcess");
            return result;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    private PakkumusParing buildPakkumusParing(Kuller_Type kt, Order o) {
        PakkumusParing pp = new PakkumusParing();
        pp.setKullerId(kt.getKullerId());

        TellimusType tt = new TellimusType();
        tt.setOrderId(o.getOrderId());
        tt.setPriceTotal(o.getPriceTotal());
        tt.setSeller(mapSellerToSeller(o.getSeller()));
        tt.setShippingAddress(mapAddressToAddress(o.getShippingAddress()));

        pp.setTellimus(tt);
        return pp;
    }
    
    private org.example.pakkumus.Address mapAddressToAddress(ttu.idu0080.order.server.Address aa) {
    	org.example.pakkumus.Address a = new org.example.pakkumus.Address();
        a.setZipcode(aa.getZipcode());
        a.setTownVillage(aa.getTownVillage());
        a.setStreetAddress(aa.getStreetAddress());
        a.setCounty(aa.getCounty());
        a.setCountry(aa.getCountry());
        a.setAddress(aa.getAddress());
        return a;
    }

    private org.example.pakkumus.Seller mapSellerToSeller(ttu.idu0080.order.server.Seller orderSeller) {
        org.example.pakkumus.Seller seller = new org.example.pakkumus.Seller();
        seller.setName(orderSeller.getName());
        seller.setEnterprise(orderSeller.getEnterprise());
        seller.addresses = buildAddressesToAddresses(orderSeller.getAddresses());
        return seller;
    }
    
    private List<org.example.pakkumus.Address> buildAddressesToAddresses(List<ttu.idu0080.order.server.EntAddress> addresses) {
        return addresses.stream()
                .map(ea -> {
                	org.example.pakkumus.Address a = new org.example.pakkumus.Address();
                    a.setAddress(ea.getAddress());
                    a.setCountry(ea.getCountry());
                    a.setCounty(ea.getCounty());
                    a.setStreetAddress(ea.getStreetAddress());
                    a.setTownVillage(ea.getTownVillage());
                    a.setZipcode(ea.getZipcode());
                    return a;
                })
				.collect(Collectors.toList());
    }

}
