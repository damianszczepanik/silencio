package pl.szczepanik.silencio.converters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.Configuration;
import pl.szczepanik.silencio.core.Execution;
import pl.szczepanik.silencio.core.IntegrityException;
import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.processors.JSONProcessor;
import pl.szczepanik.silencio.utils.IOUtility;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(IOUtility.class)
@PowerMockIgnore("jdk.internal.reflect.*")
public class WikipediaConverterTest extends GenericTest {

    private static final String URL_ADDRESS = "https://en.m.wikipedia.org/wiki/Special:Random";
    private static final String INVALID_HTML_PAGE = "This does not look like valid HTML page";

    @Test
    public void shouldFailWhenServerReturnsInvalidPage() throws IOException {

        // given
        Converter wikipedia = new WikipediaConverter();
        input = ResourceLoader.loadAsReader("suv.json");
        Processor processor = new JSONProcessor();
        Execution execution = new Execution(new PositiveDecision(), wikipedia);
        processor.setConfiguration(new Configuration(execution));
        processor.load(input);

        // when
        mockStatic(IOUtility.class);
        when(IOUtility.urlToString(new URL(URL_ADDRESS)))
                .thenReturn(INVALID_HTML_PAGE);

        // then
        assertThatThrownBy(() -> processor.process())
                .isInstanceOf(IntegrityException.class)
                .hasMessage("Could not find header pattern for page: " + INVALID_HTML_PAGE);
    }

    @Test
    public void shouldConvertWholeFile() throws IOException {

        // given
        Converter converter = new WikipediaConverter();
        input = ResourceLoader.loadAsReader("suv.json");
        output = new StringWriter();
        Processor processor = new JSONProcessor();
        Execution execution = new Execution(new PositiveDecision(), converter);
        processor.setConfiguration(new Configuration(execution));
        processor.load(input);

        // when
        mockStatic(IOUtility.class);
        when(IOUtility.urlToString(new URL(URL_ADDRESS)))
                .thenReturn(toWikiPage("George Washington"))
                .thenReturn(toWikiPage("John Adams"))
                .thenReturn(toWikiPage("George Washington"))
                .thenReturn(toWikiPage("George Washington")) // duplicate to check elimination duplicates
                .thenReturn(toWikiPage("Thomas Jefferson"))
                .thenReturn(toWikiPage("James Madison"))
                .thenReturn(toWikiPageItalics("James Monroe")) // with italics
                .thenReturn(toWikiPage("John Quincy Adams")) // one more time
                .thenReturn(toWikiPage("Andrew Jackson"))
                .thenReturn(toWikiPageItalics("Andrew Jackson")) // with italics
                .thenReturn(toWikiPage("Martin Van Buren"))
                .thenReturn(toWikiPage("William Henry Harrison"))
                .thenReturn(toWikiPage("John Tyler"))
                .thenReturn(toWikiPage("James Polk"))
                .thenReturn(toWikiPage("James Polk"))
                .thenReturn(toWikiPage("Zachary Taylor"))
                .thenThrow(new IllegalArgumentException("Trying to parse more elements than expected!"));

        processor.process();
        processor.write(output);

        // then
        String reference = ResourceLoader.loadAsString("suv_Positive_Wikipedia.json");
        assertThat(output).hasToString(reference);
    }

    @Test
    public void shouldClearHistoryOnInit() {

        // given
        Converter blank = new WikipediaConverter();
        Map<Object, Integer> values = new HashMap<>();
        values.put(this, 0);
        Set<String> words = new HashSet<>();
        words.add(null);

        Whitebox.setInternalState(blank, "values", values);
        Whitebox.setInternalState(blank, "words", words);

        // when
        blank.init();

        // then
        Map<Object, Integer> retValues = Whitebox.getInternalState(blank, "values");
        assertThat(retValues).isEmpty();
        Set<String> retWords = Whitebox.getInternalState(blank, "words");
        assertThat(retWords).isEmpty();
    }

    private static String toWikiPage(String text) {
        return String.format("ble bla bla <h1 id=\"firstHeading\" class=\"firstHeading mw-first-heading\">%s</h1> ble ble ble", text);
    }

    private static String toWikiPageItalics(String text) {
        return String.format("<h1 id=\"firstHeading\" class=\"firstHeading mw-first-heading\"><i>%s</i></h1> something, something", text);
    }

}
