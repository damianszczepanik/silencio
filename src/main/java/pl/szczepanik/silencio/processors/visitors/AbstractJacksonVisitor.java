package pl.szczepanik.silencio.processors.visitors;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.ProcessorException;

/**
 * Collects common methods and attributes used by visitors that implements Jackson's library.
 *
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public abstract class AbstractJacksonVisitor extends AbstractVisitor {

    /**
     * Process map or array/list objects.
     *
     * @param key   key
     * @param value value that should be converted
     */
    protected void processComplex(String key, Object value) {
        if (isMap(value)) {
            processMap((Map<String, Object>) value);
        } else if (isArray(value)) {
            processArray(key, (List<Object>) value);
        } else {
            throw new ProcessorException("Unknown type of the key: " + value.getClass().getName());
        }
    }

    /**
     * Process map object.
     *
     * @param map value that should be converted
     */
    protected void processMap(Map<String, Object> map) {
        for (Map.Entry<String, Object> keyMap : map.entrySet()) {
            String key = keyMap.getKey();
            Object value = keyMap.getValue();

            if (isBasicType(value)) {
                map.put(key, processValue(new Key(key), value).getValue());
            } else {
                processComplex(key, value);
            }
        }
    }

    /**
     * Process array/list object.
     *
     * @param list value that should be converted
     */
    private void processArray(String key, List<Object> list) {
        for (int i = 0; i < list.size(); i++) {
            Object value = list.get(i);
            if (isBasicType(value)) {
                list.set(i, processValue(new Key(key), value).getValue());
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
                || value instanceof Integer || value instanceof Long || value instanceof BigInteger
                || value instanceof Double || value instanceof BigDecimal
                || value instanceof Boolean
                || value == null;
    }
}
