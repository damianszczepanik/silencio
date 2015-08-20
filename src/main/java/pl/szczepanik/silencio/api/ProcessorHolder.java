package pl.szczepanik.silencio.api;

import java.util.Set;

/**
 * Contract for class that delivers list of available processors.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public interface ProcessorHolder {

    /**
     * Register processor into factory. May be used to register custom processors.
     * 
     * @param processor
     *            processor that should be register.
     * @throws IllegalArgumentException when processor has empty name or type or given name has been already registered
     */
    public void register(Processor processor);

    /**
     * Unregister processor from factory.
     * 
     * @param processor
     *            processor that should be unregister.
     */
    public void unregister(Processor processor);

    /**
     * Unregisters all processors from factory.
     */
    public void unregisterAll();

    /**
     * Returns set of available processors filtered out by {@link Format}.
     * 
     * @param type
     *            type used for filtering
     * @return set of matched processors or empty set when none processor matched
     */
    public Set<Processor> findByFormat(Format type);

    /**
     * Returns processor with given name.
     * 
     * @param type
     *            name used for filtering
     */
    public Processor findByName(String name);

    /**
     * Returns all formats that were registered in factory.
     */
    public Set<Format> getRegisteredFormats();
}
