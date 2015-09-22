package pl.szczepanik.silencio.diagnostics;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Diagnostic converter that returns blank space <code>' '</code> for every passed value.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public final class WhiteCharConverter implements Converter {

    private final Value value = new Value(" ");

    // Limits the access only to diagnostic package.
    WhiteCharConverter() {
    }

    @Override
    public Value convert(Key key, Value value) {
        return this.value;
    }

    @Override
    public void init() {
    }

}
