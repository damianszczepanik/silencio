package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.Test;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.api.Strategy;
import pl.szczepanik.silencio.stubs.StubAbstractProcessor;
import pl.szczepanik.silencio.stubs.StubStrategy;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class AbstractProcessorTest {

    @Test
    public void shouldReturnValidFormat() {

        // given
        Strategy[] strategies = { new StubStrategy(Format.XML) };
        Format format = Format.XML;

        // when
        Processor processor = new StubAbstractProcessor(format, strategies);

        // then
        assertThat(processor.getFormat()).isEqualTo(format);
    }

    @Test
    public void shouldFailWhenBuildFromEmptyFormat() {

        // given
        Strategy[] strategies = { new StubStrategy(Format.XML) };

        // when
        Format format = null;

        // then
        try {
            new StubAbstractProcessor(format, strategies);
            fail("Expected exceeption");
        } catch (IntegrityException e) {
            assertThat(e).hasMessage("Format must not be null!");
        }
    }

    @Test
    public void shouldFailWhenBuildFromNullStrategy() {

        // given
        Format format = Format.XML;

        // when
        Strategy[] strategy = null;

        // then
        try {
            new StubAbstractProcessor(format, strategy);
            fail("Expected exceeption");
        } catch (IntegrityException e) {
            assertThat(e).hasMessage("Array with strategies must not be empty!");
        }
    }

    @Test
    public void shouldFailWhenBuildFromEmptyStrategy() {

        // given
        Format format = Format.XML;

        // when
        Strategy[] strategy = {};

        // then
        try {
            new StubAbstractProcessor(format, strategy);
            fail("Expected exceeption");
        } catch (IntegrityException e) {
            assertThat(e).hasMessage("Array with strategies must not be empty!");
        }
    }

    @Test
    public void shouldFailWhenBuildFromOneOfEmptyStrategy() {

        // given
        Format format = Format.JSON;

        // when
        Strategy[] strategies = { new StubStrategy(Format.JSON), null };

        // then
        try {
            new StubAbstractProcessor(format, strategies);
            fail("Expected exceeption");
        } catch (IntegrityException e) {
            assertThat(e).hasMessage("Strategy passed on index 1 is null!");
        }
    }

    @Test
    public void shouldFailWhenPassingDifferentFormats() {

        // given
        Format format = Format.XML;

        // when
        Strategy[] strategies = { new StubStrategy(Format.XML), new StubStrategy(Format.JSON) };

        // then
        try {
            new StubAbstractProcessor(format, strategies);
            fail("Expected exceeption");
        } catch (IntegrityException e) {
            assertThat(e).hasMessage("Strategy passed on index 1 supports format 'JSON' while expecting 'XML'!");
        }
    }
}
