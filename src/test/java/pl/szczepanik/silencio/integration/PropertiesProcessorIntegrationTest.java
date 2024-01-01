package pl.szczepanik.silencio.integration;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import org.junit.jupiter.api.Test;import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.Builder;
import pl.szczepanik.silencio.utils.PropertiesUtility;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class PropertiesProcessorIntegrationTest extends GenericTest {

    @Test
    public void shouldProcessPropertiesFile() throws IOException {

        // given
        Processor processor = new Builder(Format.PROPERTIES).with(Builder.BLANK).build();
        input = ResourceLoader.loadAsReader("suv.properties");
        output = new StringWriter();
        processor.load(input);

        // when
        processor.process();

        // then
        processor.write(output);

        Properties reference = new Properties();
        reference.load(ResourceLoader.loadAsReader("suv_Positive_Blank.properties"));
        Properties converted = new Properties();
        converted.load(new StringReader(output.toString()));
        PropertiesUtility.assertEqual(reference, converted);
    }
}