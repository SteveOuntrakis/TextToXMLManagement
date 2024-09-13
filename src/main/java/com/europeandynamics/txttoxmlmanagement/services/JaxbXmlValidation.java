package com.europeandynamics.txttoxmlmanagement.services;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;

@Slf4j
public class JaxbXmlValidation {   
   
    public boolean xmlValidator(String xmlFileName, String xsdFileame, Class xmlClass) {
        log.debug("method starts");
        boolean returnStatus = false;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(xmlClass );
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(xsdFileame));
            unmarshaller.setSchema(schema);
            File xmlFile = new File(xmlFileName);
            Object object = unmarshaller.unmarshal(xmlFile);
            log.debug("xml validated ", object);
            returnStatus = true;
       } catch (JAXBException | SAXException e) {
           log.debug("not valid xml", e);
        } 
         log.debug("method terminates");
         return returnStatus;
    }
}
