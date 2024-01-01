package pl.szczepanik.silencio.processors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.StringWriter;

import org.junit.jupiter.api.Test;
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
public class XMLProcessorTest extends GenericTest {

    @Test
    void shouldReturnPassedFormat() {

        // given
        XMLProcessor processor = new XMLProcessor();

        // when
        Format format = processor.getFormat();

        // then
        assertThat(format).isEqualTo(Format.XML);
    }

    @Test
    void shouldLoadXMLFileOnRealLoad() {

        // given
        XMLProcessor processor = new XMLProcessor();
        input = ResourceLoader.loadAsReader("suv.xml");
        String refInput = ResourceLoader.loadAsString("suv_tranformed.xml");
        output = new StringWriter();

        // when
        processor.load(input);

        // then
        processor.realWrite(output);
        assertThat(refInput).isEqualTo(output.toString());
    }

    @Test
    void shouldFailWhenLoadingInvalidJSONFile() {

        // given
        Processor processor = new XMLProcessor();
        Execution execution = new Execution(new PositiveDecision(), Builder.BLANK);
        input = ResourceLoader.loadAsReader("corrupted.xml");

        // when
        processor.setConfiguration(new Configuration(execution));

        // then
        assertThatThrownBy(() -> processor.load(input))
                .isInstanceOf(ProcessorException.class)
                .hasMessageContaining("XML document structures must start and end within the same entity");
    }

    @Test
    void shouldFailWhenWritingToInvalidWriter() {

        final String errorMessage = "Don't write into this writer!";

        // given
        XMLProcessor processor = new XMLProcessor();
        Execution execution = new Execution(new PositiveDecision(), Builder.BLANK);
        processor.setConfiguration(new Configuration(execution));
        input = ResourceLoader.loadAsReader("suv.xml");
        output = new WriterCrashOnWrite(errorMessage);

        // when
        processor.load(input);
        processor.realProcess();

        // then
        assertThatThrownBy(() -> processor.realWrite(output))
                .isInstanceOf(ProcessorException.class)
                .hasMessageContaining(errorMessage);
    }
}