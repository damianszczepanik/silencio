package pl.szczepanik.silencio.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;

import pl.szczepanik.silencio.TestUtils;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.core.ConverterBuilder;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class JSONProcessorTest {

    @Test
    public void shouldRemoveAllKeysFromJSONFile() throws IOException {

        // given
        Reader input = TestUtils.loadJson("suv.json");
        Writer output = new StringWriter();

        // when
        Processor processor = ConverterBuilder.build(Format.JSON, ConverterBuilder.MAKE_EMPTY);
        processor.load(input);
        processor.process();
        processor.write(output);

        // then
        String reference = "{\"model\":\"\",\"wheels\":\"\",\"size\":{\"length\":\"\",\"height\":\"\",\"width\":\"\"},\"accessories\":[\"\",\"\"],\"fuel\":[{\"diesel\":\"\",\"LPG\":\"\"}],\"notes\":\"\",\"alarms\":{},\"assistance\":[]}";
        assertThat(output.toString()).isEqualTo(reference);

        // don't forget
        input.close();
        output.close();
    }

    @Test
    public void shouldFailWhenLoadingInvalidJSONFile() {

        // given
        Reader input = TestUtils.loadJson("corrupted.json");

        // when
        Processor processor = ConverterBuilder.build(Format.JSON, ConverterBuilder.MAKE_EMPTY);

        // then
        try {
            processor.load(input);
            fail("Expected exception");
        } catch (ProcessorException e) {
            assertThat(e.getCause()).isInstanceOf(JsonParseException.class);
            assertThat(e.getMessage()).contains("Unexpected character");
        }
    }

}