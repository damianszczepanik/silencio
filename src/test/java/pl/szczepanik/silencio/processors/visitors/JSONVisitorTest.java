package pl.szczepanik.silencio.processors.visitors;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.core.Configuration;
import pl.szczepanik.silencio.core.Execution;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.mocks.ConverterVisitor;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class JSONVisitorTest extends GenericTest {

    @Test
    public void shouldReportExceptionOnUnsupportedModel() throws Exception {

        // when
        final String key = "myKey";
        final Object value = new Object();
        JSONVisitor parser = new JSONVisitor();

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage("Unknown type of the key: " + value.getClass().getName());
        Whitebox.invokeMethod(parser, "processComplex", key, value);
    }

    @Test
    public void shouldVisitAllJsonNodes() throws IOException {

        final int nodeCounter = 14;

        // given
        input = ResourceLoader.loadAsReader("suv.json");
        Map<String, Object> jsonStructure = new ObjectMapper().readValue(input,
                new TypeReference<Map<String, Object>>() {
                });
        ConverterVisitor visitCounter = new ConverterVisitor();
        Execution execution = new Execution(new PositiveDecision(), visitCounter);
        JSONVisitor visitor = new JSONVisitor();
        visitor.setConfiguration(new Configuration(execution));

        // when
        visitor.processJSON(jsonStructure);

        // then
        assertThat(visitCounter.getVisitCounter()).isEqualTo(nodeCounter);
    }
}
