package pl.szczepanik.silencio.api;

import java.io.Reader;
import java.io.Writer;

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
