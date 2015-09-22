package pl.szczepanik.silencio.mocks;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Stores key and value passed to this converter.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ConverterVisitor implements Converter {

    private Key key;
    private Value value;

    private int visitCounter;

    @Override
    public Value convert(Key key, Value value) {
        this.key = key;
        this.value = value;

        visitCounter++;

        return value;
    }

    public Key getKey() {
        return key;
    }

    public Value getValue() {
        return value;
    }

    public int getVisitCounter() {
        return visitCounter;
    }

    public void reset() {
        key = null;
        value = null;
        visitCounter = 0;
    }

    @Override
    public void init() {
    }
}
