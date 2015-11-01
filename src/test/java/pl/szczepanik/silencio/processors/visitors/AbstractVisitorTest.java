package pl.szczepanik.silencio.processors.visitors;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Decision;
import pl.szczepanik.silencio.core.Configuration;
import pl.szczepanik.silencio.core.Execution;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;
import pl.szczepanik.silencio.decisions.NegativeDecision;
import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.mocks.ConverterVisitor;
import pl.szczepanik.silencio.stubs.StubAbstractVisitor;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
@RunWith(Parameterized.class)
public class AbstractVisitorTest extends GenericTest {

    private static final Key key = new Key("myKey");
    private static final Object value = "yourValue";


    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            {
                new Decision[] {new PositiveDecision()},
                new Converter[] { visitCounter, visitCounter },
                key,
                value,
                2
            },
            {
                new Decision[] {new PositiveDecision(), new NegativeDecision()},
                new Converter[] { visitCounter, visitCounter },
                null,
                value,
                0
            },
        });
    }
    
    @Parameter(value = 0)
    public Decision[] decisions;

    @Parameter(value = 1)
    public Converter[] processors;

    @Parameter(value = 2)
    public Key expectedKey;

    @Parameter(value = 3)
    public Object expectedValue;

    @Parameter(value = 4)
    public Object expectVisitCounter;
    
    private static final ConverterVisitor visitCounter = new ConverterVisitor();

    @Test
    public void shouldProcessAllConvertersForPositiveDecision() {
        // given
        StubAbstractVisitor visitor = new StubAbstractVisitor();
        Execution execution = new Execution(decisions, processors);
        visitor.setConfiguration(new Configuration(execution));

        // when
        Value retValue = visitor.processValue(key, value);

        // then
        assertThat(visitCounter.getKey()).isEqualTo(expectedKey);
        assertThat(retValue.getValue()).isEqualTo(expectedValue);
        assertThat(visitCounter.getVisitCounter()).isEqualTo(expectVisitCounter);
    }


    @After
    public void reset() {
        visitCounter.reset();
    }
}
