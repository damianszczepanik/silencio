package pl.szczepanik.silencio.integration;


import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import org.junit.Test;

import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.Builder;
import pl.szczepanik.silencio.diagnostics.ProcessorSmokeChecker;
import pl.szczepanik.silencio.processors.PropertiesProcessor;
import pl.szczepanik.silencio.utils.PropertiesUtility;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class PropertiesProcessorTestInt extends GenericTest {

    @Test
    public void shouldProcessPropertiesFile() throws IOException {

        // given
        input = ResourceLoader.loadPropertiesAsReader("suv.properties");
        output = new StringWriter();

        // when
        Processor processor = new Builder(Format.PROPERTIES).with(Builder.BLANK).build();
        processor.load(input);
        processor.process();
        processor.write(output);

        // then
        Properties reference = new Properties();
        reference.load(new StringReader(ResourceLoader.loadPropertiesAsString("suv_Positive_Blank.properties")));
        Properties converted = new Properties();
        converted.load(new StringReader(output.toString()));
        PropertiesUtility.assertEqual(reference, converted);
    }

    @Test
    public void shouldNotCrashOnDiagnosticTests() {

        // given
        String content = ResourceLoader.loadPropertiesAsString("suv.properties");
        ProcessorSmokeChecker checker = new ProcessorSmokeChecker(new PropertiesProcessor());

        // then
        checker.validateWithAllCombinations(content);
    }
}