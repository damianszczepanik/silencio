package pl.szczepanik.silencio.processors.visitors;

import java.util.Map;

import pl.szczepanik.silencio.core.Key;

/**
 * Iterates over YAML nodes and calls {@link #processValue(Key, Object)} for each basic node.
 *
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class YAMLVisitor extends AbstractJacksonVisitor {

    /**
     * Process passed YAML map and iterates over each node.
     *
     * @param yaml YAML map to iterate
     */
    public void processYaml(Map<String, Object> yaml) {
        processMap(yaml);
    }
}
