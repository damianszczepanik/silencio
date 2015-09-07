package pl.szczepanik.silencio.processors;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.core.AbstractProcessor;
import pl.szczepanik.silencio.core.ProcessorException;

/**
 * Provides processor that supports JSON format.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class JSONProcessor extends AbstractProcessor {

    private final ObjectMapper mapper;

    private Map<String, Object> jsonStructure;

    public JSONProcessor(Converter[] converters) {
        super(Format.JSON, converters);

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
        initConverties();
        processMap(jsonStructure);
    }

    @SuppressWarnings("unchecked")
    private void processComplex(String key, Object value) {
        if (isMap(value)) {
            processMap((Map<String, Object>) value);
        } else if (isArray(value)) {
            processArray(key, (List<Object>) value);
        } else {
            throw new ProcessorException("Unknown type of the key: " + value.getClass().getName());
        }
    }

    private void processMap(Map<String, Object> map) {
        for (Map.Entry<String, Object> keyMap : map.entrySet()) {
            String key = keyMap.getKey();
            Object value = keyMap.getValue();

            if (isBasicType(value)) {
                map.put(key, processValue(key, value).getValue());
            } else {
                processComplex(key, value);
            }
        }
    }

    private void processArray(String key, List<Object> list) {
        for (int i = 0; i < list.size(); i++) {
            Object value = list.get(i);
            if (isBasicType(value)) {
                list.set(i, processValue(key, value).getValue());
            } else {
                processComplex(key, value);
            }
        }
    }

    private boolean isMap(Object value) {
        return value instanceof Map;
    }

    private boolean isArray(Object value) {
        return value instanceof List;
    }

    private boolean isBasicType(Object value) {
        // list of available types http://wiki.fasterxml.com/JacksonInFiveMinutes
        return value instanceof String
                || value instanceof Integer
                || value instanceof Long
                || value instanceof BigInteger
                || value instanceof BigDecimal
                || value instanceof Double
                || value instanceof Boolean
                || value == null;
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
