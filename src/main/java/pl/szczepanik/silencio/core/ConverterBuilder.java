package pl.szczepanik.silencio.core;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.api.Strategy;
import pl.szczepanik.silencio.converters.MakeEmptyConverter;
import pl.szczepanik.silencio.processors.JSONProcessor;

/**
 * Default implementation of class that holds processors.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public final class ConverterBuilder {

    public static Processor build(Format format, Strategy... strategiesToApply) {
        Strategy[] strategyList = {};
        // may happen when calling build(format)
        if (strategiesToApply != null) {
            strategyList = strategiesToApply;
        }
        return new JSONProcessor(strategyList);
    }


    /**
     * Provides list of strategies that are supported by default
     */
    public static final Strategy MAKE_EMPTY = new MakeEmptyConverter();
}
