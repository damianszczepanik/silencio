package pl.szczepanik.silencio.diagnostics;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

class PassedValueConverterTest extends GenericTest {

    @Test
    void shouldReturnCorrectValue() {

        // given
        Value value = new Value("someValue");
        Key key = new Key("someKey");
        Converter converter = new PassedValueConverter();

        // when
        Value outputValue = converter.convert(key, value);

        // then
        assertThat(outputValue).isEqualTo(value);
    }
}
