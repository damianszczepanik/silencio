package pl.szczepanik.silencio;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.api.Product;

/**
 * Stub processor that does only returns passed name.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class StubProcessor implements Processor {

    private final Format type;
    private final String name;

    public StubProcessor(Format type, String name) {
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
    public Processor decorate(Processor processor) {
        return processor;
    }

    @Override
    public Product process() {
        return null;
    }

}
