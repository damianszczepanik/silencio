package pl.szczepanik.silencio.mocks;

import java.io.IOException;
import java.io.Writer;

import org.omg.CORBA.portable.IndirectionException;

/**
 * Writer that throws {@link IndirectionException} when {@link #write(char[], int, int)}} method is invoked.
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
    }

    @Override
    public void close() throws IOException {
    }
}
