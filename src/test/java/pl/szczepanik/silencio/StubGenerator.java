package pl.szczepanik.silencio;

import pl.szczepanik.silencio.api.Generator;
import pl.szczepanik.silencio.api.Result;
import pl.szczepanik.silencio.api.Format;

/**
 * Stub generator that does only returns passed name.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class StubGenerator implements Generator {

    private final Format type;
    private final String name;

    public StubGenerator(Format type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Format getFormat() {
        return type;
    }

    @Override
    public String getName() {
        return name;
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
