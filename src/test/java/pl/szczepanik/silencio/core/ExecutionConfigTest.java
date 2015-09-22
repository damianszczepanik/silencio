package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Decision;
import pl.szczepanik.silencio.converters.BlankConverter;
import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.stubs.StubConverter;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ExecutionConfigTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldReturnPassedValues() {

        // given
        Decision[] decisions = { new PositiveDecision() };
        Converter[] converters = { new BlankConverter(), new StubConverter() };

        // when
        ExecutionConfig round = new ExecutionConfig(decisions, converters);

        // then
        assertThat(round.getConverters()).isEqualTo(converters);
        assertThat(round.getDecisions()).isEqualTo(decisions);
    }

    @Test
    public void shouldFailWhenPassingEmtptyDecisions() {

        // given
        Decision[] decisions = {};
        Converter[] converters = { new BlankConverter() };

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("Array with Decisions must not be empty!");
        new ExecutionConfig(decisions, converters);
    }

    @Test
    public void shouldFailWhenPassingNullDecisions() {

        // given
        Decision[] decisions = { null };
        Converter[] converters = { new BlankConverter() };

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("Passed Decision is null!");
        new ExecutionConfig(decisions, converters);
    }

    @Test
    public void shouldFailWhenPassingEmtptyConverter() {

        // given
        Decision[] decisions = { new PositiveDecision() };
        Converter[] converters = {};

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("Array with Converters must not be empty!");
        new ExecutionConfig(decisions, converters);
    }

    @Test
    public void shouldFailWhenPassingNullConverter() {

        // given
        Decision[] decisions = { new PositiveDecision() };
        Converter[] converters = { null };

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("Passed Converter is null!");
        new ExecutionConfig(decisions, converters);
    }
}
