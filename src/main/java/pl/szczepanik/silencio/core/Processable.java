package pl.szczepanik.silencio.core;

/**
 * Defines method that is called by parser to convert {@link Value} into new value.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public interface Processable {

    /**
     * Calls each converters and passes value to convert.
     * 
     * @param key
     *            key
     * @param value
     *            value that should be converted
     * @return converted value
     */
    Value processValue(String key, Object value);
}
