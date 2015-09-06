package pl.szczepanik.silencio.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.io.IOUtils;

import pl.szczepanik.silencio.core.ProcessorException;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ResourceLoader {

    private static final String JSON_DIR = "json/";
    private static final String XML_DIR = "xml/";

    public static Reader loadJsonAsReader(String fileName) {
        return new InputStreamReader(loadFileAsStream(JSON_DIR + fileName));
    }

    public static String loadJsonAsString(String fileName) {
        return loadFileAsString(JSON_DIR + fileName);
    }

    public static Reader loadXmlAsReader(String fileName) {
        return new InputStreamReader(loadFileAsStream(XML_DIR + fileName));
    }

    public static String loadXmlAsString(String fileName) {
        return loadFileAsString(XML_DIR + fileName);
    }

    private static String loadFileAsString(String fileName) {
        try {
            return IOUtils.toString(loadFileAsStream(fileName), "UTF-8");
        } catch (IOException e) {
            throw new ProcessorException(e);
        }
    }

    private static InputStream loadFileAsStream(String fileName) {
        try {
            return ResourceLoader.class.getClassLoader().getResource(fileName).openStream();
        } catch (IOException e) {
            throw new ProcessorException(e);
        }
    }
}
