package pl.szczepanik.silencio.processors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.ConverterBuilder;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.stubs.StubConverter;
import pl.szczepanik.silencio.stubs.WriterStub;
import pl.szczepanik.silencio.utils.ReflectionUtils;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class JSONProcessorTest {

    private Writer output;
    private Reader input;

    @Test
    public void shouldFailWhenLoadingInvalidJSONFile() {

        // given
        input = ResourceLoader.loadJsonAsReader("corrupted.json");

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
            ReflectionUtils.invokeMethod(tested, "processComplex", Void.class, key, value);
            fail("expected exception");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(ProcessorException.class);
            assertThat(e.getMessage()).isEqualTo("Unknown type of the key: " + value.getClass().getName());
        }
    }

    @Test
    public void shouldFailWhenWrittingToInvalidWriter() {

        final String errorMessage = "Don't write into this writter!";
        // given

        input = ResourceLoader.loadJsonAsReader("empty.json");
        output = new WriterStub() {

            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                throw new IOException(errorMessage);
            }
        };

        // when
        Processor processor = ConverterBuilder.build(Format.JSON, ConverterBuilder.BLANK);
        processor.load(input);
        processor.process();

        // then
        try {
            processor.write(output);
            fail("expected exception");
        } catch (ProcessorException e) {
            assertThat(e.getCause().getMessage()).isEqualTo(errorMessage);
        }
    }

    @After
    public void closeStreams() {
        IOUtils.closeQuietly(input);
        IOUtils.closeQuietly(output);
    }

}