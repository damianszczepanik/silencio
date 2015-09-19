package pl.szczepanik.silencio.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import pl.szczepanik.silencio.core.IntegrityException;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(IOUtils.class)
public class IOUtilsTest {
    
    private static final String INVALID_HTML_PAGE = "This does not look like valid HTML page";
    private static final String URL_ADDRESS = "http://www.fancy.page";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldReturnWholeHtmlPage() throws Exception {
        
        // given
        mockStatic(IOUtils.class);
        when(IOUtils.toString(new URL(URL_ADDRESS)))
            .thenReturn(INVALID_HTML_PAGE);

        // then
        String page = pl.szczepanik.silencio.utils.IOUtils.urlToString(new URL(URL_ADDRESS));

        // then
        assertThat(page).isEqualTo(INVALID_HTML_PAGE);
    }

    @Test
    public void shouldFailOnIOException() throws Exception {

        String errorMessage = "Something is wrong!";
        // when
        mockStatic(IOUtils.class);
        when(IOUtils.toString(new URL(URL_ADDRESS)))
            .thenThrow(new IOException(errorMessage));

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage(errorMessage);
        pl.szczepanik.silencio.utils.IOUtils.urlToString(new URL(URL_ADDRESS));
    }

    @Test
    public void shouldFailWhenUrlIsNotValid() {

        // given
        String invalidURL = "wwww.my@funpage";

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("no protocol: " + invalidURL);
        pl.szczepanik.silencio.utils.IOUtils.createURL(invalidURL);
    }
}
