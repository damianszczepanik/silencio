package pl.szczepanik.silencio.converters;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class NumberSequenceTest {

    @Test
    public void shouldReturnValueWhenPassingNull() {

        // given
        Converter blank = new NumberSequenceConverter();
        Key key = new Key("funnyKey");
        Value value = new Value(null);

        // when
        blank.init();

        // then
        assertThat(blank.convert(key, value).getValue()).isNotNull();
    }

    @Test
    public void shouldReturnSameValuesWhenPassingEqualsValues() {

        // given having 2 values with the same value (compareTo returns true) but different reference
        Converter blank = new NumberSequenceConverter();
        Key key = new Key("funnyKey");
        Value value1 = new Value(Integer.toBinaryString(12345));
        Value value2 = new Value(Integer.toBinaryString(12345));

        // when
        blank.init();
        Value ref1 = blank.convert(key, value1);
        Value ref2 = blank.convert(key, value2);

        // then
        assertThat(ref1.getValue()).isEqualTo(ref2.getValue());
    }

    @Test
    public void shouldReturnDifferentValuesWhenPassingDifferentValue() {

        Converter blank = new NumberSequenceConverter();
        Key key = new Key("funnyKey");
        Value value1 = new Value(Integer.toBinaryString(12345));
        Value value2 = new Value(Integer.toBinaryString(54321));

        // when
        blank.init();
        Value ref1 = blank.convert(key, value1);
        Value ref2 = blank.convert(key, value2);

        // then
        assertThat(ref1.getValue()).isNotEqualTo(ref2.getValue());
    }

    @Test
    public void shouldReturnSameValuesWhenPassingSameValue() {

        // given
        Converter blank = new NumberSequenceConverter();
        Key key = new Key("funnyKey");
        Value value = new Value(Integer.toBinaryString(12345));

        // when
        blank.init();
        Value ref1 = blank.convert(key, value);
        Value ref2 = blank.convert(key, value);

        // then
        assertThat(ref1.getValue()).isEqualTo(ref2.getValue());
    }
}
