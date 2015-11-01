package pl.szczepanik.silencio.core;

import java.util.ArrayList;
import java.util.List;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Decision;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.converters.BlankConverter;
import pl.szczepanik.silencio.converters.NumberSequenceConverter;
import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.processors.JSONProcessor;
import pl.szczepanik.silencio.processors.PropertiesProcessor;

/**
 * Default implementation of class that holds processors.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public final class Builder {

    /** Blank converter that clears value for each key. */
    public static final Converter BLANK = new BlankConverter();
    /** NumberSequence converter that changes values into sequential numbers. */
    public static final Converter NUMBER_SEQUENCE = new NumberSequenceConverter();

    private final Format format;

    private final List<Execution> executions = new ArrayList<>();

    /**
     * Creates new builder from given format.
     * 
     * @param format
     *            format for this builder
     */
    public Builder(Format format) {
        this.format = format;
    }

    /**
     * Appends list of decisions and converters into the executions as the new position.
     * 
     * @param decisions
     *            decisions for next execution
     * @param converters
     *            converters for next execution
     * @return instance of current builder
     */
    public Builder with(Decision[] decisions, Converter[] converters) {
        executions.add(new Execution(decisions, converters));
        return this;
    }

    /**
     * Appends single decision and converters into the executions as the new position.
     * 
     * @param decision
     *            decision for next execution
     * @param converters
     *            converters for next execution
     * @return instance of current builder
     */
    public Builder with(Decision decision, Converter... converters) {
        return with(new Decision[] { decision }, converters);
    }

    /**
     * Appends single decision and converter into the executions as the new position.
     * 
     * @param decision
     *            decision for next execution
     * @param converter
     *            converter for next execution
     * @return instance of current builder
     */
    public Builder with(Decision decision, Converter converter) {
        return with(decision, new Converter[] { converter });
    }

    /**
     * Appends converters into the executions as the new position.
     * 
     * @param converters
     *            converters for next execution
     * @return instance of current builder
     */
    public Builder with(Converter... converters) {
        return with(new PositiveDecision(), converters);
    }

    /**
     * Appends single converter into the executions as the new position.
     * 
     * @param converter
     *            converter for next execution
     * @return instance of current builder
     */
    public Builder with(Converter converter) {
        return with(new PositiveDecision(), new Converter[] { converter });
    }

    /**
     * Builds the processor based on passed values.
     * 
     * @return created processor
     */
    public Processor build() {
        Processor processor;
        if (Format.JSON.equals(format)) {
            processor = new JSONProcessor();
        } else if (Format.PROPERTIES.equals(format)) {
            processor = new PropertiesProcessor();
        } else {
            throw new IntegrityException("Unsupported format: " + format.getName());
        }

        Execution[] executionsArray = new Execution[executions.size()];
        executions.toArray(executionsArray);
        processor.setConfiguration(new Configuration(executionsArray));

        return processor;
    }

    /** Removes all decisions and converters from this builder. */
    public void clearExecutions() {
        executions.clear();
    }
}
