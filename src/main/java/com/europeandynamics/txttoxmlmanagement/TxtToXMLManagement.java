package com.europeandynamics.txttoxmlmanagement;

import com.europeandynamics.txttoxmlmanagement.domain.Book;
import com.europeandynamics.txttoxmlmanagement.domain.Chapter;
import com.europeandynamics.txttoxmlmanagement.domain.Paragraph;
import com.europeandynamics.txttoxmlmanagement.services.TextToXml;
import com.europeandynamics.txttoxmlmanagement.services.XmlMarshalling;
import com.europeandynamics.txttoxmlmanagement.services.XmlUmarshalling;
import java.util.List;

/**
 *
 * @author stef6
 */
public class TxtToXMLManagement {

    public static void main(String[] args) {
        //Step one TxtToXml :
        TextToXml textToXml = new TextToXml();
        textToXml.TextToXmlConverter();
        
        //Step two , creating Xml writer - Marshaller:
        List linesList1 = List.of("line1","line2","line3");
        List linesList2 = List.of("line4","line5","line6");
        Paragraph paragraph1 = new Paragraph(linesList1);
        Paragraph paragraph2 = new Paragraph(linesList2);
        List paraList = List.of(paragraph1,paragraph2);
        
        Chapter chapter1 = new Chapter(paraList);
        List chapterList = List.of(chapter1,chapter1);
        Book book = new Book(chapterList);
        XmlMarshalling marshall= new XmlMarshalling();
        marshall.createXML(book,"book_Marshaller.xml");
        
        //Step two continuation, creating Xml Reader - Unmarshaller:
        XmlUmarshalling unmarshaller = new XmlUmarshalling();
        unmarshaller.readXML("book_Marshaller.xml");
    }
}
