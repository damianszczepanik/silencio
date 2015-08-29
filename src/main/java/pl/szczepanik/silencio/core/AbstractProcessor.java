package pl.szczepanik.silencio.core;

import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;

/**
 * Provides helper methods for processors.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public abstract class AbstractProcessor implements Processor {

    protected final Format format;
    protected final Converter[] converters;

    private final ProcessorStateMachine stateMachine = new ProcessorStateMachine();

    public AbstractProcessor(Format format, Converter[] converties) {
        validateFormat(format);
        validateConverters(converties);

        this.format = format;
        // deep copy to prevent manipulating on private list
        this.converters = Arrays.copyOf(converties, converties.length);
    }

    @Override
    public Format getFormat() {
        return format;
    }

    /**
     * Calls {@link Converter#reset()} method on each converter.
     */
    protected void initConverties() {
        for (Converter converter : converters) {
            converter.init();
        }
    }

    @Override
    public final void load(Reader reader) {
        realLoad(reader);
        stateMachine.moveToLoaded();
    }

    protected abstract void realLoad(Reader reader);

    @Override
    public final void process() {
        stateMachine.validateProcess();
        realProcess();
        stateMachine.moveToProcessed();
    }

    protected abstract void realProcess();

    @Override
    public final void write(Writer writer) {
        stateMachine.validateWrite();
        realWrite(writer);
    }

    protected abstract void realWrite(Writer writer);

    private void validateFormat(Format format) {
        if (format == null) {
            throw new IntegrityException("Format must not be null!");
        }
    }

    private void validateConverters(Converter[] converters) {
        if (converters == null || converters.length == 0) {
            throw new IntegrityException("Array with converters must not be empty!");
        }
        for (int i = 0; i < converters.length; i++) {
            if (converters[i] == null) {
                throw new IntegrityException(String.format("Converter passed on index %d is null!", i));
            }
        }
    }
}
