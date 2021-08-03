package pl.szczepanik.silencio.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringWriter;

import org.junit.Test;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.Builder;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class XMLProcessorIntegrationTest extends GenericTest {

    @Test
    public void shouldProcessXMLFile() {

        // given
        Processor processor = new Builder(Format.XML).with(Builder.NUMBER_SEQUENCE).build();
        input = ResourceLoader.loadAsReader("suv.xml");
        output = new StringWriter();
        processor.load(input);

        // when
        processor.process();

        // then
        processor.write(output);
        String reference = ResourceLoader.loadAsString("suv_Positive_NumberSequence.xml");
        assertThat(output).hasToString(reference);
    }
}
