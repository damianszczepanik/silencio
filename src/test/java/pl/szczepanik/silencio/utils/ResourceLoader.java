package pl.szczepanik.silencio.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.core.ProcessorException;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class ResourceLoader {

    private static final String JSON_DIR = "json/";
    private static final String PROPERTIES_DIR = "properties/";
    private static final String XML_DIR = "xml/";
    private static final String YAML_DIR = "yaml/";

    public static Reader loadAsReader(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        if (extension.equalsIgnoreCase(Format.JSON.getName())) {
            return new InputStreamReader(loadFileAsStream(JSON_DIR + fileName));
        } else if (extension.equalsIgnoreCase(Format.PROPERTIES.getName())) {
            return new InputStreamReader(loadFileAsStream(PROPERTIES_DIR + fileName));
        } else if (extension.equalsIgnoreCase(Format.XML.getName())) {
            return new InputStreamReader(loadFileAsStream(XML_DIR + fileName));
        } else if (extension.equalsIgnoreCase(Format.YAML.getName())) {
            return new InputStreamReader(loadFileAsStream(YAML_DIR + fileName));
        } else {
            throw new IllegalArgumentException("Unsupported extension file: " + fileName);
        }
    }

    public static String loadAsString(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        if (extension.equalsIgnoreCase(Format.JSON.getName())) {
            return loadFileAsString(JSON_DIR + fileName);
        } else if (extension.equalsIgnoreCase(Format.PROPERTIES.getName())) {
            return loadFileAsString(PROPERTIES_DIR + fileName);
        } else if (extension.equalsIgnoreCase(Format.XML.getName())) {
            return loadFileAsString(XML_DIR + fileName);
        } else if (extension.equalsIgnoreCase(Format.YAML.getName())) {
            return loadFileAsString(YAML_DIR + fileName);
        } else {
            throw new IllegalArgumentException("Unsupported extension file: " + fileName);
        }
    }

    private static String loadFileAsString(String fileName) {
        try {
            return IOUtils.toString(loadFileAsStream(fileName), StandardCharsets.UTF_8);
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
