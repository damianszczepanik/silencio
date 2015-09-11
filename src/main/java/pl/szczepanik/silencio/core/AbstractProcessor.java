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
    protected Converter[] converters;

    private final ProcessorStateMachine stateMachine = new ProcessorStateMachine();

    public AbstractProcessor(Format format) {
        validateFormat(format);
        this.format = format;
    }

    @Override
    public Format getFormat() {
        return format;
    }

    @Override
    public void setConverters(Converter[] converters) {
        validateConverters(converters);

        // deep copy to prevent manipulating on private list
        this.converters = Arrays.copyOf(converters, converters.length);
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

    /**
     * Calls each converters and passes value to convert.
     * 
     * @param key
     *            key
     * @param value
     *            value that should be converted
     * @return converted value
     */
    protected Value processValue(String key, Object value) {
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
