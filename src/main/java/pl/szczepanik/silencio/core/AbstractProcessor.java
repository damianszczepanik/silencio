package pl.szczepanik.silencio.core;

import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;

/**
 * Provides basic implementations of methods used by processors.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public abstract class AbstractProcessor implements Processor, Processable {

    protected final Format format;
    protected Converter[] converters;

    private final ProcessorStateMachine stateMachine = new ProcessorStateMachine();

    /**
     * Creates new instance.
     * 
     * @param format
     *            format that will be supported by this processor.
     */
    public AbstractProcessor(Format format) {
        validateFormat(format);
        this.format = format;
    }

    @Override
    public Format getFormat() {
        return format;
    }

    /**
     * Calls {@link Converter#init()} method on each converter.
     */
    protected void initConverties() {
        for (Converter converter : converters) {
            converter.init();
        }
    }

    @Override
    public final void load(Reader reader) {
        realLoad(reader);
    }

    protected abstract void realLoad(Reader reader);

    @Override
    public void setConverters(Converter[] converters) {
        validateConverters(converters);

        // deep copy to prevent manipulating on private list
        this.converters = Arrays.copyOf(converters, converters.length);

        stateMachine.moveToLoaded();
    }

    @Override
    public final void process() {
        stateMachine.validateProcess();
        realProcess();
        stateMachine.moveToProcessed();
    }

    protected abstract void realProcess();

    @Override
    public Value processValue(String key, Object value) {
        Value newValue = new Value(value);
        for (Converter converter : converters) {
            newValue = converter.convert(new Key(key), newValue);
        }

        return newValue;
    }

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
