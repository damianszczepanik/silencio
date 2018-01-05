package pl.szczepanik.silencio.integration;

import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.Builder;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
@RunWith(Parameterized.class)
public class EmptyContentIntegrationTest extends GenericTest {

    @Parameter(value = 0)
    public Format format;

    @Parameter(value = 1)
    public Reader emptyInput;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { Format.XML,        ResourceLoader.loadXmlAsReader("minimal.xml") },
            { Format.JSON,       ResourceLoader.loadJsonAsReader("empty.json") },
            { Format.PROPERTIES, ResourceLoader.loadPropertiesAsReader("empty.properties") },
        });
    }

    @Test
    public void shouldNotFailOnEmptyContent() {

        // given
        Builder builder = new Builder(format).with(Builder.BLANK);
        Processor processor = builder.build();
        processor.load(emptyInput);

        // then
        processor.process();

        // then
        // no crash
    }

    @After
    public void cleanStream() {
        IOUtils.closeQuietly(emptyInput);
    }
}
