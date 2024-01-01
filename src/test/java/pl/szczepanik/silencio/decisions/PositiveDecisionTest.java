package pl.szczepanik.silencio.decisions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Decision;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class PositiveDecisionTest extends GenericTest {

    @Test
    void shouldAllowForConversion() {

        // given
        Decision d = new PositiveDecision();

        // then
        assertThat(d.decide(null, null)).isTrue();
        assertThat(d.decide(new Key(""), new Value(null))).isTrue();
        assertThat(d.decide(new Key("Hello"), new Value(33L))).isTrue();
        assertThat(d.decide(new Key("foo"), new Value(this))).isTrue();
        assertThat(d.decide(new Key(" "), new Value(""))).isTrue();
    }

}
