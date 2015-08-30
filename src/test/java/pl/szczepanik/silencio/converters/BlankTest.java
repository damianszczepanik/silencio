package pl.szczepanik.silencio.converters;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;
import pl.szczepanik.silencio.stubs.StubFormat;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class BlankTest {

    @Test
    public void shouldReturnEmptyValue() {

        // given
        Converter blank = new BlankConverter();
        Key key = new Key("funnyKey");
        Value value = new Value(StubFormat.class);

        // when
        blank.init();

        // then
        assertThat(blank.convert(key, value).getValue()).isEqualTo("");
    }

}
