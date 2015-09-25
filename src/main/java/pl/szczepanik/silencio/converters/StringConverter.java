package pl.szczepanik.silencio.converters;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Converter that changes each passed value into given string.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class StringConverter implements Converter {

    private final String newValue;

    public StringConverter(String newValue) {
        this.newValue = newValue;
    }

    @Override
    public Value convert(Key key, Value value) {
        return new Value(newValue);
    }

    @Override
    public void init() {
    }

}
