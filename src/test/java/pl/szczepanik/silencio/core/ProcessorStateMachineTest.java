package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThatNoException;

import org.junit.Test;

import pl.szczepanik.silencio.GenericTest;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class ProcessorStateMachineTest extends GenericTest {

    @Test
    public void shouldNotAllowToValidateProcessInCreatedState() {

        // given
        ProcessorStateMachine machine = new ProcessorStateMachine();

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage("This operation is not allowed for this state: CREATED");
        machine.validateProcess();
    }

    @Test
    public void shouldAllowForValidateProcess() {

        // given
        ProcessorStateMachine machine = new ProcessorStateMachine();

        // when
        machine.moveToLoaded();

        // then
        assertThatNoException().isThrownBy(() -> machine.validateProcess());
    }

    @Test
    public void shouldNotAllowToValidateWriteInCreatedState() {

        // given
        ProcessorStateMachine machine = new ProcessorStateMachine();

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage("This operation is not allowed for this state: CREATED");
        machine.validateWrite();
    }

    @Test
    public void shouldAllowForValidateWrite() {

        // given
        ProcessorStateMachine machine = new ProcessorStateMachine();
        machine.moveToLoaded();
        machine.moveToProcessed();

        // when & then
        assertThatNoException().isThrownBy(() -> machine.validateWrite());
    }

    @Test
    public void shouldAllowForReLoad() {

        // given
        ProcessorStateMachine machine = new ProcessorStateMachine();
        machine.moveToLoaded();
        machine.moveToProcessed();

        // when & then
        assertThatNoException().isThrownBy(() -> machine.moveToLoaded());
    }
}
