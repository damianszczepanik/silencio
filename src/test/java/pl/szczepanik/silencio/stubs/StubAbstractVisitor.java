package pl.szczepanik.silencio.stubs;

import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;
import pl.szczepanik.silencio.processors.visitors.AbstractVisitor;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class StubAbstractVisitor extends AbstractVisitor {

    @Override
    // make method public, accessible for tests without reflection
    public Value processValue(Key key, Object value) {
        return super.processValue(key, value);
    }
}
