package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.mocks.ConverterHolder;
import pl.szczepanik.silencio.stubs.StubProcessor;
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
        Processor processor = new StubProcessor(format, converters);

        // then
        assertThat(processor.getFormat()).isEqualTo(format);
    }

    @Test
    public void shouldProcessAllConverters() {
        // given
        AbstractProcessor processor = new StubProcessor(Format.JSON);
        ConverterHolder[] converters = { new ConverterHolder() };
        String key = "myKey";
        Object value = "yourValue";

        // when
        processor.setConverters(converters);
        Value retValue = processor.processValue(key, value);

        // then
        assertThat(converters[0].getKey().getKey()).isEqualTo(key);
        assertThat(converters[0].getValue().getValue()).isEqualTo(value);
        assertThat(retValue.getValue()).isEqualTo(value);
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
    public void shouldFailWhenBuildFromNullConverter() {

        // given
        Format format = Format.PROPERTIES;

        // when
        Converter[] converter = null;

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("Array with converters must not be empty!");
        new StubProcessor(format, converter);
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
        thrown.expectMessage("Converter passed on index 1 is null!");
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
