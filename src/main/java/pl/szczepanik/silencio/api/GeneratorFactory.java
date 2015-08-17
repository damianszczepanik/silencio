package pl.szczepanik.silencio.api;

import java.util.Set;

/**
 * Contract for class that delivers list of available generators.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public interface GeneratorFactory {

    /**
     * Register generator into factory. May be used to register custom generators.
     * 
     * @param generator
     *            generator that should be register.
     * @return true if generator was registered, false if generator was already registered
     */
    public boolean register(Generator generator);

    /**
     * Unregister generator from factory.
     * 
     * @param generator
     *            generator that should be unregister.
     * @return true if generator was unregistered, false if generator was not found
     */
    public boolean unregister(Generator generator);

    /**
     * Returns set of available generators filtered out by name.
     * 
     * @param type
     *            type used for filtering
     * @return set of matched generators or empty set when none generator matched
     */
    public Set<Generator> findBy(String type);

    /**
     * Returns set of available generators filtered out by {@link SupportedTypes}.
     * 
     * @param type
     *            type used for filtering
     * @return set of matched generators or empty set when none generator matched
     */
    public Set<Generator> findBy(SupportedTypes type);

    /**
     * Returns set of all registered generators.
     * 
     * @param type
     *            type used for filtering
     * @return set of all registered generators or empty set when none generator found
     */
    public Set<Generator> findAll();
}
