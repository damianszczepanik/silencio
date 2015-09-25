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
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public final class Builder {

    /**
     * Provides list of converters that are supported by default.
     */
    public static final Converter BLANK = new BlankConverter();
    public static final Converter NUMBER_SEQUENCE = new NumberSequenceConverter();

    private final Format format;

    private final List<Execution> executions = new ArrayList<>();

    public Builder(Format format) {
        this.format = format;
    }

    public Builder append(Decision[] decision, Converter... converters) {
        executions.add(new Execution(decision, converters));
        return this;
    }

    public Builder append(Decision decision, Converter... converters) {
        return append(new Decision[] { decision }, converters);
    }

    public Builder append(Decision decision, Converter converter) {
        return append(decision, new Converter[] { converter });
    }

    public Builder append(Converter... converters) {
        return append(new PositiveDecision(), converters);
    }

    public Builder append(Converter converter) {
        return append(new PositiveDecision(), new Converter[] { converter });
    }

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

    public void clearExecutions() {
        executions.clear();
    }
}
