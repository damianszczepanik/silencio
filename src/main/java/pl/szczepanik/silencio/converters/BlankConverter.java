package pl.szczepanik.silencio.converters;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Converter that changes each passed value into empty string ("").
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class BlankConverter implements Converter {

    @Override
    public Value convert(Key key, Value value) {
        return new Value("");
    }

    @Override
    public void init() {
        // This method is intentionally empty, because this class is stateless
    }

}
