package pl.szczepanik.silencio.api;

import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Contract for all converters.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public interface Converter {

    /**
     * Converts value passed to this converter into new value.
     * @param key key mapped with passed value
     * @param value value that shall be converted
     * @return changed value
     */
     Value convert(Key key, Value value);

    /**
     * Called by the processor each time new content is loaded.
     * This method should set local variables to default before values from new file will be passed.
     */
      void init();
}
