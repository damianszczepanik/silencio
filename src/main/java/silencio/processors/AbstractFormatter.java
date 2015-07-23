package silencio.processors;

import java.io.Writer;
import java.util.Map;

/**
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 * 
 */
public abstract class AbstractFormatter {

    // list of standard types
    protected final static DataType JSON_DATA_TYPE = new JSONDataType();

    /** Gets the data type supported by the processor. */
    public abstract DataType getDataType();

    /**
     * Converts passed content using given formatter.
     * 
     * @param content
     *            data that shall be formatted
     * 
     * @param writter
     *            output to which the converted content will be written
     */
    public abstract void process(Map content, Writer writter);
}
