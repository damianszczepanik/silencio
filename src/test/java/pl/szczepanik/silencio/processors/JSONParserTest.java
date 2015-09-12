package pl.szczepanik.silencio.processors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.stubs.StubProcessable;
import pl.szczepanik.silencio.utils.ReflectionUtils;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class JSONParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldReportExceptionOnUnsupportedModel() throws Exception {

        // when
        String key = "myKey";
        Object value = new Object();
        JSONParser parserr = new JSONParser(new StubProcessable());

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage("Unknown type of the key: " + value.getClass().getName());
        ReflectionUtils.invokeMethod(parserr, "processComplex", Void.class, key, value);
    }

}
