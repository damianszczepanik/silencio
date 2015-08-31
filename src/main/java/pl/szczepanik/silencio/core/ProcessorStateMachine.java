package pl.szczepanik.silencio.core;

/**
 * State machine for processors.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ProcessorStateMachine {

    private enum States {
        /** Represent state where processor has been created. */
        CREATED, /** Represent state where processor has loaded the content. */
        LOADED, /** Represent state where processor has been processed the content. */
        PROCESSED;
    }

    private final static String ERROR_MESSAGE = "This operation is not allowed for this state: ";

    /**
     * Information about current state.
     */
    private States state = States.CREATED;

    /**
     * Checks if given operation is allowed.
     * 
     * @throws {@link
     *             ProcessorException} when operation is not allowed for this processor state
     */
    public void validateProcess() {
        if (state == States.CREATED) {
            throw new ProcessorException(ERROR_MESSAGE + state.name());
        }
    }

    /**
     * Checks if given operation is allowed.
     * 
     * @throws {@link
     *             ProcessorException} when operation is not allowed for this processor state
     */
    public void validateWrite() {
        if (state != States.PROCESSED) {
            throw new ProcessorException(ERROR_MESSAGE + state.name());
        }
    }


    /**
     * Changes state into new one.
     */
    public void moveToLoaded() {
        state = States.LOADED;
    }

    /**
     * Changes state into new one.
     */
    public void moveToProcessed() {
        state = States.PROCESSED;
    }

}
