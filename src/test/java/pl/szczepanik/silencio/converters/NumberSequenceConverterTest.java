package pl.szczepanik.silencio.converters;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;
import pl.szczepanik.silencio.utils.ReflectionUtility;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class NumberSequenceConverterTest {

    @Test
    public void shouldReturnValueWhenPassingNull() {

        // given
        Converter blank = new NumberSequenceConverter();
        Key key = new Key("funnyKey");
        Value value = new Value(null);

        // when
        blank.init();

        // then
        assertThat(blank.convert(key, value).getValue()).isEqualTo(0);
    }

    @Test
    public void shouldReturnSameNumbersWhenPassingEqualsValues() {

        // given having 2 values with the same value (compareTo returns true) but different reference
        Converter blank = new NumberSequenceConverter();
        Key key = new Key("funnyKey");
        Value inputValue1 = new Value(Integer.toBinaryString(12345));
        Value inputValue2 = new Value(Long.toBinaryString(12345));

        // when
        blank.init();
        Value outputValue1 = blank.convert(key, inputValue1);
        Value outputValue2 = blank.convert(key, inputValue2);

        // then
        assertThat(inputValue1.getValue()).isEqualTo(inputValue2.getValue());
        assertThat(outputValue1.getValue()).isEqualTo(outputValue2.getValue());
    }

    @Test
    public void shouldReturnDifferentNumberWhenPassingDifferentValue() {

        Converter blank = new NumberSequenceConverter();
        Key key = new Key("funnyKey");
        Value inputValue1 = new Value(Integer.toBinaryString(12345));
        Value inputValue2 = new Value(Integer.toBinaryString(54321));

        // when
        blank.init();
        Value outputValue1 = blank.convert(key, inputValue1);
        Value outputValue2 = blank.convert(key, inputValue2);

        // then
        assertThat(inputValue1.getValue()).isNotEqualTo(inputValue2.getValue());
        assertThat(outputValue1.getValue()).isNotEqualTo(outputValue2.getValue());
    }

    @Test
    public void shouldReturnSameNumberWhenPassingSameReference() {

        // given
        Converter blank = new NumberSequenceConverter();
        Key key = new Key("funnyKey");
        Value value = new Value(Integer.toBinaryString(12345));

        // when
        blank.init();
        Value outputValue1 = blank.convert(key, value);
        Value outputValue2 = blank.convert(key, value);

        // then
        assertThat(outputValue1.getValue()).isEqualTo(outputValue2.getValue());
    }

    @Test
    public void shouldClearHistoryOnInit() {

        // given
        Converter blank = new NumberSequenceConverter();
        Map<Object, Integer> values = new HashMap<>();
        values.put(this, 0);

        // when
        ReflectionUtility.setField(blank, "values", values);
        blank.init();

        // then
        Map<Object, Integer> retValues = (Map) ReflectionUtility.getField(blank, "values");
        assertThat(retValues).isEmpty();
    }
}
