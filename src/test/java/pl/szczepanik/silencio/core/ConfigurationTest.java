package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.stubs.StubConverter;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ConfigurationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldReturnPassedExecutions() {

        // given
        Execution[] executions = { new Execution(new PositiveDecision(), new StubConverter()) };

        // when
        Configuration configuration = new Configuration(executions);

        // then
        assertThat(configuration.getExecutions()).isEqualTo(executions);
    }

    @Test
    public void shouldFailWhenPassingEmtptyExecution() {

        // given
        Execution[] executors = {};

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("Executions must not be empty!");
        new Configuration(executors);
    }
}
