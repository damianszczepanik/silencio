package pl.szczepanik.silencio.api;

import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Decides whether values passed to {@link Converter} should be applied or the conversion should be skipped for this
 * value and key.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public interface Decision {

    /**
     * Based on passed key and value takes decision if the conversion should be applied or not.
     * @param key key mapped with passed value
     * @param value value that shall be converted
     * @return true if decision is accepted, false otherwise
     */
     boolean decide(Key key, Value value);
}
