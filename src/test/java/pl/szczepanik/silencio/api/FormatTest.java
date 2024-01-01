package pl.szczepanik.silencio.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.core.IntegrityException;
import pl.szczepanik.silencio.stubs.StubFormat;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
class FormatTest extends GenericTest {

    @Test
    void shouldReturnFormatName() {

        // given
        String formatName = "sunnyFormat";

        // when
        Format format = new StubFormat(formatName);

        // then
        assertThat(format.getName()).isEqualTo(formatName);
    }

    @Test
    void shouldNotAllowForEmptyName() {

        // when
        String emptyName = StringUtils.EMPTY;

        // then
        assertThatThrownBy(() -> new StubFormat(emptyName))
                .isInstanceOf(IntegrityException.class)
                .hasMessage("Name of the format must not be empty!");
    }

    @Test
    void shouldEqualsWhenNameIsEqual() {

        // given
        Format one = new StubFormat("one");
        Format two = new StubFormat("one");

        // when
        boolean compare = one.equals(two);

        // then
        assertThat(compare).isTrue();
    }

    @Test
    void shouldNotEqualsWhenNameIsNotEqual() {

        // given
        Format one = new StubFormat("one");
        Format two = new StubFormat("One");

        // when
        boolean compare = one.equals(two);

        // then
        assertThat(compare).isFalse();
    }

    @Test
    void shouldEqualsWithAnyObject() {

        // when
        Format one = Format.JSON;

        // then
        assertThat(one).isEqualTo(one);
        assertThat(one).isNotNull();
    }

    @Test
    void shouldNotEqualsWithNull() {

        // when
        Format one = Format.JSON;

        // when
        boolean compare = one.equals(null);

        // then
        assertThat(compare).isFalse();
    }

    @Test
    void shouldReturnHashCodeOfName() {

        // given
        String name = "crazyName";

        // when
        Format format = new StubFormat(name);

        // then
        assertThat(format.hashCode()).hasSameHashCodeAs(name);
    }

    @Test
    void shouldReturnValidFormat() {

        // given
        String formatName = "some, some, some";
        Format format = new Format(formatName);

        // when
        String dump = format.toString();

        // then
        assertThat(dump).isEqualTo(String.format("Format [name='%s']", formatName));
    }
}
