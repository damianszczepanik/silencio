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
public class MatcherDecisionTest extends GenericTest {

    @Test
    void shouldAcceptValuesAsWords() {

        // given
        Decision d = new MatcherDecision("\\w+");

        // then
        assertThat(d.decide(new Key(""), new Value(null))).isTrue();
        assertThat(d.decide(new Key("Hello"), new Value("World"))).isTrue();
        assertThat(d.decide(new Key("???"), new Value("World_1234"))).isTrue();
        assertThat(d.decide(new Key(""), new Value(""))).isFalse();
        assertThat(d.decide(new Key(null), new Value("-123"))).isFalse();
    }

    @Test
    void shouldNotAcceptKeysAsDigits() {

        // given
        Decision d = new MatcherDecision("[^\\d]", "foo");

        // then
        assertThat(d.decide(new Key("13456"), new Value("fooooofooofo"))).isFalse();
        assertThat(d.decide(new Key("T3ST"), new Value("ffffoo"))).isFalse();
        assertThat(d.decide(new Key("???"), new Value(null))).isFalse();
        assertThat(d.decide(new Key("Test"), new Value("foo"))).isFalse();
        assertThat(d.decide(new Key("WORLD@UNIVERSE"), new Value("fofofoo"))).isFalse();
    }

    @Test
    void shouldAcceptNullValues() {

        // given
        Decision d = new MatcherDecision(null, null);

        // then
        assertThat(d.decide(new Key(""), new Value(null))).isTrue();
        assertThat(d.decide(new Key("Hello"), new Value("World"))).isTrue();
        assertThat(d.decide(new Key(null), new Value("-123"))).isTrue();
    }

}
