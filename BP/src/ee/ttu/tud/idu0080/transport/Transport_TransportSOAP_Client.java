
package ee.ttu.tud.idu0080.transport;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2017-05-05T09:39:59.694+03:00
 * Generated source version: 2.7.18
 * 
 */
public final class Transport_TransportSOAP_Client {

    private static final QName SERVICE_NAME = new QName("http://idu0080.tud.ttu.ee/transport/", "transport");

    private Transport_TransportSOAP_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = Transport_Service.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        Transport_Service ss = new Transport_Service(wsdlURL, SERVICE_NAME);
        Transport port = ss.getTransportSOAP();  
        
        {
        System.out.println("Invoking transport...");
        java.lang.String _transport_pakkumusId = "_transport_pakkumusId1647183846";
        java.lang.String _transport__return = port.transport(_transport_pakkumusId);
        System.out.println("transport.result=" + _transport__return);


        }

        System.exit(0);
    }

}
