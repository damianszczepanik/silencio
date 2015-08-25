package pl.szczepanik.silencio.core;

/**
 * State machine for processors.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ProcessorState {

    private enum States {
        /** Represent state where processor has been created. */
        CREATED, /** Represent state where processor has loaded the content. */
        LOADED, /** Represent state where processor has been processed the content. */
        PROCESSED;
    }

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
    public void canProcess() {
        if (state == States.CREATED) {
            reportViolation();
        }
    }

    /**
     * Checks if given operation is allowed.
     * 
     * @throws {@link
     *             ProcessorException} when operation is not allowed for this processor state
     */
    public void canWrite() {
        if (state == States.LOADED) {
            reportViolation();
        }
    }

    /**
     * Throws an exception with information that for given state requested operation is not allowed.
     */
    private void reportViolation() {
        throw new ProcessorException("This operation is not allowed for this state: " + state.name());
    }

    /**
     * Changes state into new one.
     */
    public void markAsLoaded() {
        state = States.LOADED;
    }

    /**
     * Changes state into new one.
     */
    public void markAsProcessed() {
        state = States.PROCESSED;
    }

}