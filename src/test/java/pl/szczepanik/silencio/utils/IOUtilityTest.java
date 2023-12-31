package pl.szczepanik.silencio.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.core.IntegrityException;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(IOUtils.class)
@PowerMockIgnore("jdk.internal.reflect.*")
public class IOUtilityTest extends GenericTest {

    private static final String INVALID_HTML_PAGE = "This does not look like valid HTML page";
    private static final String URL_ADDRESS = "http://www.fancy.page";

    @Test
    public void shouldReturnWholeHtmlPage() throws Exception {

        // given
        mockStatic(IOUtils.class);
        when(IOUtils.toString(new URL(URL_ADDRESS), StandardCharsets.UTF_8))
                .thenReturn(INVALID_HTML_PAGE);

        // then
        String page = IOUtility.urlToString(new URL(URL_ADDRESS));

        // then
        assertThat(page).isEqualTo(INVALID_HTML_PAGE);
    }

    @Test
    public void shouldFailOnIOException() throws Exception {

        String errorMessage = "Something is wrong!";
        // when
        mockStatic(IOUtils.class);
        when(IOUtils.toString(new URL(URL_ADDRESS), StandardCharsets.UTF_8))
                .thenThrow(new IOException(errorMessage));

        // then
        assertThatThrownBy(() -> IOUtility.urlToString(new URL(URL_ADDRESS)))
                .isInstanceOf(IntegrityException.class)
                .hasMessage(errorMessage);
    }

    @Test
    public void shouldFailWhenUrlIsNotValid() {

        // given
        String invalidURL = "wwww.my@funpage";

        // then
        assertThatThrownBy(() -> IOUtility.createURL(invalidURL))
                .isInstanceOf(IntegrityException.class)
                .hasMessageContaining("no protocol: " + invalidURL);
    }
}
