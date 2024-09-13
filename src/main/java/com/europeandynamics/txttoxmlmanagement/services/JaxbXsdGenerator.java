package com.europeandynamics.txttoxmlmanagement.services;

import com.europeandynamics.txttoxmlmanagement.domain.Book;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.SchemaOutputResolver;
import java.io.File;
import java.io.IOException;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

public class JaxbXsdGenerator {

    public void xsdGenerator() throws IOException {
        try {
            String xsdFileName = "book-schema.xsd";
            JAXBContext context = JAXBContext.newInstance(Book.class);
            context.generateSchema(new MySchemaOutputResolver(xsdFileName));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    static class MySchemaOutputResolver extends SchemaOutputResolver {

        private String xsdFileName;

        public MySchemaOutputResolver(String xsdFileName) {
            this.xsdFileName = xsdFileName;
        }

        @Override
        public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
            File file = new File(xsdFileName);
            StreamResult result = new StreamResult(file);
            result.setSystemId(file.toURI().toString());
            return result;
        }
    }
}
