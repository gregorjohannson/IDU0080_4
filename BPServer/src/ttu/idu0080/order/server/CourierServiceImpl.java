
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package ttu.idu0080.order.server;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import ttu.idu0080.order.server.model.Enterprise;
import ttu.idu0080.order.server.model.EshopOrder;

/**
 * This class was generated by Apache CXF 3.1.11
 * 2017-05-01T17:56:20.637+03:00
 * Generated source version: 3.1.11
 * 
 */

@javax.jws.WebService(
                      serviceName = "CourierServiceService",
                      portName = "CourierServicePort",
                      targetNamespace = "http://server.order.idu0080.ttu/",
                      wsdlLocation = "file:/C:/Projektid/IDU0080_4/BPServer/WebContent/couriers_1.wsdl",
                      endpointInterface = "ttu.idu0080.order.server.CourierService")
                      
public class CourierServiceImpl implements CourierService {
	
	@PersistenceContext
	private EntityManager em;

    private static final Logger LOG = Logger.getLogger(CourierServiceImpl.class.getName());

    /* (non-Javadoc)
     * @see ttu.idu0080.order.server.CourierService#getCouriersByAddress(java.lang.String country, java.lang.String county)*
     */
    public java.util.List<ttu.idu0080.order.server.Courier> getCouriersByAddress(java.lang.String country, java.lang.String county) { 
        LOG.info("Executing operation getCouriersByAddress");
        System.out.println(country);
        System.out.println(county);
        try {
            java.util.List<ttu.idu0080.order.server.Courier> _return = new java.util.ArrayList<ttu.idu0080.order.server.Courier>();
            ttu.idu0080.order.server.Courier _returnVal1 = new ttu.idu0080.order.server.Courier();
            java.util.List<ttu.idu0080.order.server.EntAddress> _returnVal1Addresses = new java.util.ArrayList<ttu.idu0080.order.server.EntAddress>();
            ttu.idu0080.order.server.EntAddress _returnVal1AddressesVal1 = new ttu.idu0080.order.server.EntAddress();
            _returnVal1AddressesVal1.setAddress(1937917034);
            _returnVal1AddressesVal1.setCountry("Country-1651990161");
            _returnVal1AddressesVal1.setCounty("County-2046475541");
            _returnVal1AddressesVal1.setStreetAddress("StreetAddress394235116");
            _returnVal1AddressesVal1.setTownVillage("TownVillage105405347");
            _returnVal1AddressesVal1.setZipcode("Zipcode-1854597027");
            _returnVal1Addresses.add(_returnVal1AddressesVal1);
            _returnVal1.getAddresses().addAll(_returnVal1Addresses);
            _returnVal1.setEnterprise(1836132735);
            _returnVal1.setName("Name-1069749213");
            _returnVal1.setPercentFromOrder(-859017470);
            _return.add(_returnVal1);
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public ttu.idu0080.order.server.EntAddress addressBuilder(ttu.idu0080.order.server.model.EntAddress entAddress) {
        ttu.idu0080.order.server.EntAddress ea = new EntAddress();
        ea.setAddress((int) entAddress.getEntAddress());
        ea.setCountry(entAddress.getCountry());
        ea.setCounty(entAddress.getCounty());
        ea.setStreetAddress(entAddress.getStreetAddress());
        ea.setTownVillage(entAddress.getTownVillage());
        ea.setZipcode(entAddress.getZipcode());
        return ea;
    }
    
    /* (non-Javadoc)
     * @see ttu.idu0080.order.server.CourierService#getCourierById(int courierId)*
     */
    public ttu.idu0080.order.server.Courier getCourierById(int courierId) {
        LOG.info("Executing operation getCourierById");
        System.out.println(courierId);
        try {
        	EntityManagerFactory factory = Persistence.createEntityManagerFactory("BPServer");
        	EntityManager em = factory.createEntityManager();
        	
        	Enterprise enterprise = em.find(Enterprise.class, (long) courierId);
        	
        	Courier courier = new Courier();
        	courier.setEnterprise((int) enterprise.getEnterprise());
        	courier.setName(enterprise.getFullName());        	
        	courier.setPercentFromOrder(enterprise.getPercentFromOrder().intValue());
        	
        	List<ttu.idu0080.order.server.model.EntAddress> addresses = em.createQuery("SELECT e FROM EntAddress e WHERE e.subjectFk = :enterprise")
        	        .setParameter("enterprise", BigDecimal.valueOf(courierId))
        	        .getResultList();

			for (ttu.idu0080.order.server.model.EntAddress address : addresses) {
				courier.getAddresses().add(addressBuilder(address));
			}
			
			factory.close();
        	
            return courier;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see ttu.idu0080.order.server.CourierService#getAllCouriers()*
     */
    public java.util.List<ttu.idu0080.order.server.Courier> getAllCouriers() { 
        LOG.info("Executing operation getAllCouriers");
        try {
        	EntityManagerFactory factory = Persistence.createEntityManagerFactory("BPServer");
        	EntityManager em = factory.createEntityManager();
        	
        	List<Enterprise> enterprises = em.createNamedQuery("Enterprise.findAll").getResultList();
        	
        	List<ttu.idu0080.order.server.Courier> couriers = new ArrayList<ttu.idu0080.order.server.Courier>();
        	for (Enterprise e : enterprises) {
        		Courier c = new Courier();
        		c.setEnterprise((int) e.getEnterprise());
            	c.setName(e.getFullName());        	
            	c.setPercentFromOrder(e.getPercentFromOrder().intValue());
            	
            	List<ttu.idu0080.order.server.model.EntAddress> addresses = em.createQuery("SELECT e FROM EntAddress e WHERE e.subjectFk = :enterprise")
            	        .setParameter("enterprise", BigDecimal.valueOf(e.getEnterprise()))
            	        .getResultList();

    			for (ttu.idu0080.order.server.model.EntAddress address : addresses) {
    				c.getAddresses().add(addressBuilder(address));
    			}
    			
    			couriers.add(c);
        	}
        	
        	factory.close();
        	
        	return couriers;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
