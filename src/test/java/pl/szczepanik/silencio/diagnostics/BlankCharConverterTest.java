package pl.szczepanik.silencio.diagnostics;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

public class BlankCharConverterTest extends GenericTest {

    @Test
    public void shouldReturnConstantValue() {

        // given
        Value value = new Value("value1");
        Key key = new Key("key1");

        // when
        Converter converter = new WhiteCharConverter();
        Value reference = converter.convert(key, value);

        // then
        assertThat(reference.getValue()).isEqualTo(" ");
    }
}
