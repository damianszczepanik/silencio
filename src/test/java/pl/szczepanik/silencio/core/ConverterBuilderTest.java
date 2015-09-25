package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.stubs.StubConverter;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ConverterBuilderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldNotFailWhenPassingAnyConverter() {

        // when
        Format format = Format.JSON;
        Converter[] converters = { new StubConverter(), new StubConverter() };

        // then
        Processor processor = Builder.build(format, converters);

        // then
        assertThat(processor).isNotNull();
        assertThat(processor.getFormat()).isEqualTo(format);
    }

    @Test
    public void shouldFailWhenPassingNoneConverter() {

        // when
        Format format = Format.PROPERTIES;
        Converter[] converters = null;

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("Array with Converters must not be empty!");
        Builder.build(format, converters);
    }

    @Test
    public void shouldFailWhenPassingInvalidFormat() {

        // when
        Format format = new Format("tr!cky") { };
        Converter[] converters = { Builder.BLANK };

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("Unsupported format: " + format.getName());
        Builder.build(format, converters);
    }
}
