package pl.szczepanik.silencio.api;

import pl.szczepanik.silencio.base.GeneratorFactoryImpl;

/**
 * Delivers default instance of {@link GeneratorFactory}.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public final class DefaultFactory {

    private final static GeneratorFactoryImpl factory = new GeneratorFactoryImpl();

    /**
     * Returns instance of default factory.
     */
    public static GeneratorFactory getInstance() {
        return factory;
    }
}
