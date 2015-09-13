package pl.szczepanik.silencio.processors.visitors;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import pl.szczepanik.silencio.core.Processable;
import pl.szczepanik.silencio.core.ProcessorException;

/**
 * Iterates over JSON nodes and calls {@link Processable} for each basic node.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class JSONVisitor extends AbstractVisitor {

    public JSONVisitor(Processable processable) {
        super(processable);
    }

    public void process(Map<String, Object> json) {
        processMap(json);
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
                map.put(key, processable.processValue(key, value).getValue());
            } else {
                processComplex(key, value);
            }
        }
    }

    private void processArray(String key, List<Object> list) {
        for (int i = 0; i < list.size(); i++) {
            Object value = list.get(i);
            if (isBasicType(value)) {
                list.set(i, processable.processValue(key, value).getValue());
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
        return value instanceof String || value instanceof Integer || value instanceof Long
                || value instanceof BigInteger || value instanceof BigDecimal || value instanceof Double
                || value instanceof Boolean || value == null;
    }

}
