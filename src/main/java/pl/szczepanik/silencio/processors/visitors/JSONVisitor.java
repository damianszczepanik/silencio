package pl.szczepanik.silencio.processors.visitors;

import java.util.Map;

import pl.szczepanik.silencio.core.Key;

/**
 * Iterates over JSON nodes and calls {@link #processValue(Key, Object)} for each basic node.
 *
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class JSONVisitor extends AbstractJacksonVisitor {

    /**
     * Process passed JSON map and iterates over each node.
     *
     * @param json JSON map to iterate
     */
    public void processJSON(Map<String, Object> json) {
        processMap(json);
    }
}
