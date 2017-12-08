package ocp.service.po;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class ErrorResponse {

    public static Response create(String message) {
        Document xmlDocument = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            xmlDocument = builder.newDocument();
            Element rootElement = xmlDocument.createElement("error");
            rootElement.appendChild(xmlDocument.createTextNode(message));
            xmlDocument.appendChild(rootElement);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ErrorResponse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(xmlDocument).type(MediaType.APPLICATION_XML).build();
    }

}
