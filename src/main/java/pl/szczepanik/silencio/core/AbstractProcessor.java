package pl.szczepanik.silencio.core;

import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;

/**
 * Provides basic implementations of methods used by processors.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public abstract class AbstractProcessor implements Processor {

    protected final Format format;

    protected ExecutionConfig[] executionConfigs;

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
        for (ExecutionConfig executionConfig : executionConfigs) {
            for (Converter converter : executionConfig.getConverters()) {
                converter.init();
            }
        }
    }

    @Override
    public final void load(Reader reader) {
        realLoad(reader);
    }

    protected abstract void realLoad(Reader reader);

    @Override
    public void setExecutionConfig(ExecutionConfig[] executionConfig) {
        validateExecutionConfig(executionConfig);

        // deep copy to prevent manipulating on private list
        this.executionConfigs = Arrays.copyOf(executionConfig, executionConfig.length);

        stateMachine.moveToLoaded();
    }

    @Override
    public final void process() {
        stateMachine.validateProcess();
        initConverties();
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

    private void validateExecutionConfig(ExecutionConfig[] executionConfig) {
        if (ArrayUtils.isEmpty(executionConfig)) {
            throw new IntegrityException("Array with ExecutionConfig must not be empty!");
        }
    }

}
