# TextToXMLManagement

TextToXMLManagement is a project dedicated to parse a txt file and convert it to XML. 

## How it works

Its purpose is to read a book and with the making of some assumptions we convert it to a beautify XML file. These assumptions are:

- Each line ends in a dot.
- Each paragraph ends when we meet an empty line.
- Each chapter is 20 paragraphs.

## Statistics

AlongSide with the parsing proccess, some usefull statistics are written. The statistics are : 
- the number of paragraphs, lines, words, and distinct words of the document.
- the date/time of the creation of the document.
- the author’s name.
- and the application class name.

## Domain classes

In order to achieve the structure that we want in our xml,
a Book class was created that contains a list of chapters. 
Chapter is also a domain class that contains a list of paragraphs and a Paragraph is a list of Strings, the lines. 
All of the domain classes have the appropriate bean annotations in order to achieve our result!

## Service classes

This package contains a few classes that helps us to build our app. Many of them shouldn't be contained in Service package (maybe a Util package is a more appropriate name), but in order to simplify our app, have everything in one place and lastly to show that every class answers a question, the classes were created in the Service package.

- ### **TextToXml**:
    -   This is an interface that works with TextToXmlImpl. Its creation is not so necessary, but in order to show the understanding of interfaces and how they work, this interface was added.
- ### **TextToXmlImpl**:
    - This is where the parsing of the book from the .txt file to .xml file is happening.
    - In order to achieve the best approach, the usage of stAX was required.
    - Basically a XMLStreamWriter writer was created in order to do a serial conversion from the txt file to the xml file.
    - By doing, at the same time there are some counters keeping track the statistics that we need.
    - Firstly the BufferedReader reader ,reads the txt file.
    - Secondly the writer starts by starting an element Chapter that has the attribute chapterCount in order to show in which chapter we are in.
    - In this chapter we go in a loop, where we call a new method, the addParagraphToChapter. This method contains also one loop where we add lines.
    - Basically we start here. We named a list of Strings as Sentences and the list ends when the paragraph ends. Then we split each line by finding the dot and we keep this loop until the paragraph ends.
    - Then we go back, and we do the same thing untill we count 20 paragraphs.
    - Then we ender an If where we end our chapter,the paragraph count resets, the counter goes up and we start a new chapter Element.
    - As long as we find text the loop continues. When we can not find anything else we close everything and we write down the statistics in our console.
- ### **BookHandler** :
    - In this class, the implementation was the same as the example but the addition of the overriden method endDocument was required.
    - The idea is that the user searches a specific paragraph in a specific chapter and saves it in an xml file.
    - This class is called after the usage of SAXParser in the main class.
- ### **JaxbXmlGenerator** :
    - This class is the same as the example. It takes a domain class and creates a XSD file from it.
- ### **JaxbXmlValidation** :
    - Finally this class compares the XSD file and the xml file ( not the book.xml, but the paragraph.xml ) and if everything is correct it says Valid.
    - Unmarshaller was used for this implementation.

## Used libraries - methods

### StAX

StAX is one of the several XML libraries in Java. It’s a memory-efficient library included in the JDK since Java 6. StAX doesn’t load the entire XML into memory. Instead, it pulls data from a stream in a forward-only fashion. The stream is read by an XMLEventReader object.

### SAX 

SAX (Simple API for XML) is an event-driven online algorithm for lexing and parsing XML documents, with an API developed by the XML-DEV mailing list. SAX provides a mechanism for reading data from an XML document that is an alternative to that provided by the Document Object Model (DOM).
### Unmarshaller

The JAXB Unmarshaller interface is responsible for governing the process of deserializing XML to Java Objects.

## Running Proccess

- We run the main class.
- Then the first thing it does is to call textToXml.TextToXmlConverter() and convert the txt file into xml.
- Secondly with the help of SAX, the BookHandler is called in order to create a new xml file with a specific paragraph from a specific chapter that the user wants.
- Moreover, we call the xsdGenerator in order to create a XSD file from the domain classes.
- Lastly, we take the newly created xml file and we validate it with the schema. If it is valid then there is no mistake and the programm runs appropriately.

