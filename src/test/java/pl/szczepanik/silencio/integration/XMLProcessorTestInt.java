package pl.szczepanik.silencio.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringWriter;

import org.junit.Test;

import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.Builder;
import pl.szczepanik.silencio.diagnostics.ProcessorSmokeChecker;
import pl.szczepanik.silencio.processors.XMLProcessor;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class XMLProcessorTestInt extends GenericTest {

    @Test
    public void shouldProcessXMLFile() {

        // given
        input = ResourceLoader.loadXmlAsReader("suv.xml");
        output = new StringWriter();
        Processor processor = new Builder(Format.XML).with(Builder.NUMBER_SEQUENCE).build();
        processor.load(input);

        // when
        processor.process();

        // then
        processor.write(output);
        String reference = ResourceLoader.loadXmlAsString("suv_Positive_NumberSequence.xml");
        assertThat(output.toString()).isEqualTo(reference);
    }

    @Test
    public void shouldNotCrashOnDiagnosticTests() {

        // given
        String content = ResourceLoader.loadXmlAsString("suv.xml");
        ProcessorSmokeChecker checker = new ProcessorSmokeChecker(new XMLProcessor());

        // when
        checker.validateWithAllCombinations(content);

        // then
        // no crash
    }
}