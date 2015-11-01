package pl.szczepanik.silencio.decisions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pl.szczepanik.silencio.api.Decision;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class NegativeDecisionTest {

    @Test
    public void shouldNotAllowForConversion() {

        // given
        Decision d = new NegativeDecision();

        // then
        assertThat(d.decide(null, null)).isFalse();
        assertThat(d.decide(new Key(""), new Value(null))).isFalse();
        assertThat(d.decide(new Key("Hello"), new Value(33L))).isFalse();
        assertThat(d.decide(new Key("foo"), new Value(this))).isFalse();
        assertThat(d.decide(new Key(" "), new Value(""))).isFalse();
    }

}
