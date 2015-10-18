package pl.szczepanik.silencio.api;

import java.io.Reader;
import java.io.Writer;

import pl.szczepanik.silencio.core.Configuration;

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
     * Defines list of {@link Configuration} that should be used by this processor.
     * @param configuration list of Configuration that should be applied
     */
    void setConfiguration(Configuration configuration);

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
     * @param writer writer where the results will be stored
     */
    void write(Writer writer);
}
