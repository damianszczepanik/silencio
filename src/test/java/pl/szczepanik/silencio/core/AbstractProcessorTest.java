package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.stubs.StubConverter;
import pl.szczepanik.silencio.stubs.StubProcessor;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class AbstractProcessorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldReturnPassedFormat() {

        // given
        Converter[] converters = { new StubConverter() };
        Format format = Format.PROPERTIES;

        // when
        AbstractProcessor processor = new StubProcessor(format, converters);

        // then
        assertThat(processor.getFormat()).isEqualTo(format);
    }

    @Test
    public void shouldFailWhenBuildFromEmptyFormat() {

        // given
        Converter[] converters = { new StubConverter() };

        // when
        Format format = null;

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("Format must not be null!");
        new StubProcessor(format, converters);
    }

    @Test
    public void shouldFailWhenBuildFromNullConfiguration() {

        // given
        Format format = Format.PROPERTIES;
        AbstractProcessor processor = new StubProcessor(format);

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("Configuration must not be null!");
        processor.setConfiguration(null);

    }

    @Test
    public void shouldFailWhenBuildFromEmptyConverter() {

        // given
        Format format = Format.PROPERTIES;

        // when
        Converter[] converter = {};

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("Array with Converters must not be empty!");
        new StubProcessor(format, converter);
    }

    @Test
    public void shouldFailWhenBuildFromOneOfEmptyConverter() {

        // given
        Format format = Format.JSON;

        // when
        Converter[] converters = { new StubConverter(), null };

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("Passed Converter is null!");
        new StubProcessor(format, converters);
    }

    @Test
    public void shouldFailOnProcessWhenConvertersAreNotSet() {

        // given
        Format format = Format.PROPERTIES;

        // when
        AbstractProcessor processor = new StubProcessor(format);

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage("This operation is not allowed for this state: CREATED");
        processor.process();
    }
}
