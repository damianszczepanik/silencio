package pl.szczepanik.silencio.diagnostics;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Diagnostic converter that returns new instance of {@link Value} object created from <code>null>code> value.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class NullValueConverter implements Converter {

    /**
     * Limits the access to diagnostic package.
     */
    NullValueConverter() {
    }

    @Override
    public Value convert(Key key, Value value) {
        return new Value(null);
    }

    @Override
    public void init() {
    }

}
