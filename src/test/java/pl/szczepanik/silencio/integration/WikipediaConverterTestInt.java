package pl.szczepanik.silencio.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.converters.WikipediaConverter;
import pl.szczepanik.silencio.core.Builder;
import pl.szczepanik.silencio.utils.JSONUtility;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class WikipediaConverterTestInt {

    private Writer output;
    private Reader input;

    @Test
    public void shouldGetWordsFromWikipediaOrg() throws IOException {
        // WARNING: this test fail when no Internet connection is available

        // given
        input = ResourceLoader.loadJsonAsReader("suv.json");
        output = new StringWriter();

        // when
        Processor processor = new Builder(Format.JSON).with(new WikipediaConverter()).build();
        processor.load(input);
        processor.process();
        processor.write(output);

        // then
        assertThat(JSONUtility.matchesJsonToPattern(output.toString(), "suv-pattern.json")).isTrue();
    }

    @After
    public void closeStreams() {
        IOUtils.closeQuietly(input);
        IOUtils.closeQuietly(output);
    }
}
