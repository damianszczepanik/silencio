package pl.szczepanik.silencio.processors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import java.io.StringWriter;

import org.junit.Test;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Format;
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
public class YAMLProcessorTest extends GenericTest {

    @Test
    public void shouldReturnPassedFormat() {

        // given
        YAMLProcessor processor = new YAMLProcessor();

        // when
        Format format = processor.getFormat();

        // then
        assertThat(format).isEqualTo(Format.YAML);
    }

    @Test
    public void shouldLoadYAMLFileOnRealLoad() {

        // given
        YAMLProcessor processor = new YAMLProcessor();
        input = ResourceLoader.loadAsReader("suv.yaml");
        String refInput = ResourceLoader.loadAsString("suv.yaml");
        output = new StringWriter();

        // when
        processor.load(input);

        // then
        processor.realWrite(output);
        assertThat(refInput).isEqualToNormalizingNewlines(output.toString());
    }

    @Test
    public void shouldFailWhenLoadingInvalidYAMLFile() {

        // given
        YAMLProcessor processor = new YAMLProcessor();
        Execution execution = new Execution(new PositiveDecision(), Builder.BLANK);
        input = ResourceLoader.loadAsReader("corrupted.yaml");

        // when
        processor.setConfiguration(new Configuration(execution));

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage(containsString("Cannot construct instance of"));
        processor.load(input);
    }

    @Test
    public void shouldFailWhenWritingToInvalidWriter() {

        final String errorMessage = "Don't write into this writer!";

        // given
        YAMLProcessor processor = new YAMLProcessor();
        Execution execution = new Execution(new PositiveDecision(), Builder.BLANK);
        processor.setConfiguration(new Configuration(execution));
        input = ResourceLoader.loadAsReader("empty.yaml");
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