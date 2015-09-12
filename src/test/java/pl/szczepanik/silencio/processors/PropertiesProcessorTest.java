package pl.szczepanik.silencio.processors;

import static org.hamcrest.core.StringContains.containsString;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.ConverterBuilder;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.stubs.WriterStub;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class PropertiesProcessorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Writer output;
    private Reader input;

    @Test
    public void shouldFailWhenLoadingInvalidPropertiesFile() {

        // given
        input = ResourceLoader.loadPropertiesAsReader("corrupted.properties");

        // when
        Processor processor = ConverterBuilder.build(Format.PROPERTIES, ConverterBuilder.BLANK);

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage(containsString("Malformed \\uxxxx encoding."));
        processor.load(input);
    }

    @Test
    public void shouldFailWhenWrittingToInvalidWriter() {

        final String errorMessage = "Don't write into this writter!";
        // given

        input = ResourceLoader.loadPropertiesAsReader("empty.properties");
        output = new WriterStub() {

            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                throw new IOException(errorMessage);
            }
        };

        // when
        Processor processor = ConverterBuilder.build(Format.PROPERTIES, ConverterBuilder.BLANK);
        processor.load(input);
        processor.process();

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage(errorMessage);
        processor.write(output);
    }

    @After
    public void closeStreams() {
        IOUtils.closeQuietly(input);
        IOUtils.closeQuietly(output);
    }

}