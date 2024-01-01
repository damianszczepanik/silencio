package pl.szczepanik.silencio.diagnostics;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

public class ConstantValueConverterTest extends GenericTest {

    @Test
    public void shouldReturnConstantValue() {

        // given
        Converter converter = new ConstantValueConverter();

        Value value1 = new Value("value1");
        Key key1 = new Key("key1");
        Value value2 = new Value("value2");
        Key key2 = new Key("key2");

        // when
        Value reference = converter.convert(key1, value1);

        // then
        assertThat(converter.convert(key2, value2)).isEqualTo(reference);
    }
}
