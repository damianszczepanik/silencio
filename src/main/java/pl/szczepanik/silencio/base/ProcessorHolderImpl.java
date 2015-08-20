package pl.szczepanik.silencio.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.api.ProcessorHolder;

/**
 * Default implementation of class that holds processors.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ProcessorHolderImpl implements ProcessorHolder {

    private final Map<String, Processor> processors = new HashMap<>();

    private final Set<Format> formats = new HashSet<>();

    @Override
    public void register(Processor processor) {
        validateProcessor(processor);
        processors.put(processor.getName(), processor);
        formats.add(processor.getFormat());
    }

    @Override
    public void unregister(Processor processor) {
        processors.remove(processor.getName());
    }

    @Override
    public void unregisterAll() {
        processors.clear();
    }

    @Override
    public Set<Processor> findByFormat(Format type) {
        Set<Processor> matched = new HashSet<>();
        for (Processor processor : processors.values()) {
            if (processor.getFormat().equals(type)) {
                matched.add(processor);
            }
        }
        return matched;
    }

    @Override
    public Processor findByName(String name) {
        return processors.get(name);
    }

    private void validateProcessor(Processor proocessor) {
        if (StringUtils.isEmpty(proocessor.getName())) {
            throw new IllegalArgumentException("Name of the processor must not be empty!");
        }
        if (proocessor.getFormat() == null) {
            throw new IllegalArgumentException("Type of the processor must not be empty!");
        }

        Processor registeredProcessor = findByName(proocessor.getName());
        if (registeredProcessor != null) {
            throw new IllegalArgumentException(
                    String.format("Processor with name '%s' has been already registered!", proocessor.getName()));
        }
    }

    @Override
    public Set<Format> getRegisteredFormats() {
        return Collections.unmodifiableSet(formats);
    }
}
