package pl.szczepanik.silencio.stubs;

import pl.szczepanik.silencio.api.Strategy;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Stub strategy that has only stub methods.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class StubConverter implements Strategy {

    @Override
    public Value convert(Key key, Value value) {
        return value;
    }

    @Override
    public void init() {
    }

}