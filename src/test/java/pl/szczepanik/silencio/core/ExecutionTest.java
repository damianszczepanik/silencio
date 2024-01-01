package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Decision;
import pl.szczepanik.silencio.converters.BlankConverter;
import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.stubs.StubConverter;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class ExecutionTest extends GenericTest {

    @Test
    void shouldReturnPassedValues() {

        // given
        Decision[] decisions = { new PositiveDecision() };
        Converter[] converters = { new BlankConverter(), new StubConverter() };

        // when
        Execution execution = new Execution(decisions, converters);

        // then
        assertThat(execution.getConverters()).isEqualTo(converters);
        assertThat(execution.getDecisions()).isEqualTo(decisions);
    }

    @Test
    void shouldFailWhenPassingEmtptyDecisions() {

        // given
        Decision[] decisions = {};
        Converter[] converters = { new BlankConverter() };

        // then
        assertThatThrownBy(() -> new Execution(decisions, converters))
                .isInstanceOf(IntegrityException.class)
                .hasMessage("Array with Decisions must not be empty!");
    }

    @Test
    void shouldFailWhenPassingNullDecisions() {

        // given
        Decision[] decisions = { null };
        Converter[] converters = { new BlankConverter() };

        // then
        assertThatThrownBy(() -> new Execution(decisions, converters))
                .isInstanceOf(IntegrityException.class)
                .hasMessage("None of passed Decision can be null!");
    }

    @Test
    void shouldFailWhenPassingEmtptyConverter() {

        // given
        Decision[] decisions = { new PositiveDecision() };
        Converter[] converters = {};

        // then
        assertThatThrownBy(() -> new Execution(decisions, converters))
                .isInstanceOf(IntegrityException.class)
                .hasMessage("Array with Converters must not be empty!");
    }

    @Test
    void shouldFailWhenPassingNullConverter() {

        // given
        Decision[] decisions = { new PositiveDecision() };
        Converter[] converters = { null };

        // then
        assertThatThrownBy(() -> new Execution(decisions, converters))
                .isInstanceOf(IntegrityException.class)
                .hasMessage("None of passed Converter can be null!");
    }
}
