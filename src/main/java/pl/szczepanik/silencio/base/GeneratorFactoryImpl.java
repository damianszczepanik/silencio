package pl.szczepanik.silencio.base;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import pl.szczepanik.silencio.api.Generator;
import pl.szczepanik.silencio.api.GeneratorFactory;
import pl.szczepanik.silencio.api.SupportedTypes;

public class GeneratorFactoryImpl implements GeneratorFactory {

    private final Set<Generator> supportedGenerators = new HashSet<>();

    @Override
    public boolean register(Generator generator) {
        return supportedGenerators.add(generator);
    }

    @Override
    public boolean unregister(Generator generator) {
        return supportedGenerators.remove(generator);
    }

    @Override
    public Set<Generator> findBy(String type) {
        Set<Generator> matched = new HashSet<>();
        for (Generator generator : supportedGenerators) {
            if (generator.getType().equals(type)) {
                matched.add(generator);
            }
        }
        return matched;
    }

    @Override
    public Set<Generator> findBy(SupportedTypes type) {
        return findBy(type.name());
    }

    @Override
    public Set<Generator> findAll() {
        return Collections.unmodifiableSet(supportedGenerators);
    }

}
