package pl.szczepanik.silencio.api;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

import pl.szczepanik.silencio.core.IntegrityException;

/**
 * Type of the file content.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class Format {

    /** Predefined format for property files. */
    public static final Format PROPERTIES = new Format("PROPERTIES");

    /** Predefined format for JSON files. */
    public static final Format JSON = new Format("JSON");

    /** Predefined format for XML files. */
    public static final Format XML = new Format("XML");

    private final String name;

    /**
     * Creates new format with given name.
     * 
     * @param name
     *            name of the format
     */
    public Format(String name) {
        validateName(name);

        this.name = name;
    }

    /**
     * Gets name of the format. Usually file format such as XML or JSON.
     * 
     * @return name of the format
     */
    public String getName() {
        return name;
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Two formats are equal if they have the same name.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }

        Format ref = (Format) obj;
        return new EqualsBuilder()
                .append(name, ref.name)
                .isEquals();
    }

    private void validateName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IntegrityException("Name of the format must not be empty!");
        }
    }

    @Override
    public String toString() {
        return String.format("Format [name='%s']", name);
    }
}
