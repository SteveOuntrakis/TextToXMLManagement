package com.europeandynamics.txttoxmlmanagement;

import com.europeandynamics.txttoxmlmanagement.domain.Book;
import com.europeandynamics.txttoxmlmanagement.services.TextToXml;
import com.europeandynamics.txttoxmlmanagement.services.BookHandler;
import com.europeandynamics.txttoxmlmanagement.services.JaxbXmlValidation;
import com.europeandynamics.txttoxmlmanagement.services.JaxbXsdGenerator;
import java.io.IOException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author stef6
 */
@Slf4j
public class TxtToXMLManagement {

    public static final String FILENAME = "book.xml";
    public static final String PARAGRAPH = "paragraph.xml";
    public static final String SCHEMA = "book-schema.xsd";

    public static void main(String[] args) throws IOException {

        //read a txt file and convert it to xml. Then show statistics.
        TextToXml textToXml = new TextToXml();
        textToXml.TextToXmlConverter(FILENAME);

        //read and write a specific paragraph from a specific chapter to a new xml file.
        System.out.println("\n==================================================\n");
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            log.info("Selecting paragraph...");
            BookHandler handler = new BookHandler(3, 7, PARAGRAPH);
            saxParser.parse(FILENAME, handler);
            log.info("Saved the selected paragraph to : " + PARAGRAPH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //We generate Xsd file through the domain classes.
        System.out.println("\n==================================================\n");
        JaxbXsdGenerator xsdGenerator= new JaxbXsdGenerator();
        xsdGenerator.xsdGenerator();
        
        //Validating the xml and xsd file.
        JaxbXmlValidation validator =  new JaxbXmlValidation();
        boolean validity = validator.xmlValidator(PARAGRAPH,SCHEMA , Book.class);

        if (validity) {
            System.out.println("The file is valid");
        } else {
            System.out.println("The file is not valid");
        }       
    }
}
