package pl.szczepanik.silencio.converters;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class NumberSequenceConverterTest extends GenericTest {

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

        blank.init();

        // when
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

        blank.init();

        // when
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

        blank.init();

        // when
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
        Whitebox.setInternalState(blank, "values", values);

        // when
        blank.init();

        // then
        Map<Object, Integer> retValues = Whitebox.getInternalState(blank, "values");
        assertThat(retValues).isEmpty();
    }
}
