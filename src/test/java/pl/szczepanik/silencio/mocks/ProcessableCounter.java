package pl.szczepanik.silencio.mocks;

import pl.szczepanik.silencio.core.Processable;
import pl.szczepanik.silencio.core.Value;

/**
 * Counts how many {{@link #finalize()} {@link #processValue(String, Object)} method has been invoked.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ProcessableCounter implements Processable {

    private int visitCounter;

    @Override
    public Value processValue(String key, Object value) {
        visitCounter++;
        return new Value(value);
    }

    public int getVisitCounter() {
        return visitCounter;
    }
}
