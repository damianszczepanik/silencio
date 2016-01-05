package pl.szczepanik.silencio.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.core.IntegrityException;
import pl.szczepanik.silencio.stubs.StubFormat;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class FormatTest extends GenericTest {

    @Test
    public void shouldReturnFormatName() {

        // given
        String formatName = "sunnyFormat";

        // when
        Format format = new StubFormat(formatName);

        // then
        assertThat(format.getName()).isEqualTo(formatName);
    }

    @Test
    public void shouldNotAllowForEmptyName() {

        // when
        String emptyName = StringUtils.EMPTY;

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("Name of the format must not be empty!");
        new StubFormat(emptyName);
    }

    @Test
    public void shouldEqualsWhenNameIsEqual() {

        // given
        Format one = new StubFormat("one");
        Format two = new StubFormat("one");

        // when
        boolean compare = one.equals(two);

        // then
        assertThat(compare).isTrue();
    }

    @Test
    public void shouldNotEqualsWhenNameIsNotEqual() {

        // given
        Format one = new StubFormat("one");
        Format two = new StubFormat("One");

        // when
        boolean compare = one.equals(two);

        // then
        assertThat(compare).isFalse();
    }

    @Test
    public void shouldEqualsWithAnyObject() {

        // when
        Format one = Format.JSON;

        // then
        assertThat(one.equals(one)).isTrue();
        assertThat(one.equals(null)).isFalse();
        assertThat(one.equals("one")).isFalse();
    }

    @Test
    public void shouldReeturnHashCodeOfName() {

        // given
        String name = "crazyName";

        // when
        Format format = new StubFormat(name);

        // then
        assertThat(format.hashCode()).isEqualTo(name.hashCode());
    }

    @Test
    public void shouldReturnValidFormat() {

        // given
        String formatName = "some, some, some";
        Format format = new Format(formatName);

        // when
        String dump = format.toString();

        // then
        assertThat(dump).isEqualTo(String.format("Format [name='%s']", formatName));
    }
}
