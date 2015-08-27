package pl.szczepanik.silencio;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.io.IOUtils;

import pl.szczepanik.silencio.core.ProcessorException;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class TestUtils {

    public static Reader loadJsonAsReader(String fileName) {
        return new InputStreamReader(loadJsonAsStream(fileName));
    }

    public static String loadJSonAsString(String fileName) {
        try {
            return IOUtils.toString(loadJsonAsStream(fileName), "UTF-8");
        } catch (IOException e) {
            throw new ProcessorException(e);
        }
    }

    private static InputStream loadJsonAsStream(String fileName) {
        try {
            return TestUtils.class.getClassLoader().getResource("json/" + fileName).openStream();
        } catch (IOException e) {
            throw new ProcessorException(e);
        }
    }
}
