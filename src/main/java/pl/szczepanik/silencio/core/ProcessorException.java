package pl.szczepanik.silencio.core;

/**
 * Exception that is thrown when any phase of processing fails.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ProcessorException extends RuntimeException {

    public ProcessorException(Throwable cause) {
        super(cause);
    }

    public ProcessorException(String message) {
        super(message);
    }
}
