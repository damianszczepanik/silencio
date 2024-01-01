package pl.szczepanik.silencio.stubs;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Stub converter that has only stub methods.
 *
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class StubConverter implements Converter {

    @Override
    public Value convert(Key key, Value value) {
        return value;
    }

    @Override
    public void init() {
        // This method is intentionally empty, it is overridden only to meet Converter interface
    }
}
