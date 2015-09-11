package pl.szczepanik.silencio.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.ConverterBuilder;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class BlankConverterIntegrationTest {

    private Writer output;
    private Reader input;

    @Test
    public void shoulClearValuesWhenBlankSiExecutedAsLast() throws IOException {

        // given
        input = ResourceLoader.loadJsonAsReader("suv.json");
        output = new StringWriter();

        // when
        Processor processor = ConverterBuilder.build(Format.JSON, ConverterBuilder.NUMBER_SEQUENCE,
                ConverterBuilder.BLANK);
        processor.load(input);
        processor.process();
        processor.write(output);

        // then
        String reference = ResourceLoader.loadJsonAsString("suv_blank.json");
        assertThat(output.toString()).isEqualTo(reference);
    }

    @After
    public void closeStreams() {
        IOUtils.closeQuietly(input);
        IOUtils.closeQuietly(output);
    }
}
