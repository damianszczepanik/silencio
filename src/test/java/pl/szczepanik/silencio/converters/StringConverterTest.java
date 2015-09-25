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
public class StringConverterTest {

    @Test
    public void shouldReturnEmptyValue() {

        // given
        String pattern = "constant";
        Converter string = new StringConverter(pattern);
        Key key = new Key("funnyKey");
        Value value = new Value(StubFormat.class);

        // when
        string.init();
        Value retValue = string.convert(key, value);

        // then
        assertThat(retValue.getValue()).isEqualTo(pattern);
    }

}
