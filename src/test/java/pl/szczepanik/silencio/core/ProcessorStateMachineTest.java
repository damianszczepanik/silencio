package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import pl.szczepanik.silencio.GenericTest;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class ProcessorStateMachineTest extends GenericTest {

    @Test
    void shouldNotAllowToValidateProcessInCreatedState() {

        // given
        ProcessorStateMachine machine = new ProcessorStateMachine();

        // then
        assertThatThrownBy(() -> machine.validateProcess())
                .isInstanceOf(ProcessorException.class)
                .hasMessage("This operation is not allowed for this state: CREATED");
    }

    @Test
    void shouldAllowForValidateProcess() {

        // given
        ProcessorStateMachine machine = new ProcessorStateMachine();

        // when
        machine.moveToLoaded();

        // then
        assertThatNoException().isThrownBy(() -> machine.validateProcess());
    }

    @Test
    void shouldNotAllowToValidateWriteInCreatedState() {

        // given
        ProcessorStateMachine machine = new ProcessorStateMachine();

        // then
        assertThatThrownBy(() -> machine.validateWrite())
                .isInstanceOf(ProcessorException.class)
                .hasMessage("This operation is not allowed for this state: CREATED");
    }

    @Test
    void shouldAllowForValidateWrite() {

        // given
        ProcessorStateMachine machine = new ProcessorStateMachine();
        machine.moveToLoaded();
        machine.moveToProcessed();

        // when & then
        assertThatNoException().isThrownBy(() -> machine.validateWrite());
    }

    @Test
    void shouldAllowForReLoad() {

        // given
        ProcessorStateMachine machine = new ProcessorStateMachine();
        machine.moveToLoaded();
        machine.moveToProcessed();

        // when & then
        assertThatNoException().isThrownBy(() -> machine.moveToLoaded());
    }
}
