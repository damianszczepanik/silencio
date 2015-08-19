package pl.szczepanik.silencio.base;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pl.szczepanik.silencio.api.Format;

/**
 * Default factory implementation that holds generators.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class FormatTest {

    @Test
    public void shouldReturnFormatName() {

        // given
        String formatName = "sunnyFormat";

        // when
        Format format = new Format(formatName);

        // then
        assertThat(format.getName()).isEqualTo(formatName);
    }

    @Test
    public void shouldNotAllowForEmptyName() {

        // when
        String emptyName = "";

        // then
        try {
            new Format(emptyName);
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Name of the format must not be empty!");
        }

    }
}
