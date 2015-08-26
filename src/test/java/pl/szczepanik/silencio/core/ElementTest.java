package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ElementTest {

    @Test
    public void shouldReturnKeyAndVAlue() {

        // given
        String key = "oneKey";
        String value = "twoValue";

        // when
        Element element = new Element(key, value);

        // then
        assertThat(element.getKey()).isEqualTo(key);
        assertThat(element.getValue()).isEqualTo(value);
    }

}
