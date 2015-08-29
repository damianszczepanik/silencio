package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.Test;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.stubs.StubConverter;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ConverterBuilderTest {

    @Test
    public void shouldNotFailWhenPassingAnyConverter() {

        // when
        Format format = Format.JSON;
        Converter[] converters = { new StubConverter(), new StubConverter() };

        // then
        Processor processor = ConverterBuilder.build(format, converters);

        // then
        assertThat(processor).isNotNull();
    }

    @Test
    public void shouldFailWhenPassingNoneConverter() {

        // when
        Format format = Format.XML;
        Converter[] converters = null;

        // then
        try {
            ConverterBuilder.build(format, converters);
            fail("expected exception");
        } catch (IntegrityException e) {
            assertThat(e.getMessage()).isEqualTo("Array with converters must not be empty!");
        }
    }

}
