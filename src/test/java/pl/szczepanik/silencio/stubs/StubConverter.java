package pl.szczepanik.silencio.stubs;

import pl.szczepanik.silencio.api.Strategy;
import pl.szczepanik.silencio.core.Element;

/**
 * Stub strategy that has only stub methods.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class StubConverter implements Strategy {

    @Override
    public Element convert(Element value) {
        return value;
    }

    @Override
    public void init() {
    }

}
