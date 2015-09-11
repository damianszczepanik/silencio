package pl.szczepanik.silencio.diagnostics;

import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.converters.BlankConverter;
import pl.szczepanik.silencio.core.ProcessorException;

/**
 * Executes basic tests on processor to make sure that basic, functional requirements are met.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public final class ProcessorSmokeChecker {

    public static final Converter[] converters = {
            new BlankConverter(),
            new ConstantValueConverter(),
            new KeyValueConverter(),
            new PassedValueConverter(),
            };

    /**
     * Passes sets of basic converters into given processor and make sure that processor does not crash.
     * 
     * @param processor
     *            processor to test
     * @param reader
     *            source with the data to convert
     * @throws ProcessorException
     *             when processing fails (any reason)
     */
    public static void validateProcessor(Processor processor, Reader reader) {

        Writer output = new StringWriter();

        try {
            processor.setConverters(converters);
            processor.load(reader);
            processor.process();
            processor.write(output);
        } catch (Exception e) {
            throw new ProcessorException(e);
        }
    }

}
