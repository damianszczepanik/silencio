package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class KeyTest {

    @Test
    public void shouldReturnKey() {

        // given
        String keyString = "oneKey";

        // when
        Key key = new Key(keyString);

        // then
        assertThat(key.getKey()).isEqualTo(keyString);
    }

}
