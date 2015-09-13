package pl.szczepanik.silencio.mocks;

import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;
import pl.szczepanik.silencio.stubs.StubConverter;

/**
 * Stores key and value passed to this converter.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ConverterHolder extends StubConverter {

    private Key key;
    private Value value;

    @Override
    public Value convert(Key key, Value value) {
        this.key = key;
        this.value = value;
        return value;
    }

    public Key getKey() {
        return key;
    }

    public Value getValue() {
        return value;
    }
}
