package pl.szczepanik.silencio.integration;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.converters.StringConverter;
import pl.szczepanik.silencio.core.Builder;
import pl.szczepanik.silencio.decisions.MatcherDecision;
import pl.szczepanik.silencio.utils.PropertiesUtility;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class BuilderTestInt {
    private Writer output;
    private Reader input;

    @Test
    public void shouldProcessPropertiesFile() throws IOException {

        // given
        input = ResourceLoader.loadPropertiesAsReader("suv.properties");
        output = new StringWriter();

        // when
        Builder builder = new Builder(Format.PROPERTIES);
        builder.with(new MatcherDecision(".*(money|cash|price).*", null), Builder.BLANK)
                .with(new MatcherDecision(".*sunroof.*", ".*Optional.*"), new StringConverter("[Standard]"));
        Processor processor = builder.build();
        processor.load(input);
        processor.process();
        processor.write(output);

        // then
        Properties reference = new Properties();
        reference.load(new StringReader(ResourceLoader.loadPropertiesAsString("suv_Matcher_Blank+Matcher_String.properties")));
        Properties converted = new Properties();
        converted.load(new StringReader(output.toString()));
        PropertiesUtility.assertEqual(reference, converted);
    }

    @After
    public void closeStreams() {
        IOUtils.closeQuietly(input);
        IOUtils.closeQuietly(output);
    }
}
