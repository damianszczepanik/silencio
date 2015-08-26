package pl.szczepanik.silencio.core;

import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.api.Strategy;

public abstract class AbstractProcessor implements Processor {

    protected final Format format;
    protected final Strategy[] strategies;

    private final ProcessorStateMachine stateMachine = new ProcessorStateMachine();

    public AbstractProcessor(Format format, Strategy[] strategies) {
        validateFormat(format);
        validateStrategies(strategies, format);

        this.format = format;
        // deep copy to prevent manipulating on private list
        this.strategies = Arrays.copyOf(strategies, strategies.length);
    }

    @Override
    public Format getFormat() {
        return format;
    }

    /**
     * Calls {@link Strategy#reset()} method on each strategies.
     */
    protected void resetStrategies() {
        for (Strategy strategy : strategies) {
            strategy.reset();
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

    private void validateStrategies(Strategy[] strategies, Format format) {
        if (strategies == null || strategies.length == 0) {
            throw new IntegrityException("Array with strategies must not be empty!");
        }
        for (int i = 0; i < strategies.length; i++) {
            if (strategies[i] == null) {
                throw new IntegrityException(String.format("Strategy passed on index %d is null!", i));
            }
            if (!strategies[i].getFormat().equals(format)) {
                throw new IntegrityException(
                        String.format("Strategy passed on index %d supports format '%s' while expecting '%s'!", i,
                                strategies[i].getFormat().getName(), format.getName()));
            }
        }
    }
}
