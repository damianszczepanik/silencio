package pl.szczepanik.silencio.core;

/**
 * Exception that is thrown when any phase of processing fails.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class ProcessorException extends RuntimeException {

    /**
     * Creates new instance from passed throwable.
     * 
     * @param cause
     *            source of the exception
     */
    public ProcessorException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates new instance from passed message.
     * 
     * @param message
     *            reason of the exception
     */
    public ProcessorException(String message) {
        super(message);
    }
}
