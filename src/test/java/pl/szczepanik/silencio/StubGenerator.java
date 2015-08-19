package pl.szczepanik.silencio;

import pl.szczepanik.silencio.api.Generator;
import pl.szczepanik.silencio.api.Result;
import pl.szczepanik.silencio.api.SupportedTypes;

/**
 * Stub generator that does only returns passed name.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class StubGenerator implements Generator {

    private final SupportedTypes type;
    private final String name;

    public StubGenerator(SupportedTypes type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public SupportedTypes getType() {
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
