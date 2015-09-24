package pl.szczepanik.silencio.processors.visitors;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Decision;
import pl.szczepanik.silencio.core.ExecutionConfig;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Collects common methods and attributes used by visitors that goes over passed model and visits all nodes.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public abstract class AbstractVisitor {

    private ExecutionConfig[] executionConfigs;

    public void setExecutionConfigs(ExecutionConfig[] executionConfigs) {
        this.executionConfigs = executionConfigs;
    }

    /**
     * Calls each converters and passes value to convert. If no conversion is applied it returns passed object packed
     * into {#link Value}.
     * 
     * @param key
     *            key
     * @param value
     *            value that should be converted
     * @return converted value
     */
    protected Value processValue(Key key, Object value) {
        Value newValue = new Value(value);

        for (ExecutionConfig executionConfig : executionConfigs) {
            boolean shouldConvert = true;
            for (Decision decision : executionConfig.getDecisions()) {
                shouldConvert &= decision.decide(key, newValue);
                if (!shouldConvert) {
                    break;
                }
            }

            if (shouldConvert) {
                for (Converter converter : executionConfig.getConverters()) {
                    newValue = converter.convert(key, newValue);
                }
            }
        }

        return newValue;
    }
}
