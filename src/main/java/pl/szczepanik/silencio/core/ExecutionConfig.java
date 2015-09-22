package pl.szczepanik.silencio.core;

import org.apache.commons.lang3.ArrayUtils;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Decision;

/**
 * Keeps array of the {@link Decision} with array of the @{link {@link Converter} that will be used for single
 * conversion.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ExecutionConfig {

    private final Decision[] decisions;
    private final Converter[] converters;
    
    public ExecutionConfig(Decision[] decisions, Converter[] converters) {
        validateDecisions(decisions);
        validateConverters(converters);

        this.decisions = decisions;
        this.converters = converters;
    }

    public ExecutionConfig(Decision decision, Converter[] converters) {
        this(new Decision[] { decision }, converters);
    }


    public Decision[] getDecisions() {
        return decisions;
    }

    public Converter[] getConverters() {
        return converters;
    }


    private void validateDecisions(Decision[] decisions) {
        if (ArrayUtils.isEmpty(decisions)) {
            throw new IntegrityException("Array with Decisions must not be empty!");
        }
        if (ArrayUtils.contains(decisions, null)) {
            throw new IntegrityException(String.format("Passed Decision is null!"));
        }
    }

    private void validateConverters(Converter[] converters) {
        if (ArrayUtils.isEmpty(converters)) {
            throw new IntegrityException("Array with Converters must not be empty!");
        }
        if (ArrayUtils.contains(converters, null)) {
            throw new IntegrityException(String.format("Passed Converter is null!"));
        }
    }
}
