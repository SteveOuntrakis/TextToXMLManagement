package com.europeandynamics.txttoxmlmanagement.services;

import java.io.FileWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

@Slf4j
public class BookHandler extends DefaultHandler {

    private boolean inChapter = false;
    private boolean inParagraph = false;
    private int currentChapter = 0;
    private int currentParagraph = 0;
    private int chapterNumber;
    private int paragraphNumber;
    private XMLStreamWriter writer;

    public BookHandler(int chapterNumber, int paragraphNumber, String outputFilename) throws Exception {
        this.chapterNumber = chapterNumber;
        this.paragraphNumber = paragraphNumber;
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        this.writer = outputFactory.createXMLStreamWriter(new FileWriter(outputFilename));

        writer.writeStartDocument("UTF-8", "1.0");
        writer.writeCharacters("\n");
        writer.writeStartElement("Book");
        writer.writeCharacters("\n");
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try {
            if (qName.equalsIgnoreCase("chapter")) {
                currentChapter = Integer.parseInt(attributes.getValue("number"));
                if (currentChapter == chapterNumber) {
                    inChapter = true;
                    writer.writeCharacters("  ");
                    writer.writeStartElement("chapter");
                    writer.writeCharacters("\n");
                } else {
                    inChapter = false;
                }
            }

            if (inChapter && qName.equalsIgnoreCase("paragraph")) {
                currentParagraph = Integer.parseInt(attributes.getValue("number"));
                if (currentParagraph == paragraphNumber) {
                    inParagraph = true;
                    writer.writeCharacters("    ");
                    writer.writeStartElement("paragraph");
                    writer.writeCharacters("\n");
                } else {
                    inParagraph = false;
                }
            }
            if (inChapter && inParagraph && qName.equalsIgnoreCase("line")) {
                writer.writeCharacters("      ");
                writer.writeStartElement("line");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }

    public void characters(char[] ch, int start, int length) {
        if (inChapter && inParagraph) {
            try {
                String content = new String(ch, start, length).trim();
                if (!content.isEmpty()) {
                    writer.writeCharacters(content);
                }
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
    }

    public void endElement(String uri, String localName, String qName) {
        try {
           if (inChapter && inParagraph && qName.equalsIgnoreCase("line")) {
                writer.writeEndElement();
                writer.writeCharacters("\n");
            }
            if (inChapter && qName.equalsIgnoreCase("paragraph") && inParagraph) {
                writer.writeCharacters("    ");
                writer.writeEndElement(); 
                writer.writeCharacters("\n");
                inParagraph = false;
            }
            if (qName.equalsIgnoreCase("chapter") && inChapter) {
                writer.writeCharacters("  ");
                writer.writeEndElement();
                writer.writeCharacters("\n");
                inChapter = false;
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }
    public void endDocument() {
        try {
            writer.writeEndElement(); 
            writer.writeEndDocument();
            writer.flush();
            writer.close();
            log.info("\nNew XML file created successfully with the selected paragraph.");
        } catch (Exception e) {
             log.info(e.getMessage());
        }
    }

}
