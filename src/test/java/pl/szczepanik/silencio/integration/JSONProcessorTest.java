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
import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.ConverterBuilder;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.processors.JSONProcessor;
import pl.szczepanik.silencio.stubs.StubConverter;
import pl.szczepanik.silencio.utils.ReflectionUtil;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class JSONProcessorTest {

    @Test
    public void shouldRemoveAllKeysFromJSONFile() throws IOException {

        // given
        Reader input = TestUtils.loadJsonAsReader("suv.json");
        Writer output = new StringWriter();

        // when
        Processor processor = ConverterBuilder.build(Format.JSON, ConverterBuilder.BLANK);
        processor.load(input);
        processor.process();
        processor.write(output);

        // then
        String reference = TestUtils.loadJSonAsString("suv_blank.json");
        assertThat(output.toString()).isEqualTo(reference);

        // don't forget
        input.close();
        output.close();
    }

    @Test
    public void shouldFailWhenLoadingInvalidJSONFile() {

        // given
        Reader input = TestUtils.loadJsonAsReader("corrupted.json");

        // when
        Processor processor = ConverterBuilder.build(Format.JSON, ConverterBuilder.BLANK);

        // then
        try {
            processor.load(input);
            fail("Expected exception");
        } catch (ProcessorException e) {
            assertThat(e.getCause()).isInstanceOf(JsonParseException.class);
            assertThat(e.getMessage()).contains("Unexpected character");
        }
    }

    @Test
    public void shouldReportExceptionOnUnsupportedModel() {

        // when
        String key = "myKey";
        Object value = new Object();
        JSONProcessor tested = new JSONProcessor(new Converter[] { new StubConverter() });

        // then
        try {
            ReflectionUtil.invokeMethod(tested, "processComplex", Void.class, key, value);
            fail("expected exception");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(ProcessorException.class);
            assertThat(e.getMessage()).isEqualTo("Unknown type of the key: " + value.getClass().getName());
        }
    }
}