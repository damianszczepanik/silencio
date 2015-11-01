package pl.szczepanik.silencio;

import java.io.Reader;
import java.io.Writer;

import org.apache.commons.io.IOUtils;
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
    public void closeStreams() {
        IOUtils.closeQuietly(input);
        IOUtils.closeQuietly(output);
    }
}
