package silencio.processors;


/**
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 * 
 */
public abstract class AbstractConverter {

    /**
     * Adds passed value and index it for future processing.
     * 
     * @param value
     *            value of the key that shall be indexed
     */
    public abstract void register(String value);

    /**
     * Prepares {@link #register(String) passed} values into the format or
     * values which will be available by {@link #convert(String)}.
     */
    public abstract void build();

    /**
     * Returns converted value.
     * 
     * @param value
     *            value that should be converted.
     * @return converted value
     */
    public abstract String getConvertedFor(String value);
}
