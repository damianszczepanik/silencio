package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.Test;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ProcessorStateMachineTest {

    @Test
    public void shouldNotAllowForValidateProcess() {

        // when
        ProcessorStateMachine machine = new ProcessorStateMachine();

        // then
        try {
            machine.validateProcess();
            fail("expected exception");
        } catch (ProcessorException e) {
            assertThat(e.getMessage()).isEqualTo("This operation is not allowed for this state: CREATED");
        }
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
    public void shouldNotAllowForValidateWrite() {

        // when
        ProcessorStateMachine machine = new ProcessorStateMachine();

        // then
        try {
            machine.validateWrite();
            fail("expected exception");
        } catch (ProcessorException e) {
            assertThat(e.getMessage()).isEqualTo("This operation is not allowed for this state: CREATED");
        }
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
