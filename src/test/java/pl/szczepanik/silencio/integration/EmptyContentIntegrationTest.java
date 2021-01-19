package pl.szczepanik.silencio.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;

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
    public String fileName;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Format.XML, "minimal.xml"},
                {Format.JSON, "empty.json"},
                {Format.PROPERTIES, "empty.properties"},
                {Format.YAML, "empty.yaml"},
        });
    }

    @Test
    public void shouldNotFailOnEmptyContent() {

        // given
        Builder builder = new Builder(format).with(Builder.BLANK);
        Processor processor = builder.build();
        input = ResourceLoader.loadAsReader(fileName);
        processor.load(input);

        // then
        processor.process();

        // then
        output = new StringWriter();
        processor.write(output);
        String reference = ResourceLoader.loadAsString(fileName);
        assertThat(output.toString()).isEqualToNormalizingNewlines(output.toString());
    }
}
