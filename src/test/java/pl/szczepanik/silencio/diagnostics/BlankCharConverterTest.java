package pl.szczepanik.silencio.diagnostics;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

class BlankCharConverterTest extends GenericTest {

    @Test
    void shouldReturnConstantValue() {

        // given
        Converter converter = new WhiteCharConverter();

        Value value = new Value("value1");
        Key key = new Key("key1");

        // when
        Value reference = converter.convert(key, value);

        // then
        assertThat(reference.getValue()).isEqualTo(" ");
    }
}
