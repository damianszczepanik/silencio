package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.api.Strategy;
import pl.szczepanik.silencio.stubs.StubStrategy;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class StrategyBuilderTest {

    @Test
    public void shouldNotFailWhenNoStrategyPassed() {

        // when
        Format format = Format.JSON;
        Strategy[] strategies = { new StubStrategy(Format.JSON), new StubStrategy(Format.JSON) };

        // then
        Processor processor = StrategyBuilder.build(format, strategies);

        // then
        assertThat(processor).isNotNull();
    }

}
