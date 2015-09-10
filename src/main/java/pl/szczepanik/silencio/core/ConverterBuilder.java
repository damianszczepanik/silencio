package pl.szczepanik.silencio.core;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.converters.BlankConverter;
import pl.szczepanik.silencio.converters.NumberSequenceConverter;
import pl.szczepanik.silencio.processors.JSONProcessor;
import pl.szczepanik.silencio.processors.PropertiesProcessor;

/**
 * Default implementation of class that holds processors.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public final class ConverterBuilder {

    public static Processor build(Format format, Converter... converterToApply) {
        Converter[] converterList = {};
        // may happen when calling build(format)
        if (converterToApply != null) {
            converterList = converterToApply;
        }
        return buildProcessor(format, converterList);
    }

    private static Processor buildProcessor(Format format, Converter[] converterList) {
        if (Format.JSON.getName().equals(format.getName())) {
            return new JSONProcessor(converterList);
        } else if (Format.PROPERTIES.getName().equals(format.getName())) {
            return new PropertiesProcessor(converterList);
        }
        throw new IntegrityException("Unsupported format: " + format.getName());
    }

    /**
     * Provides list of converters that are supported by default.
     */
    public static final Converter BLANK = new BlankConverter();
    public static final Converter NUMBER_SEQUENCE = new NumberSequenceConverter();
}
