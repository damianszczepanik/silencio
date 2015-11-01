package pl.szczepanik.silencio.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
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
public class BlankConverterTestInt extends GenericTest {

    @Test
    public void shoulClearValuesWhenBlankIsExecutedAsLast() throws IOException {

        // given
        input = ResourceLoader.loadJsonAsReader("suv.json");
        output = new StringWriter();

        // when
        Processor processor = new Builder(Format.JSON).with(Builder.NUMBER_SEQUENCE, Builder.BLANK).build();
        processor.load(input);
        processor.process();
        processor.write(output);

        // then
        String reference = ResourceLoader.loadJsonAsString("suv_Positive_Blank.json");
        assertThat(output.toString()).isEqualTo(reference);
    }

}
