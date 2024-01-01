package pl.szczepanik.silencio.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.core.IntegrityException;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class IOUtilityTest extends GenericTest {

    private static final String INVALID_HTML_PAGE = "This does not look like valid HTML page";
    private static final String URL_ADDRESS = "http://www.fancy.page";

    private MockedStatic<IOUtils> mockedStatic;

    @BeforeEach
    void mockBefore() {
        mockedStatic = mockStatic(IOUtils.class);
    }

    @AfterEach
    void closeAfter() {
        mockedStatic.close();
    }

    @Test
    void shouldReturnWholeHtmlPage() throws Exception {

        // given
        when(IOUtils.toString(new URL(URL_ADDRESS), StandardCharsets.UTF_8))
                .thenReturn(INVALID_HTML_PAGE);

        // then
        String page = IOUtility.urlToString(new URL(URL_ADDRESS));

        // then
        assertThat(page).isEqualTo(INVALID_HTML_PAGE);
    }

    @Test
    void shouldFailOnIOException() throws Exception {

        String errorMessage = "Something is wrong!";

        // given
        URL url = new URL(URL_ADDRESS);

        // when
        when(IOUtils.toString(url, StandardCharsets.UTF_8))
                .thenThrow(new IOException(errorMessage));

        // then
        assertThatThrownBy(() -> IOUtility.urlToString(url))
                .isInstanceOf(IntegrityException.class)
                .hasMessage(errorMessage);
    }

    @Test
    void shouldFailWhenUrlIsNotValid() {

        // given
        String invalidURL = "wwww.my@funpage";

        // then
        assertThatThrownBy(() -> IOUtility.createURL(invalidURL))
                .isInstanceOf(IntegrityException.class)
                .hasMessageContaining("no protocol: " + invalidURL);
    }
}
