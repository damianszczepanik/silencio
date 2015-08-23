package pl.szczepanik.silencio.stubs;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Value;
import pl.szczepanik.silencio.core.AbstractStrategy;

/**
 * Stub for {@link AbstractStrategy} that has only stub methods.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class StubAbstractStrategy extends AbstractStrategy {

    public StubAbstractStrategy(Format format) {
        super(format);
    }

    @Override
    public Value convert(Value value) {
        return value;
    }

    @Override
    public void reset() {
    }

}
