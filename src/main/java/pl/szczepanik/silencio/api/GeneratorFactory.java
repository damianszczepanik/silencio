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
     * @throws IllegalArgumentException when generator has empty name or type or given name has been already registered
     */
    public void register(Generator generator);

    /**
     * Unregister generator from factory.
     * 
     * @param generator
     *            generator that should be unregister.
     */
    public void unregister(Generator generator);

    /**
     * Unregisters all generators from factory.
     */
    public void unregisterAll();

    /**
     * Returns set of available generators filtered out by {@link Format}.
     * 
     * @param type
     *            type used for filtering
     * @return set of matched generators or empty set when none generator matched
     */
    public Set<Generator> findByFormat(Format type);

    /**
     * Returns generator with given name.
     * 
     * @param type
     *            name used for filtering
     */
    public Generator findByName(String name);

    /**
     * Returns all formats that were registered in factory.
     */
    public Set<Format> getRegisteredFormats();
}
