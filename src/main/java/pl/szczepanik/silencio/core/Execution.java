package pl.szczepanik.silencio.core;

import org.apache.commons.lang3.ArrayUtils;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Decision;

/**
 * Keeps array of the {@link Decision} with array of the @{link {@link Converter} that will be used for single
 * conversion.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class Execution {

    private final Decision[] decisions;
    private final Converter[] converters;

    /**
     * Creates execution with pairs of decisions and converters.
     * 
     * @param decisions
     *            list of decisions use for this execution
     * @param converters
     *            list of converters use for this execution
     */
    public Execution(Decision[] decisions, Converter[] converters) {
        validateDecisions(decisions);
        validateConverters(converters);

        this.decisions = decisions;
        this.converters = converters;
    }

    /**
     * Creates execution with pairs of decisions and converters.
     * 
     * @param decision
     *            single decision use for this execution
     * @param converters
     *            list of converters use for this execution
     */
    public Execution(Decision decision, Converter[] converters) {
        this(new Decision[] { decision }, converters);
    }

    /**
     * Creates execution with pairs of decisions and converters.
     * 
     * @param decision
     *            single decision use for this execution
     * @param converter
     *            single converter use for this execution
     */
    public Execution(Decision decision, Converter converter) {
        this(new Decision[] { decision }, new Converter[] { converter });
    }

    /**
     * Gets all decisions for this execution.
     * 
     * @return all decisions
     */
    public Decision[] getDecisions() {
        return decisions;
    }

    /**
     * Gets all converters for this execution.
     * 
     * @return all converters
     */
    public Converter[] getConverters() {
        return converters;
    }

    private void validateDecisions(Decision[] decisions) {
        if (ArrayUtils.isEmpty(decisions)) {
            throw new IntegrityException("Array with Decisions must not be empty!");
        }
        if (ArrayUtils.contains(decisions, null)) {
            throw new IntegrityException("None of passed Decision can be null!");
        }
    }

    private void validateConverters(Converter[] converters) {
        if (ArrayUtils.isEmpty(converters)) {
            throw new IntegrityException("Array with Converters must not be empty!");
        }
        if (ArrayUtils.contains(converters, null)) {
            throw new IntegrityException(String.format("None of passed Converter can be null!"));
        }
    }
}
