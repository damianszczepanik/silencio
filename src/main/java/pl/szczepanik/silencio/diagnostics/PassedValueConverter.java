package pl.szczepanik.silencio.diagnostics;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Diagnostic converter that returns the same value as the one which was passed.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public final class PassedValueConverter implements Converter {

    // Limits the access only to diagnostic package.
    PassedValueConverter() {
    }

    @Override
    public Value convert(Key key, Value value) {
        return value;
    }

    @Override
    public void init() {
    }

}
