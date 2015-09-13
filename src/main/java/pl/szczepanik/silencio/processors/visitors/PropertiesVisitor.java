package pl.szczepanik.silencio.processors.visitors;

import java.util.Properties;

import pl.szczepanik.silencio.core.Processable;
import pl.szczepanik.silencio.core.Value;

/**
 * Iterates over Properties nodes and calls {@link Processable} for each basic node.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class PropertiesVisitor extends AbstractVisitor {

    public PropertiesVisitor(Processable processable) {
        super(processable);
    }

    public void process(Properties properties) {
        for (Object key : properties.keySet()) {
            Value newValue = processable.processValue(key.toString(), properties.getProperty(key.toString()));
            properties.put(key, newValue.getValue().toString());
        }
    }
}
