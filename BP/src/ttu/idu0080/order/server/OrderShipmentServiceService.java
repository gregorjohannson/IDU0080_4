package ttu.idu0080.order.server;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2017-05-05T09:44:33.490+03:00
 * Generated source version: 2.7.18
 * 
 */
@WebServiceClient(name = "OrderShipmentServiceService", 
                  wsdlLocation = "http://localhost:8080/BPServer/services/OrderShipmentServicePort?wsdl",
                  targetNamespace = "http://server.order.idu0080.ttu/") 
public class OrderShipmentServiceService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://server.order.idu0080.ttu/", "OrderShipmentServiceService");
    public final static QName OrderShipmentServicePort = new QName("http://server.order.idu0080.ttu/", "OrderShipmentServicePort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/BPServer/services/OrderShipmentServicePort?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(OrderShipmentServiceService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/BPServer/services/OrderShipmentServicePort?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public OrderShipmentServiceService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public OrderShipmentServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OrderShipmentServiceService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public OrderShipmentServiceService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public OrderShipmentServiceService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public OrderShipmentServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns OrderShipmentService
     */
    @WebEndpoint(name = "OrderShipmentServicePort")
    public OrderShipmentService getOrderShipmentServicePort() {
        return super.getPort(OrderShipmentServicePort, OrderShipmentService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns OrderShipmentService
     */
    @WebEndpoint(name = "OrderShipmentServicePort")
    public OrderShipmentService getOrderShipmentServicePort(WebServiceFeature... features) {
        return super.getPort(OrderShipmentServicePort, OrderShipmentService.class, features);
    }

}
