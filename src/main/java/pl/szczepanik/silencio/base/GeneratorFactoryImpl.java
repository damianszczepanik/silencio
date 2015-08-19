package pl.szczepanik.silencio.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Generator;
import pl.szczepanik.silencio.api.GeneratorFactory;

/**
 * Default factory implementation that holds generators.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class GeneratorFactoryImpl implements GeneratorFactory {

    private final Map<String, Generator> supportedGenerators = new HashMap<>();

    private final Set<Format> supportedFormats = new HashSet<>();

    @Override
    public void register(Generator generator) {
        validateGenerator(generator);
        supportedGenerators.put(generator.getName(), generator);
        supportedFormats.add(generator.getFormat());
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
    public Set<Generator> findByFormat(Format type) {
        Set<Generator> matched = new HashSet<>();
        for (Generator generator : supportedGenerators.values()) {
            if (generator.getFormat().equals(type)) {
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
        if (generator.getFormat() == null) {
            throw new IllegalArgumentException("Type of the generator must not be empty!");
        }

        Generator registeredGenerator = findByName(generator.getName());
        if (registeredGenerator != null) {
            throw new IllegalArgumentException(
                    String.format("Generator with name '%s' has been already registered!", generator.getName()));
        }
    }

    @Override
    public Set<Format> getRegisteredFormats() {
        return Collections.unmodifiableSet(supportedFormats);
    }
}
