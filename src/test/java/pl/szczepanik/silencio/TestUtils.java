package pl.szczepanik.silencio;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import pl.szczepanik.silencio.core.ProcessorException;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class TestUtils {

    public static Reader loadJson(String fileName) {
        try {
            return new InputStreamReader(TestUtils.class.getClassLoader().getResource("json/" + fileName).openStream());
        } catch (IOException e) {
            throw new ProcessorException(e);
        }

    }
}
