package pl.szczepanik.silencio.core;

import java.io.Reader;
import java.io.Writer;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;

/**
 * Provides basic implementations of methods used by processors.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public abstract class AbstractProcessor implements Processor {

    /** Format used by this type of processor. */
    protected final Format format;

    /** Configuration used by current processor. */
    protected Configuration configuration;

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
        for (Execution execution : configuration.getExecutions()) {
            for (Converter converter : execution.getConverters()) {
                converter.init();
            }
        }
    }

    @Override
    public final void load(Reader reader) {
        realLoad(reader);
    }

    /**
     * Method implemented by processors which is responsible only for loading content from reader.
     * @param reader reader with the content to read
     */
    protected abstract void realLoad(Reader reader);

    @Override
    public void setConfiguration(Configuration configuration) {
        validateConfiguration(configuration);

        // deep copy to prevent manipulating on private list
        this.configuration = configuration;

        stateMachine.moveToLoaded();
    }

    @Override
    public final void process() {
        stateMachine.validateProcess();
        initConverties();
        realProcess();
        stateMachine.moveToProcessed();
    }

    /**
     * Method implemented by processors which is responsible only for processing content.
     */
    protected abstract void realProcess();

    @Override
    public final void write(Writer writer) {
        stateMachine.validateWrite();
        realWrite(writer);
    }

    /**
     * Method implemented by processors which is responsible only for writing results into the writer.
     * @param writer writer to which the content will be written
     */
    protected abstract void realWrite(Writer writer);

    private void validateFormat(Format format) {
        if (format == null) {
            throw new IntegrityException("Format must not be null!");
        }
    }

    private void validateConfiguration(Configuration configuration) {
        if (configuration == null) {
            throw new IntegrityException("Configuration must not be null!");
        }
    }

}
