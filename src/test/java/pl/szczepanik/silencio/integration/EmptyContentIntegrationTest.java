package pl.szczepanik.silencio.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.Builder;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
class EmptyContentIntegrationTest extends GenericTest {

    public Format format;

    public String fileName;

    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Format.XML, "minimal.xml"},
                {Format.JSON, "empty.json"},
                {Format.PROPERTIES, "empty.properties"},
                {Format.YAML, "empty.yaml"},
        });
    }

    @MethodSource("data")
    @ParameterizedTest(name = "\"{0}\" with \"{1}\"")
    void shouldNotFailOnEmptyContent(Format format, String fileName) {

        initDataWithNameTest(format, fileName);

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
        assertThat(output.toString()).isEqualToNormalizingNewlines(output.toString());
    }

    private void initDataWithNameTest(Format format, String fileName) {
        this.format = format;
        this.fileName = fileName;
    }
}
