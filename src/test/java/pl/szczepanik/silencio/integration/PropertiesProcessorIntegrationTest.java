package pl.szczepanik.silencio.integration;


import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.ConverterBuilder;
import pl.szczepanik.silencio.diagnostics.ProcessorSmokeChecker;
import pl.szczepanik.silencio.processors.PropertiesProcessor;
import pl.szczepanik.silencio.utils.PropertiesUtils;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class PropertiesProcessorIntegrationTest {

    private Writer output;
    private Reader input;

    @Test
    public void shouldProcessPropertiesFile() throws IOException {

        // given
        input = ResourceLoader.loadPropertiesAsReader("suv.properties");
        output = new StringWriter();

        // when
        Processor processor = ConverterBuilder.build(Format.PROPERTIES, ConverterBuilder.BLANK);
        processor.load(input);
        processor.process();
        processor.write(output);

        // then
        Properties reference = new Properties();
        reference.load(new StringReader(ResourceLoader.loadPropertiesAsString("suv_blank.properties")));
        Properties converted = new Properties();
        converted.load(new StringReader(output.toString()));
        PropertiesUtils.assertEqual(reference, converted);
    }

    @Test
    public void shouldPassDiagnosticTests() {

        // given
        input = ResourceLoader.loadPropertiesAsReader("suv.properties");

        // then
        ProcessorSmokeChecker.validateProcessor(new PropertiesProcessor(), input);
    }

    @After
    public void closeStreams() {
        IOUtils.closeQuietly(input);
        IOUtils.closeQuietly(output);
    }
}