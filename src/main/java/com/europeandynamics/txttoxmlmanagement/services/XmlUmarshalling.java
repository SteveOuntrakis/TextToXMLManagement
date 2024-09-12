package com.europeandynamics.txttoxmlmanagement.services;

import com.europeandynamics.txttoxmlmanagement.domain.Book;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;

public class XmlUmarshalling {

    public Book readXML(String filePath) {
        Book book = null;
        try {
            // Create a JAXBContext for the Book class
            JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);

            // Create an Unmarshaller
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Read the XML file and unmarshal it into a Book object
            book = (Book) unmarshaller.unmarshal(new File(filePath));

            System.out.println("XML file read from: " + filePath);
            System.out.println(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }
}