package pl.szczepanik.silencio;

import pl.szczepanik.silencio.api.Generator;
import pl.szczepanik.silencio.api.Result;

/**
 * Stub generator that does only returns passed name.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class StubGenerator implements Generator {

    private final String type;

    public StubGenerator(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Generator decorate(Generator generator) {
        return generator;
    }

    @Override
    public Result process() {
        return null;
    }

}
