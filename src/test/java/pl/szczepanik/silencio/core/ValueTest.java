package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.szczepanik.silencio.GenericTest;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class ValueTest extends GenericTest {

    @Test
    void shouldReturnValue() {

        // given
        String value = "secondValue";

        // when
        Value element = new Value(value);

        // then
        assertThat(element.getValue()).isEqualTo(value);
    }

    @Test
    void shouldReturnValidFormat() {

        // given
        String valueName = "go, go go!";
        Value value = new Value(valueName);

        // when
        String dump = value.toString();

        // then
        assertThat(dump).isEqualTo(String.format("Value [value='%s']", valueName));
    }
}
