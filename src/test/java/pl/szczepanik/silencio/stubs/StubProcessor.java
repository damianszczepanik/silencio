package pl.szczepanik.silencio.stubs;

import java.io.Reader;
import java.io.Writer;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.core.Configuration;
import pl.szczepanik.silencio.core.Execution;
import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.processors.AbstractProcessor;

/**
 * Stub of @link {@link AbstractProcessor} that has only stub methods.
 *
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class StubProcessor extends AbstractProcessor {

    public StubProcessor(Format format, Converter[] converters) {
        this(format);
        Execution execution = new Execution(new PositiveDecision(), converters);
        setConfiguration(new Configuration(execution));
    }

    public StubProcessor(Format format) {
        super(format);
    }

    @Override
    public void realLoad(Reader reader) {
        // This method is intentionally empty, it is overridden only to meet AbstractProcessor class
    }

    @Override
    public void realProcess() {
        // This method is intentionally empty, it is overridden only to meet AbstractProcessor class
    }

    @Override
    public void realWrite(Writer writer) {
        // This method is intentionally empty, it is overridden only to meet AbstractProcessor class
    }

}
