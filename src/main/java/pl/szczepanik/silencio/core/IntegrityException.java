package pl.szczepanik.silencio.core;

/**
 * Exception that is thrown when data used for processing is not valid.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class IntegrityException extends RuntimeException {

    /**
     * Creates new instance from passed message.
     * 
     * @param message
     *            reason of the exception
     */
    public IntegrityException(String message) {
        super(message);
    }
}
