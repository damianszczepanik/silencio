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
public class JSONProcessorIntegrationTest extends GenericTest {

    @Test
    public void shouldProcessJSONFile() {

        // given
        Processor processor = new Builder(Format.JSON).with(Builder.NUMBER_SEQUENCE).build();
        input = ResourceLoader.loadAsReader("suv.json");
        output = new StringWriter();
        processor.load(input);

        // when
        processor.process();

        // then
        processor.write(output);
        String reference = ResourceLoader.loadAsString("suv_Positive_NumberSequence.json");
        assertThat(output).hasToString(reference);
    }
}
