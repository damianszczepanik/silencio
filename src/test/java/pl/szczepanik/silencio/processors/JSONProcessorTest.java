package pl.szczepanik.silencio.processors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import java.io.StringWriter;

import org.junit.Test;
import pl.szczepanik.silencio.GenericTest;
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
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class JSONProcessorTest extends GenericTest {

    @Test
    public void shouldReturnPassedFormat() {

        // given
        Processor processor = new JSONProcessor();

        // when
        Format format = processor.getFormat();

        // then
        assertThat(format).isEqualTo(Format.JSON);
    }

    @Test
    public void shouldLoadJSONFileOnRealLoad() {

        // given
        JSONProcessor processor = new JSONProcessor();
        input = ResourceLoader.loadAsReader("suv.json");
        String refInput = ResourceLoader.loadAsString("suv.json");
        output = new StringWriter();

        // when
        processor.load(input);

        // then
        processor.realWrite(output);
        assertThat(refInput).isEqualToNormalizingNewlines(output.toString());
    }

    @Test
    public void shouldFailWhenLoadingInvalidJSONFile() {

        // given
        Processor processor = new JSONProcessor();
        Execution execution = new Execution(new PositiveDecision(), Builder.BLANK);
        input = ResourceLoader.loadAsReader("corrupted.json");

        // when
        processor.setConfiguration(new Configuration(execution));

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage(containsString("Unexpected character"));
        processor.load(input);
    }

    @Test
    public void shouldFailWhenWritingToInvalidWriter() {

        final String errorMessage = "Don't write into this writer!";

        // given
        JSONProcessor processor = new JSONProcessor();
        Execution execution = new Execution(new PositiveDecision(), Builder.BLANK);
        processor.setConfiguration(new Configuration(execution));
        input = ResourceLoader.loadAsReader("empty.json");
        output = new WriterCrashOnWrite(errorMessage);

        // when
        processor.load(input);
        processor.realProcess();

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage(errorMessage);
        processor.realWrite(output);
    }
}