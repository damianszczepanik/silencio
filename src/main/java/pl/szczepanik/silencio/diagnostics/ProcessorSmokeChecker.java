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
import pl.szczepanik.silencio.api.Decision;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.converters.BlankConverter;
import pl.szczepanik.silencio.core.Configuration;
import pl.szczepanik.silencio.core.Execution;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.decisions.MatcherDecision;
import pl.szczepanik.silencio.decisions.NegativeDecision;
import pl.szczepanik.silencio.decisions.PositiveDecision;

/**
 * Executes basic tests on processor to make sure that basic, functional requirements are met.
 *
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public final class ProcessorSmokeChecker {

    private static final Converter[] CONVERTERS = {
            new BlankConverter(),
            new WhiteCharConverter(),
            new ConstantValueConverter(),
            new KeyValueConverter(),
            new PassedValueConverter()
    };

    private static final Decision[] DECISIONS = {
            new PositiveDecision(),
            new NegativeDecision(),
            new MatcherDecision(".*")
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

    /**
     * Validates processor against many combinations built from available converters. For more details check
     * https://github.com/dpaukov/combinatoricslib#5-subsets
     * 
     * @param content
     *            content that should be converted
     */
    public void validateWithAllCombinations(String content) {
        ICombinatoricsVector<Converter> allConverters = Factory.createVector(CONVERTERS);
        Generator<Converter> subSet = Factory.createSubSetGenerator(allConverters);

        for (ICombinatoricsVector<Converter> subConverters : subSet) {
            if (subConverters.getSize() != 0) {
                Converter[] converters = subConverters.getVector().toArray(new Converter[subConverters.getSize()]);
                validateWithSetsOfDecisions(converters, content);
            }
        }
    }

    private void validateWithSetsOfDecisions(Converter[] converters, String content) {
        ICombinatoricsVector<Decision> allDecisions = Factory.createVector(DECISIONS);
        Generator<Decision> subSet = Factory.createSubSetGenerator(allDecisions);

        for (ICombinatoricsVector<Decision> subDecisions : subSet) {
            if (subDecisions.getSize() != 0) {
                Decision[] decisions = subDecisions.getVector().toArray(new Decision[subDecisions.getSize()]);
                Execution[] executions = { new Execution(decisions, converters) };
                validateProcessor(executions, content);
            }
        }
    }

    /**
     * Passes sets of basic converters into given processor and make sure that processor does not crash.
     *
     * @param executions
     *            Executions that will be used for validation
     * @param content
     *            content to the data to convert
     * @throws ProcessorException
     *             when processing fails (any reason)
     */
    public void validateProcessor(Execution[] executions, String content) {
        Writer output = new StringWriter();
        Reader input = new StringReader(content);

        try {
            processor.setConfiguration(new Configuration(executions));
            processor.load(input);
            processor.process();
            processor.write(output);
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
    }
}
