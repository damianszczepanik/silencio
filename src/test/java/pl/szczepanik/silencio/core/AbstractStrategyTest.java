package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.Test;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Strategy;
import pl.szczepanik.silencio.stubs.StubAbstractStrategy;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class AbstractStrategyTest {

    @Test
    public void shouldReturnValidName() {

        // given
        Format format = Format.XML;
        Strategy strategy = new StubAbstractStrategy(format);

        // when
        boolean compare = strategy.getFormat().equals(format);

        // then
        assertThat(compare).isTrue();
    }

    @Test
    public void shouldFailOnEmeptyFormat() {

        // when
        Format format = null;

        // then
        try {
            new StubAbstractStrategy(format);
            fail("Expected exception");
        } catch (IntegrityException e) {
            assertThat(e.getMessage()).isEqualTo("Format of the strategy must not be null!");
        }
    }
}
