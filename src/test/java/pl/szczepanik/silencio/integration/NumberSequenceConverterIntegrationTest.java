package pl.szczepanik.silencio.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;

import pl.szczepanik.silencio.TestUtils;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.ConverterBuilder;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class NumberSequenceConverterIntegrationTest {

    private Writer output;
    private Reader input;

    @Test
    public void shouldProcessBlankConverter() {

        // given
        input = TestUtils.loadJsonAsReader("suv.json");
        output = new StringWriter();

        // when
        Processor processor = ConverterBuilder.build(Format.JSON, ConverterBuilder.BLANK);
        processor.load(input);
        processor.process();
        processor.write(output);

        // then
        String reference = TestUtils.loadJSonAsString("suv_blank.json");
        assertThat(output.toString()).isEqualTo(reference);
    }

    @After
    public void closeStreams() {
        IOUtils.closeQuietly(input);
        IOUtils.closeQuietly(output);
    }
}