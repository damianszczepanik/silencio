package pl.szczepanik.silencio.core;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Strategy;

/**
 * Contract for all strategies.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public abstract class AbstractStrategy implements Strategy {

    protected final Format format;

    public AbstractStrategy(Format format) {
        validateFormat(format);

        this.format = format;
    }

    /**
     * Gets supported format. Pair format and name should be unique.
     */
    public final Format getFormat() {
        return format;
    }

    private void validateFormat(Format format) {
        if (format == null) {
            throw new IntegrityException("Format of the strategy must not be null!");
        }
    }
}
