package pl.szczepanik.silencio.core;

/**
 * Exception that is thrown when data used for processing is not valid.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class IntegrityException extends RuntimeException {

    public IntegrityException(String message) {
        super(message);
    }
}
