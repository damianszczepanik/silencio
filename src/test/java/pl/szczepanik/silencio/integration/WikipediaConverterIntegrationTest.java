package pl.szczepanik.silencio.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.converters.WikipediaConverter;
import pl.szczepanik.silencio.core.Builder;
import pl.szczepanik.silencio.utils.JSONUtility;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class WikipediaConverterIntegrationTest extends GenericTest {

    @Test
    public void shouldGetWordsFromWikipediaOrg() throws IOException {
        // WARNING: this test fail when no Internet connection is available

        // given
        Processor processor = new Builder(Format.JSON).with(new WikipediaConverter()).build();
        input = ResourceLoader.loadAsReader("suv.json");
        output = new StringWriter();

        // when
        processor.load(input);
        processor.process();

        // then
        processor.write(output);
        assertThat(JSONUtility.matchesJsonToPattern(output.toString(), "suv-pattern.json")).isTrue();
    }
}
