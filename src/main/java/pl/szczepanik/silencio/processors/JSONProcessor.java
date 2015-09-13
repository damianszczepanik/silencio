package pl.szczepanik.silencio.processors;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.core.AbstractProcessor;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.processors.visitors.JSONVisitor;

/**
 * Provides processor that supports JSON format.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class JSONProcessor extends AbstractProcessor {

    private final ObjectMapper mapper;

    private Map<String, Object> jsonStructure;

    private final JSONVisitor visitor = new JSONVisitor(this);

    public JSONProcessor() {
        super(Format.JSON);

        mapper = new ObjectMapper();
        // this prevents printing eg. 2.20 as 2.2
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
    }

    @Override
    public void realLoad(Reader reader) {
        try {
            jsonStructure = mapper.readValue(reader, new TypeReference<Map<String, Object>>() { });
        } catch (IOException e) {
            throw new ProcessorException(e);
        }
    }

    @Override
    public void realProcess() {
        visitor.process(jsonStructure);
    }

    @Override
    public void realWrite(Writer writer) {
        ObjectWriter objectWriter = mapper.writer().with(SerializationFeature.INDENT_OUTPUT);
        try {
            objectWriter.writeValue(writer, jsonStructure);
        } catch (IOException e) {
            throw new ProcessorException(e);
        }

    }

}
