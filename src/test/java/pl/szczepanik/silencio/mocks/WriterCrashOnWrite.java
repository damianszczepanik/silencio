package pl.szczepanik.silencio.mocks;

import java.io.IOException;
import java.io.Writer;

/**
 * Writer that throws exception when {@link #write(char[], int, int)}} method is invoked.
 *
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class WriterCrashOnWrite extends Writer {

    private final String errorMessage;

    public WriterCrashOnWrite(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        throw new IOException(errorMessage);
    }

    @Override
    public void flush() throws IOException {
        // This method is intentionally empty, it is overridden only to meet Writer class
    }

    @Override
    public void close() throws IOException {
        // This method is intentionally empty, it is overridden only to meet Writer class
    }
}
