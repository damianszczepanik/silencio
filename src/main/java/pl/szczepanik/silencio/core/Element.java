package pl.szczepanik.silencio.core;

import pl.szczepanik.silencio.api.Strategy;

/**
 * Value that should be converted by {@link Strategy}. This type is immutable. This class wraps passed object but it
 * allows also to store metadata or change behavior when requirements changes.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class Element {

    private final String key;
    private final Object value;

    public Element(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns kye.
     */
    public Object getKey() {
        return key;
    }

    /**
     * Returns value.
     */
    public Object getValue() {
        return value;
    }
}
