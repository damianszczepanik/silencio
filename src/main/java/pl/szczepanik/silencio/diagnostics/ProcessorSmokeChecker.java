package pl.szczepanik.silencio.diagnostics;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.io.IOUtils;

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

    public static final Converter[] CONVERTERS = {
            new BlankConverter(),
            new BlankCharConverter(),
            new ConstantValueConverter(),
            new KeyValueConverter(),
            new PassedValueConverter(),
            };

    /**
     * Passes sets of basic converters into given processor and make sure that processor does not crash.
     *
     * @param processor
     *            processor to test
     * @param content
     *            content to the data to convert
     * @throws ProcessorException
     *             when processing fails (any reason)
     */
    public static void validateProcessor(Processor processor, String content) {

        for (Converter converter : CONVERTERS) {
            Writer output = new StringWriter();
            Reader input = new StringReader(content);

            try {
                processor.setConverters(new Converter[] { converter });
                processor.load(input);
                processor.process();
                processor.write(output);
            } catch (Exception e) {
                throw new ProcessorException(e);
            } finally {
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(output);
            }
        }
    }

}
