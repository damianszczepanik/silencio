package pl.szczepanik.silencio.core;

import pl.szczepanik.silencio.api.Converter;

/**
 * Value that should be converted by {@link Converter}. This class wraps passed object but it allows also to store
 * metadata or change behavior when requirements changes.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class Value {

    private final Object value;

    /**
     * Creates new instance from passed value.
     * 
     * @param value
     *            value of this instance
     */
    public Value(Object value) {
        this.value = value;
    }

    /**
     * Gets value of this instance.
     * 
     * @return value of this instance
     */
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("Value [value='%s']", value);
    }
}
