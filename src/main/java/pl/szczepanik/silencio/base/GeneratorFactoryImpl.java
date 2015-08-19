package pl.szczepanik.silencio.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import pl.szczepanik.silencio.api.Generator;
import pl.szczepanik.silencio.api.GeneratorFactory;
import pl.szczepanik.silencio.api.SupportedTypes;

/**
 * Default factory implementation that holds generators.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class GeneratorFactoryImpl implements GeneratorFactory {

    private final Map<String, Generator> supportedGenerators = new HashMap<>();

    @Override
    public void register(Generator generator) {
        validateGenerator(generator);
        supportedGenerators.put(generator.getName(), generator);
    }

    @Override
    public void unregister(Generator generator) {
        supportedGenerators.remove(generator.getName());
    }

    @Override
    public void unregisterAll() {
        supportedGenerators.clear();
    }

    @Override
    public Set<Generator> findByType(SupportedTypes type) {
        Set<Generator> matched = new HashSet<>();
        for (Generator generator : supportedGenerators.values()) {
            if (generator.getType().equals(type)) {
                matched.add(generator);
            }
        }
        return matched;
    }

    @Override
    public Generator findByName(String name) {
        return supportedGenerators.get(name);
    }

    private void validateGenerator(Generator generator) {
        if (StringUtils.isEmpty(generator.getName())) {
            throw new IllegalArgumentException("Name of the generator must not be empty!");
        }
        if (generator.getType() == null) {
            throw new IllegalArgumentException("Type of the generator must not be empty!");
        }

        Generator registeredGenerator = findByName(generator.getName());
        if (registeredGenerator != null) {
            throw new IllegalArgumentException(
                    String.format("Generator with name '%s' has been already registered!", generator.getName()));
        }
    }
}
