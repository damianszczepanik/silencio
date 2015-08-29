package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.Test;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.stubs.StubAbstractProcessor;
import pl.szczepanik.silencio.stubs.StubConverter;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class AbstractProcessorTest {

    @Test
    public void shouldReturnValidFormat() {

        // given
        Converter[] converters = { new StubConverter() };
        Format format = Format.XML;

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
        try {
            new StubAbstractProcessor(format, converters);
            fail("Expected exceeption");
        } catch (IntegrityException e) {
            assertThat(e).hasMessage("Format must not be null!");
        }
    }

    @Test
    public void shouldFailWhenBuildFromNullConverter() {

        // given
        Format format = Format.XML;

        // when
        Converter[] converter = null;

        // then
        try {
            new StubAbstractProcessor(format, converter);
            fail("Expected exceeption");
        } catch (IntegrityException e) {
            assertThat(e).hasMessage("Array with converters must not be empty!");
        }
    }

    @Test
    public void shouldFailWhenBuildFromEmptyConverter() {

        // given
        Format format = Format.XML;

        // when
        Converter[] converter = {};

        // then
        try {
            new StubAbstractProcessor(format, converter);
            fail("Expected exceeption");
        } catch (IntegrityException e) {
            assertThat(e).hasMessage("Array with converters must not be empty!");
        }
    }

    @Test
    public void shouldFailWhenBuildFromOneOfEmptyConverter() {

        // given
        Format format = Format.JSON;

        // when
        Converter[] converters = { new StubConverter(), null };

        // then
        try {
            new StubAbstractProcessor(format, converters);
            fail("Expected exceeption");
        } catch (IntegrityException e) {
            assertThat(e).hasMessage("Converter passed on index 1 is null!");
        }
    }

}
