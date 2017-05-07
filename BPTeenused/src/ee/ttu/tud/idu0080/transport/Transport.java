package ee.ttu.tud.idu0080.transport;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2017-05-05T09:37:05.590+03:00
 * Generated source version: 2.7.18
 * 
 */
@WebService(targetNamespace = "http://idu0080.tud.ttu.ee/transport/", name = "transport")
@XmlSeeAlso({ObjectFactory.class})
public interface Transport {

    @WebMethod(operationName = "Transport", action = "http://idu0080.ttu.ee/transport/Transport")
    @RequestWrapper(localName = "Transport", targetNamespace = "http://idu0080.tud.ttu.ee/transport/", className = "ee.ttu.tud.idu0080.transport.Transport_Type")
    @ResponseWrapper(localName = "TransportResponse", targetNamespace = "http://idu0080.tud.ttu.ee/transport/", className = "ee.ttu.tud.idu0080.transport.TransportResponse")
    @WebResult(name = "track_id", targetNamespace = "")
    public java.lang.String transport(
        @WebParam(name = "pakkumus_id", targetNamespace = "")
        java.lang.String pakkumusId
    );
}
