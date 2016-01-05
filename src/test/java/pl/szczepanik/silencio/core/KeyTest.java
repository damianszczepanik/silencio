package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pl.szczepanik.silencio.GenericTest;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class KeyTest extends GenericTest {

    @Test
    public void shouldReturnKey() {

        // given
        String keyString = "oneKey";

        // when
        Key key = new Key(keyString);

        // then
        assertThat(key.getKey()).isEqualTo(keyString);
    }

    @Test
    public void shouldReturnValidFormat() {

        // given
        String keyName = "golden key";
        Key key = new Key(keyName);

        // when
        String dump = key.toString();

        // then
        assertThat(dump).isEqualTo(String.format("Key [key='%s']", keyName));
    }
}
