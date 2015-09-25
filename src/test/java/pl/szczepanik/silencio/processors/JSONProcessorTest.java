package pl.szczepanik.silencio.processors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.Builder;
import pl.szczepanik.silencio.core.Configuration;
import pl.szczepanik.silencio.core.Execution;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.mocks.WriterCrashOnWrite;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class JSONProcessorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Writer output;
    private Reader input;

    @Test
    public void shouldReturnPassedFormat() {

        // given
        JSONProcessor processor = new JSONProcessor();

        // when
        Format format = processor.getFormat();

        // then
        assertThat(format).isEqualTo(Format.JSON);
    }

    @Test
    public void shouldLoadJSONFileOnRealLoad() {

        // given
        input = ResourceLoader.loadJsonAsReader("suv.json");
        String refInput = ResourceLoader.loadJsonAsString("suv.json");
        output = new StringWriter();

        // when
        JSONProcessor processor = new JSONProcessor();
        processor.load(input);
        processor.realWrite(output);

        // then
        assertThat(refInput).isEqualTo(output.toString());
    }

    @Test
    public void shouldFailWhenLoadingInvalidJSONFile() {

        // given
        input = ResourceLoader.loadJsonAsReader("corrupted.json");

        // when
        Processor processor = new JSONProcessor();
        Execution execution = new Execution(new PositiveDecision(), Builder.BLANK);
        processor.setConfiguration(new Configuration(execution));

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage(containsString("Unexpected character"));
        processor.load(input);
    }

    @Test
    public void shouldFailWhenWrittingToInvalidWriter() {

        final String errorMessage = "Don't write into this writter!";

        // given
        input = ResourceLoader.loadJsonAsReader("empty.json");
        output = new WriterCrashOnWrite(errorMessage);

        // when
        JSONProcessor processor = new JSONProcessor();
        Execution execution = new Execution(new PositiveDecision(), Builder.BLANK);
        processor.setConfiguration(new Configuration(execution));
        processor.load(input);
        processor.realProcess();

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage(errorMessage);
        processor.realWrite(output);
    }

    @After
    public void closeStreams() {
        IOUtils.closeQuietly(input);
        IOUtils.closeQuietly(output);
    }

}