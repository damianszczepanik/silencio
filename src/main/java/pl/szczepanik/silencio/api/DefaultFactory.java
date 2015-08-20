package pl.szczepanik.silencio.api;

import pl.szczepanik.silencio.base.ProcessorHolderImpl;

/**
 * Delivers default instance of {@link ProcessorHolder}.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public final class DefaultFactory {

    private final static ProcessorHolderImpl factory = new ProcessorHolderImpl();

    private DefaultFactory() {
    }

    /**
     * Returns instance of default factory.
     */
    public static ProcessorHolder getInstance() {
        return factory;
    }
}
