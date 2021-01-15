package pl.szczepanik.silencio;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import org.junit.After;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public abstract class GenericTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
