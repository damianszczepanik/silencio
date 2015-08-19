package pl.szczepanik.silencio.api;

import org.apache.commons.lang3.StringUtils;

/**
 * Type of the content that are supported by default.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class Format {

    /** Predefined format for XML files. */
    public static final Format XML = new Format("XML");

    /** Predefined format for JSON files. */
    public static final Format JSON = new Format("JSON");

    private final String name;

    public Format(String name) {
        validateName(name);
        this.name = name;
    }

    /**
     * Gets name of the format. Usually file format such as XML or JSON.
     */
    public String getName() {
        return name;
    }

    private void validateName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Name of the format must not be empty!");
        }
    }
}
