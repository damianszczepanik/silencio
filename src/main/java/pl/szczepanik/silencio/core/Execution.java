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
public class Execution {

    private final Decision[] decisions;
    private final Converter[] converters;

    public Execution(Decision[] decisions, Converter[] converters) {
        validateDecisions(decisions);
        validateConverters(converters);

        this.decisions = decisions;
        this.converters = converters;
    }

    public Execution(Decision decision, Converter[] converters) {
        this(new Decision[] { decision }, converters);
    }

    public Execution(Decision decision, Converter converter) {
        this(new Decision[] { decision }, new Converter[] { converter });
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
