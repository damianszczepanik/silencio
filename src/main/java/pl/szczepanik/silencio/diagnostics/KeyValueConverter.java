package pl.szczepanik.silencio.diagnostics;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Diagnostic converter that returns new instance of {@link Value} object created from passed key.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class KeyValueConverter implements Converter {

    /**
     * Limits the access to diagnostic package.
     */
    KeyValueConverter() {
    }

    @Override
    public Value convert(Key key, Value value) {
        return new Value(key.getKey());
    }

    @Override
    public void init() {
    }

}
