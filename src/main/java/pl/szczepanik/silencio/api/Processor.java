package pl.szczepanik.silencio.api;

import java.io.Reader;
import java.io.Writer;

import pl.szczepanik.silencio.core.ExecutionConfig;

/**
 * Contract for processor that converts content.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public interface Processor {

    /**
     * Gets format of the processor.
     * @return format supported by this processor
     */
    Format getFormat();

    /**
     * Defines list of {@link ExecutionConfig} that should be used by this processor.
     * @param executionConfig list of ExecutionConfig that should be applied
     */
    void setExecutionConfig(ExecutionConfig[] executionConfig);

    /**
     * Reads content that will be processed.
     * @param reader content to process
     */
    void load(Reader reader);

    /**
     * Executes current processor.
     */
    void process();

    /**
     * Writes results into passed writer.
     */
    void write(Writer writer);
}
