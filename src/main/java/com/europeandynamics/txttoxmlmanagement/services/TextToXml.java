package com.europeandynamics.txttoxmlmanagement.services;

import javax.xml.stream.XMLStreamWriter;

public interface TextToXml {

    void TextToXmlConverter(String filename);

    void addParagraphToChapter(XMLStreamWriter writer, String paragraphText, int paragraphCount) throws Exception;

}
