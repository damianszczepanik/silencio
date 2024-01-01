package pl.szczepanik.silencio.processors.visitors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;
import pl.szczepanik.silencio.stubs.StubAbstractVisitor;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
class AbstractVisitorTest extends GenericTest {

    @Test
    void shouldFailWhenPassingValueToProcessValue() {

        // given
        Key key = new Key("123");
        Object value = new Value("321");
        AbstractVisitor visitor = new StubAbstractVisitor();

        // then
        assertThatThrownBy(() -> visitor.processValue(key, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(AbstractVisitor.EXCEPTION_MESSAGE_INVALID_VALUE_TYPE);
    }
}
