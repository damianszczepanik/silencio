package pl.szczepanik.silencio.stubs;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class WriterStub extends Writer {

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
    }
}
