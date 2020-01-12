package pl.szczepanik.silencio.processors.visitors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.core.Configuration;
import pl.szczepanik.silencio.core.Execution;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.mocks.ConverterVisitor;
import pl.szczepanik.silencio.utils.ResourceLoader;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class YAMLVisitorTest extends GenericTest {

    @Test
    public void shouldReportExceptionOnUnsupportedModel() throws Exception {

        // when
        final String key = "myKey";
        final Object value = new Object();
        YAMLVisitor parser = new YAMLVisitor();

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage("Unknown type of the key: " + value.getClass().getName());
        Whitebox.invokeMethod(parser, "processComplex", key, value);
    }

    @Test
    public void shouldVisitAllYamlNodes() throws IOException {

        final int nodeCounter = 16;

        // given
        input = ResourceLoader.loadYamlAsReader("suv.yaml");
        Map<String, Object> yamlStructure = new ObjectMapper(new YAMLFactory()).readValue(input,
                new TypeReference<Map<String, Object>>() {
                });
        ConverterVisitor visitCounter = new ConverterVisitor();
        Execution execution = new Execution(new PositiveDecision(), visitCounter);
        YAMLVisitor visitor = new YAMLVisitor();
        visitor.setConfiguration(new Configuration(execution));

        // when
        visitor.processYaml(yamlStructure);

        // then
        assertThat(visitCounter.getVisitCounter()).isEqualTo(nodeCounter);
    }
}
