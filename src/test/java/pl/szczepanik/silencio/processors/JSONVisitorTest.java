package pl.szczepanik.silencio.processors;

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

import pl.szczepanik.silencio.core.Processable;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.core.Value;
import pl.szczepanik.silencio.stubs.StubProcessable;
import pl.szczepanik.silencio.utils.ReflectionUtils;
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
        JSONVisitor parserr = new JSONVisitor(new StubProcessable());

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage("Unknown type of the key: " + value.getClass().getName());
        ReflectionUtils.invokeMethod(parserr, "processComplex", Void.class, key, value);
    }

    @Test
    public void shouldVisitAllJsonNodes() throws IOException {

        final int nodeCounter = 12;

        // given
        input = ResourceLoader.loadJsonAsReader("suv.json");
        VisitorProcessable visitor = new VisitorProcessable();
        Map<String, Object> jsonStructure = new ObjectMapper().readValue(input,
                new TypeReference<Map<String, Object>>() {
                });

        // when
        JSONVisitor parser = new JSONVisitor(visitor);
        parser.process(jsonStructure);

        // then
        assertThat(visitor.visitCounter).isEqualTo(nodeCounter);
    }

    @After
    public void closeStreams() {
        IOUtils.closeQuietly(input);
    }

    private final class VisitorProcessable implements Processable {
        public int visitCounter;

        @Override
        public Value processValue(String key, Object value) {
            visitCounter++;
            return new Value(value);
        }
    }
}
