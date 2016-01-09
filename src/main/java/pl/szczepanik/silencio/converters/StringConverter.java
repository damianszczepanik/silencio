package pl.szczepanik.silencio.converters;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Converter that changes each passed value into given string.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class StringConverter implements Converter {

    private final String newValue;

    /**
     * Creates new converter with value that will be returned by this converter.
     * 
     * @param newValue
     *            new value used as return value
     */
    public StringConverter(String newValue) {
        this.newValue = newValue;
    }

    @Override
    public Value convert(Key key, Value value) {
        return new Value(newValue);
    }

    @Override
    public void init() {
        // This method is intentionally empty, because this class is stateless
    }

}
