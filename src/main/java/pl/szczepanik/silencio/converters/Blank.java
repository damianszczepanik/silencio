package pl.szczepanik.silencio.converters;

import pl.szczepanik.silencio.api.Strategy;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Provides converter that converts each passed value into empty string ("").
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class Blank implements Strategy {

    @Override
    public Value convert(Key key, Value value) {
        return new Value("");
    }

    @Override
    public void init() {
    }

}
