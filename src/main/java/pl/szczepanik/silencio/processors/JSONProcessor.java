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

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Strategy;
import pl.szczepanik.silencio.core.AbstractProcessor;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.core.Element;

/**
 * Provides processor that supports JSON format.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class JSONProcessor extends AbstractProcessor {

    private final ObjectMapper mapper;

    private Map<String, Object> jsonStructure;

    public JSONProcessor(Strategy[] strategies) {
        super(Format.JSON, strategies);

        mapper = new ObjectMapper();
        // this prevents printing eg. 2.20 as 2.2
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
    }

    @SuppressWarnings("unchecked")
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
        initStrategies();
        processMap(jsonStructure);
    }

    @SuppressWarnings("unchecked")
    private void processComplex(String key, Object value) {
        if (isMap(value)) {
            processMap((Map) value);
        } else if (isArray(value)) {
            processArray(key, (List) value);
        } else {
            throw new ProcessorException("Unknown type of the key: " + value.getClass().getName());
        }
    }

    private void processMap(Map<String, Object> map) {
        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (isBasicType(value)) {
                map.put(key, processBasicType(key, value).getValue());
            } else {
                processComplex(key, value);
            }
        }
    }

    private void processArray(String key, List<Object> list) {
        for (int i = 0; i < list.size(); i++) {
            Object value = list.get(i);
            if (isBasicType(value)) {
                list.set(i, processBasicType(key, value).getValue());
            } else {
                processComplex(key, value);
            }
        }
    }

    private Element processBasicType(String key, Object value) {
        Element converted = new Element(key, value);
        for (Strategy strategy : strategies) {
            converted = strategy.convert(converted);
        }

        return converted;
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
        try {
            mapper.writeValue(writer, jsonStructure);
        } catch (IOException e) {
            throw new ProcessorException(e);
        }

    }

}
