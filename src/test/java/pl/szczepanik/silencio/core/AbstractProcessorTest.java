package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.processors.AbstractProcessor;
import pl.szczepanik.silencio.stubs.StubConverter;
import pl.szczepanik.silencio.stubs.StubProcessor;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
class AbstractProcessorTest extends GenericTest {

    @Test
    void shouldReturnPassedFormat() {

        // given
        Converter[] converters = {new StubConverter()};
        Format format = Format.PROPERTIES;

        // when
        AbstractProcessor processor = new StubProcessor(format, converters);

        // then
        assertThat(processor.getFormat()).isEqualTo(format);
    }

    @Test
    void shouldFailWhenBuildFromEmptyFormat() {

        // given
        Converter[] converters = {new StubConverter()};

        // when
        Format format = null;

        // then
        assertThatThrownBy(() -> new StubProcessor(format, converters))
                .isInstanceOf(IntegrityException.class)
                .hasMessage("Format must not be null!");
    }

    @Test
    void shouldFailWhenBuildFromNullConfiguration() {

        // given
        Format format = Format.PROPERTIES;
        AbstractProcessor processor = new StubProcessor(format);

        // then
        assertThatThrownBy(() -> processor.setConfiguration(null))
                .isInstanceOf(IntegrityException.class)
                .hasMessage("Configuration must not be null!");
    }

    @Test
    void shouldFailWhenBuildFromEmptyConverter() {

        // given
        Format format = Format.PROPERTIES;
        Converter[] converter = {};

        // then
        assertThatThrownBy(() -> new StubProcessor(format, converter))
                .isInstanceOf(IntegrityException.class)
                .hasMessage("Array with Converters must not be empty!");
    }

    @Test
    void shouldFailOnProcessWhenConvertersAreNotSet() {

        // given
        Format format = Format.PROPERTIES;

        // when
        AbstractProcessor processor = new StubProcessor(format);

        // then
        assertThatThrownBy(processor::process)
                .isInstanceOf(ProcessorException.class)
                .hasMessage("This operation is not allowed for this state: CREATED");
    }
}
