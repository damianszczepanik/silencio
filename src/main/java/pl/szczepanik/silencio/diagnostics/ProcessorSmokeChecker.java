package pl.szczepanik.silencio.diagnostics;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.io.IOUtils;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

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

    private final Processor processor;

    /**
     * Creates checker with the processor that should be examined.
     * 
     * @param processor
     *            processor that wil lbe validated
     */
    public ProcessorSmokeChecker(Processor processor) {
        this.processor = processor;
    }

    public void validateWithAllConverters(String content) {
        for (Converter converter : CONVERTERS) {
            validateProcessor(new Converter[] { converter }, content);
        }
    }

    /**
     * Validates processor against many combinations built from available converters. For more details check
     * https://github.com/dpaukov/combinatoricslib#5-subsets
     * 
     * @param content
     *            content that should be converted
     */
    public void validateWithSetsOfConverters(String content) {
        ICombinatoricsVector<Converter> initialSet = Factory.createVector(CONVERTERS);
        Generator<Converter> gen = Factory.createSubSetGenerator(initialSet);

        for (ICombinatoricsVector<Converter> subSet : gen) {
            if (subSet.getSize() != 0) {
                Converter[] converters = subSet.getVector().toArray(new Converter[subSet.getSize()]);
                validateProcessor(converters, content);
            }
        }
    }

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
    public void validateProcessor(Converter[] converter, String content) {
        Writer output = new StringWriter();
        Reader input = new StringReader(content);

        try {
            processor.setConverters(converter);
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
