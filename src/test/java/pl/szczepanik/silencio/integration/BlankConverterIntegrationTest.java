package pl.szczepanik.silencio.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.jupiter.api.Test;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.Builder;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
class BlankConverterIntegrationTest extends GenericTest {

    @Test
    void shoulClearValuesWhenBlankIsExecutedAsLast() {

        // given
        Processor processor = new Builder(Format.JSON).with(Builder.NUMBER_SEQUENCE, Builder.BLANK).build();
        input = ResourceLoader.loadAsReader("suv.json");
        output = new StringWriter();

        // when
        processor.load(input);
        processor.process();

        // then
        processor.write(output);

        String reference = ResourceLoader.loadAsString("suv_Positive_Blank.json");
        assertThat(output).hasToString(reference);
    }
}
