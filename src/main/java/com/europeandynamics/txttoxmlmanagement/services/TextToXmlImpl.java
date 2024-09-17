package com.europeandynamics.txttoxmlmanagement.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TextToXmlImpl implements TextToXml{

    int paragraphNumber = 0;
    int lineNumber = 0;
    int wordNumber = 0;
    Set<String> distinctWords = new HashSet<>();
    Date creationTime = new Date();

    @Override
    public void TextToXmlConverter(String filename) {

        try {
            System.out.println("\n==================================================\n");
            log.info("Starting reading txt File...");
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = outputFactory.createXMLStreamWriter(new FileWriter(filename));

            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeCharacters("\n");
            writer.writeStartElement("Book");
            writer.writeCharacters("\n");

            BufferedReader reader = new BufferedReader(new FileReader("sample-lorem-ipsum-text-file.txt"));
            String line;
            int chapterCount = 1;
            int paragraphCount = 0;
            StringBuilder paragraphBuilder = new StringBuilder();
            log.info("Converting Txt File to Xml using Stax...");
            writer.writeCharacters("  ");
            writer.writeStartElement("chapter");
            writer.writeAttribute("number", String.valueOf(chapterCount));
            writer.writeCharacters("\n");

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    if (paragraphBuilder.length() > 0) {
                        paragraphCount++;
                        paragraphNumber++;
                        addParagraphToChapter(writer, paragraphBuilder.toString(), paragraphCount);

                        paragraphBuilder.setLength(0);

                        // After 20 paragraphs, create a new chapter
                        if (paragraphCount == 20) {
                            writer.writeCharacters("  ");
                            writer.writeEndElement(); // End current chapter
                            writer.writeCharacters("\n");
                            chapterCount++;
                            paragraphCount = 0;
                            writer.writeCharacters("  ");
                            writer.writeStartElement("chapter");
                            writer.writeAttribute("number", String.valueOf(chapterCount));
                            writer.writeCharacters("\n");
                        }
                    }
                } else {
                    paragraphBuilder.append(line).append(" ");
                }
            }

            // End current chapter, book and document. Then we close everything        
            writer.writeEndElement();
            writer.writeCharacters("\n");
            writer.writeEndElement();
            writer.writeCharacters("\n");
            writer.writeEndDocument();
            writer.flush();
            writer.close();
            reader.close();
            log.info("Xml file was created succesfully...");
            System.out.println("\n==================================================\n");
            log.info("Statistics:");
            log.info("Number of paragraphs: " + paragraphNumber);
            log.info("Number of lines: " + lineNumber);
            log.info("Number of words: " + wordNumber);
            log.info("Number of distinct words: " + distinctWords.size()); 
            log.info("Created at: " + creationTime);
            log.info("Author: Stefanos Ountrakis");
            log.info("Appication class Name : "+this.getClass().getName());
        } catch (IOException e) {
            log.info(e.getMessage());
        } catch (XMLStreamException e) {
            log.info(e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(TextToXmlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addParagraphToChapter(XMLStreamWriter writer, String paragraphText, int paragraphCount) throws Exception {
        // Start a new paragraph
        writer.writeCharacters("    ");
        writer.writeStartElement("paragraph");
        writer.writeAttribute("number", String.valueOf(paragraphCount));
        writer.writeCharacters("\n");
        // Split the paragraph into sentences
        String[] sentences = paragraphText.split("\\.\\s*");
        for (String sentence : sentences) {
            if (!sentence.trim().isEmpty()) {
                lineNumber++;
                // Add each sentence as a Line
                writer.writeCharacters("      ");
                writer.writeStartElement("line");
                writer.writeCharacters(sentence.trim() + ".");
                writer.writeEndElement();
                writer.writeCharacters("\n");
                String[] words = sentence.split("\\s+");
                wordNumber += words.length;
                for (String word : words) {
                    distinctWords.add(word.toLowerCase());
                }
            }
        }
        writer.writeCharacters("    ");
        writer.writeEndElement();
        writer.writeCharacters("\n");
    }
}
