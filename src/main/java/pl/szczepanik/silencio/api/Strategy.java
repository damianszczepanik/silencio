package pl.szczepanik.silencio.api;

import pl.szczepanik.silencio.core.Element;

/**
 * Contract for all strategies.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public interface Strategy {

    /**
     * Gets supported format. Pair format and name should be unique.
     */
     Format getFormat();

    /**
     * Converts value passed to this strategy into new value.
     * @param value value that shall be converted
     * @return new value
     */
     Element convert(Element value);

    /**
     * Called by the processor each time new content is loaded.
     * This method should set local variables to default before values from new file will be passed.
     */
      void reset();
}
