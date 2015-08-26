package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.Test;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.api.Strategy;
import pl.szczepanik.silencio.stubs.StubConverter;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ConverterBuilderTest {

    @Test
    public void shouldNotFailWhenPassingAnyStrategy() {

        // when
        Format format = Format.JSON;
        Strategy[] strategies = { new StubConverter(), new StubConverter() };

        // then
        Processor processor = ConverterBuilder.build(format, strategies);

        // then
        assertThat(processor).isNotNull();
    }

    @Test
    public void shouldFailWhenPassingNoneStrategy() {

        // when
        Format format = Format.XML;
        Strategy[] strategies = null;

        // then
        try {
            ConverterBuilder.build(format, strategies);
            fail("expected exception");
        } catch (IntegrityException e) {
            assertThat(e.getMessage()).isEqualTo("Array with strategies must not be empty!");
        }
    }

}
