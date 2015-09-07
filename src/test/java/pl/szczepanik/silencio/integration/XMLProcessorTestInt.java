package pl.szczepanik.silencio.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.ConverterBuilder;
import pl.szczepanik.silencio.diagnostics.ProcessorSmokeChecker;
import pl.szczepanik.silencio.processors.XMLProcessor;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class XMLProcessorTestInt {

    private Writer output;
    private Reader input;

    @Test
    public void shouldProcessXmlFile() {

        // given
        input = ResourceLoader.loadXmlAsReader("suv.xml");
        output = new StringWriter();

        // when
        Processor processor = ConverterBuilder.build(Format.XML, ConverterBuilder.NUMBER_SEQUENCE);
        processor.load(input);
        processor.process();
        processor.write(output);

        // then
        String reference = ResourceLoader.loadXmlAsString("suv_numbersequence.xml");
        assertThat(output.toString()).isEqualTo(reference);
    }

    @Test
    public void shouldNotCrashOnDiagnosticTests() {

        // given
        String content = ResourceLoader.loadXmlAsString("suv.xml");
        ProcessorSmokeChecker checker = new ProcessorSmokeChecker(new XMLProcessor());

        // then
        checker.validateWithAllConverters(content);
        checker.validateWithSetsOfConverters(content);
    }

    @After
    public void closeStreams() {
        IOUtils.closeQuietly(input);
        IOUtils.closeQuietly(output);
    }
}