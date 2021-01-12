package pl.szczepanik.silencio.processors;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.processors.visitors.XMLVisitor;

/**
 * Provides processor that supports XML format.
 *
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class XMLProcessor extends AbstractProcessor {

    private final XMLVisitor visitor = new XMLVisitor();

    private Document document = null;

    /**
     * Creates new processor for XML file.
     */
    public XMLProcessor() {
        super(Format.XML);
    }

    @Override
    public void realLoad(Reader reader) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(reader));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ProcessorException(e);
        }
    }

    @Override
    public void realProcess() {
        visitor.setConfiguration(configuration);
        Element rootElement = document.getDocumentElement();
        // optional, but recommended
        // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        rootElement.normalize();

        visitor.processXML(rootElement);
    }

    @Override
    public void realWrite(Writer writer) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        try {
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
        } catch (TransformerException e) {
            throw new ProcessorException(e);
        }
    }
}
