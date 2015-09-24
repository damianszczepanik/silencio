package pl.szczepanik.silencio.processors.visitors;

import java.util.Properties;

import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Iterates over Properties nodes and calls {@link #processValue(Key, Object)} for each basic node.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class PropertiesVisitor extends AbstractVisitor {

    public void process(Properties properties) {
        for (Object key : properties.keySet()) {
            Value newValue = processValue(new Key(key.toString()),
                    properties.getProperty(key.toString()));
            properties.put(key, newValue.getValue().toString());
        }
    }
}
