package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.stubs.StubConverter;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class ConfigurationTest extends GenericTest {

    @Test
    void shouldReturnPassedExecutions() {

        // given
        Execution[] executions = { new Execution(new PositiveDecision(), new StubConverter()) };

        // when
        Configuration configuration = new Configuration(executions);

        // then
        assertThat(configuration.getExecutions()).isEqualTo(executions);
    }

    @Test
    void shouldFailWhenPassingEmtptyExecution() {

        // given
        Execution[] executors = {};

        // then
        assertThatThrownBy(() -> new Configuration(executors))
                .isInstanceOf(IntegrityException.class)
                .hasMessage("Executions must not be empty!");
    }
}
