package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pl.szczepanik.silencio.GenericTest;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class ValueTest extends GenericTest {

    @Test
    public void shouldReturnValue() {

        // given
        String value = "secondValue";

        // when
        Value element = new Value(value);

        // then
        assertThat(element.getValue()).isEqualTo(value);
    }

}
