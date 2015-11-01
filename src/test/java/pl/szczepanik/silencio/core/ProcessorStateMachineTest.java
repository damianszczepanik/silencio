package pl.szczepanik.silencio.core;

import org.junit.Test;

import pl.szczepanik.silencio.GenericTest;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class ProcessorStateMachineTest extends GenericTest {

    @Test
    public void shouldNotAllowToValidateProcessInCreatedState() {

        // when
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
        machine.validateProcess();
    }

    @Test
    public void shouldNotAllowToValidateWriteInCreatedState() {

        // when
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

        // when
        machine.moveToLoaded();
        machine.moveToProcessed();

        // then
        machine.validateWrite();
    }

    @Test
    public void shouldAllowForReLoad() {

        // given
        ProcessorStateMachine machine = new ProcessorStateMachine();

        // when
        machine.moveToLoaded();
        machine.moveToProcessed();

        // then
        machine.moveToLoaded();
    }
}
