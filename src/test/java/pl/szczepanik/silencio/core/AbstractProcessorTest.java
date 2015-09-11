package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.stubs.StubAbstractProcessor;
import pl.szczepanik.silencio.stubs.StubConverter;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class AbstractProcessorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldReturnValidFormat() {

        // given
        Converter[] converters = { new StubConverter() };
        Format format = Format.PROPERTIES;

        // when
        Processor processor = new StubAbstractProcessor(format, converters);

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
        new StubAbstractProcessor(format, converters);
    }

    @Test
    public void shouldFailWhenBuildFromNullConverter() {

        // given
        Format format = Format.PROPERTIES;

        // when
        Converter[] converter = null;

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("Array with converters must not be empty!");
        new StubAbstractProcessor(format, converter);
    }

    @Test
    public void shouldFailWhenBuildFromEmptyConverter() {

        // given
        Format format = Format.PROPERTIES;

        // when
        Converter[] converter = {};

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("Array with converters must not be empty!");
        new StubAbstractProcessor(format, converter);
    }

    @Test
    public void shouldFailWhenBuildFromOneOfEmptyConverter() {

        // given
        Format format = Format.JSON;

        // when
        Converter[] converters = { new StubConverter(), null };

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("Converter passed on index 1 is null!");
        new StubAbstractProcessor(format, converters);
    }


    @Test
    public void shouldFailOnProcessWhenConvertersAreNotSet() {

        // given
        Format format = Format.PROPERTIES;

        // when
        AbstractProcessor processor = new StubAbstractProcessor(format);

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage("This operation is not allowed for this state: CREATED");
        processor.process();
    }
}
