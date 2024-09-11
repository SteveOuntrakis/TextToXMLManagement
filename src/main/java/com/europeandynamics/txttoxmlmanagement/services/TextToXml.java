package com.europeandynamics.txttoxmlmanagement.services;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.xml.transform.OutputKeys;

public class TextToXml {
public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();          
        
            Element rootElement = doc.createElement("Book");
            doc.appendChild(rootElement);
            
            BufferedReader reader = new BufferedReader(new FileReader("sample-lorem-ipsum-text-file.txt"));
            String line;
            int chapterCount = 1;
            int paragraphCount = 0;
            Element chapter = null;
          
            chapter = doc.createElement("Chapter");
            chapter.setAttribute("number", String.valueOf(chapterCount));
            rootElement.appendChild(chapter);

            StringBuilder paragraphBuilder = new StringBuilder();
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {                  
                    if (paragraphBuilder.length() > 0) {
                        paragraphCount++;
                        addParagraphToChapter(doc, chapter, paragraphBuilder.toString(),paragraphCount);
                        paragraphBuilder.setLength(0);                        
                                              
                        if (paragraphCount == 20) {
                            chapterCount++;
                            paragraphCount = 0;
                            chapter = doc.createElement("Chapter");
                            chapter.setAttribute("number", String.valueOf(chapterCount));
                            rootElement.appendChild(chapter);
                        }
                    }
                } else {
                    paragraphBuilder.append(line).append(" ");
                }
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("book.xml"));
            transformer.transform(source, result);
            
            reader.close();
            System.out.println("XML file created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addParagraphToChapter(Document doc, Element chapterElement, String paragraphText, int paragraphCount) {
        Element paragraph = doc.createElement("Paragraph");
        paragraph.setAttribute("number", String.valueOf(paragraphCount));
        chapterElement.appendChild(paragraph);
        
        String[] sentences = paragraphText.split("\\.\\s*");
        for (String sentence : sentences) {
            if (!sentence.trim().isEmpty()) {
                Element line = doc.createElement("Line");
                line.appendChild(doc.createTextNode(sentence.trim() + "."));
                paragraph.appendChild(line);
            }
        }
    }
}
