package pl.szczepanik.silencio.processors.visitors;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.szczepanik.silencio.core.Configuration;
import pl.szczepanik.silencio.core.Execution;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.mocks.ConverterVisitor;
import pl.szczepanik.silencio.utils.ReflectionUtility;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class JSONVisitorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Reader input;

    @Test
    public void shouldReportExceptionOnUnsupportedModel() throws Exception {

        // when
        String key = "myKey";
        Object value = new Object();
        JSONVisitor parserr = new JSONVisitor();

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage("Unknown type of the key: " + value.getClass().getName());
        ReflectionUtility.invokeMethod(parserr, "processComplex", Void.class, key, value);
    }

    @Test
    public void shouldVisitAllJsonNodes() throws IOException {

        final int nodeCounter = 14;

        // given
        input = ResourceLoader.loadJsonAsReader("suv.json");
        Map<String, Object> jsonStructure = new ObjectMapper().readValue(input,
                new TypeReference<Map<String, Object>>() {
                });
        ConverterVisitor visitCounter = new ConverterVisitor();
        Execution execution = new Execution(new PositiveDecision(), visitCounter);
        JSONVisitor visitor = new JSONVisitor();
        visitor.setConfiguration(new Configuration(execution));

        // when
        visitor.process(jsonStructure);

        // then
        assertThat(visitCounter.getVisitCounter()).isEqualTo(nodeCounter);
    }

    @After
    public void closeStreams() {
        IOUtils.closeQuietly(input);
    }

}
