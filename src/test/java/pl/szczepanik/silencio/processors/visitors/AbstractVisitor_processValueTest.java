package pl.szczepanik.silencio.processors.visitors;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
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
public class AbstractVisitor_processValueTest extends GenericTest {

    private static final Key key = new Key("myKey");
    private static final Object value = "yourValue";
    private static final ConverterVisitor visitCounter = new ConverterVisitor();

    public Decision[] decisions;

    public Converter[] processors;

    public Key expectedKey;

    public Object expectedValue;

    public Object expectVisitCounter;

    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {
                        new Decision[] { new PositiveDecision() },
                        new Converter[] { visitCounter, visitCounter },
                        key,
                        value,
                        2
                },
                {
                        new Decision[] { new PositiveDecision(), new NegativeDecision() },
                        new Converter[] { visitCounter, visitCounter },
                        null,
                        value,
                        0
                },
        });
    }


    @MethodSource("data")
    @ParameterizedTest(name = "\"{0}\" with \"{1}\"")
    void shouldProcessAllConvertersForPositiveDecision(
            Decision[] decisions,
            Converter[] processors,
            Key expectedKey,
            Object expectedValue,
            Object expectVisitCounter) {

        initDataWithNameTest(decisions, processors, expectedKey, expectedValue, expectVisitCounter);

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

    @AfterEach
    void reset() {
        visitCounter.reset();
    }

    private void initDataWithNameTest(
            Decision[] decisions,
            Converter[] processors,
            Key expectedKey,
            Object expectedValue,
            Object expectVisitCounter) {
        this.decisions = decisions;
        this.processors = processors;
        this.expectedKey = expectedKey;
        this.expectedValue = expectedValue;
        this.expectVisitCounter = expectVisitCounter;
    }
}
