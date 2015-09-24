package pl.szczepanik.silencio.processors;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.core.AbstractProcessor;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.processors.visitors.JSONVisitor;

/**
 * Provides processor that supports XML format.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class XMLProcessor extends AbstractProcessor {

    private final ObjectMapper mapper;

    private Map<String, Object> xmlStructure;

    // XML, not JSON
    private final JSONVisitor visitor = new JSONVisitor(this);

    public XMLProcessor() {
        super(Format.XML);

        mapper = new XmlMapper();
    }

    @Override
    public void realLoad(Reader reader) {
        try {
            xmlStructure = mapper.readValue(reader, Map.class);
            System.out.println(xmlStructure);
            System.out.println(mapper.writeValueAsString(new HashMap<>()));
        } catch (IOException e) {
            throw new ProcessorException(e);
        }
    }

    @Override
    public void realProcess() {
        visitor.process(xmlStructure);
    }

    @Override
    public void realWrite(Writer writer) {
        try {
            mapper.writeValue(writer, xmlStructure);
        } catch (IOException e) {
            throw new ProcessorException(e);
        }

    }

}
