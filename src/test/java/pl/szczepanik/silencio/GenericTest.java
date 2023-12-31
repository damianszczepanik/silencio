package pl.szczepanik.silencio;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import org.junit.After;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public abstract class GenericTest {

    protected Writer output;
    protected Reader input;

    @After
    public void closeStreams() throws IOException {
        if (input != null) {
            input.close();
        }
        if (output != null) {
            output.close();
        }
    }
}
