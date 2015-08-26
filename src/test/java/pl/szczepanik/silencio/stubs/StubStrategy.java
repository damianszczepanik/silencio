package pl.szczepanik.silencio.stubs;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.core.AbstractStrategy;
import pl.szczepanik.silencio.core.Element;

/**
 * Stub strategy that has only stub methods.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class StubStrategy extends AbstractStrategy {

    public StubStrategy(Format type) {
        super(type);
    }

    @Override
    public Element convert(Element value) {
        return value;
    }

    @Override
    public void reset() {
    }

}
