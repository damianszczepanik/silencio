package pl.szczepanik.silencio.stubs;

import pl.szczepanik.silencio.core.Processable;
import pl.szczepanik.silencio.core.Value;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class StubProcessable implements Processable {

    @Override
    public Value processValue(String key, Object value) {
        return new Value("");
    }

}
