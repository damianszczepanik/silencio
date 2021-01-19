package pl.szczepanik.silencio.integration;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import org.junit.Test;
import pl.szczepanik.silencio.GenericTest;
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
public class BuilderIntegrationTest extends GenericTest {

    @Test
    public void shouldProcessPropertiesFile() throws IOException {

        // given
        Builder builder = new Builder(Format.PROPERTIES);
        builder.with(new MatcherDecision(".*(money|cash|price).*", null), Builder.BLANK)
                .with(new MatcherDecision(".*sunroof.*", ".*Optional.*"), new StringConverter("[Standard]"));
        Processor processor = builder.build();

        input = ResourceLoader.loadAsReader("suv.properties");
        output = new StringWriter();

        // when
        processor.load(input);
        processor.process();

        // then
        processor.write(output);

        Properties reference = new Properties();
        reference.load(new StringReader(ResourceLoader.loadAsString("suv_Matcher_Blank+Matcher_String.properties")));
        Properties converted = new Properties();
        converted.load(new StringReader(output.toString()));
        PropertiesUtility.assertEqual(reference, converted);
    }

}
