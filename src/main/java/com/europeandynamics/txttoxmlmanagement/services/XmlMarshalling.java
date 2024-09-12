package com.europeandynamics.txttoxmlmanagement.services;

import com.europeandynamics.txttoxmlmanagement.domain.Book;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import java.io.File;

public class XmlMarshalling {

    public void createXML(Book book,String filepath) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(book, new File(filepath));
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
