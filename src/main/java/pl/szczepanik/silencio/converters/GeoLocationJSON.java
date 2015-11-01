package pl.szczepanik.silencio.converters;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contains selected (not all!) attributes from JSON response that contains Google geo information.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class GeoLocationJSON {

    /**
     * Array with information about geo location.
     */
    public final Results[] results = new Results[0];

    /**
     * Details about response status.
     * 
     * @see <a href="https://developers.google.com/maps/documentation/geocoding/intro?csw=1#StatusCodes">Status
     *      codes</a>
     */
    public final String status = null;

    /**
     * Result with geo information. Contains information, some useless for this class.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Results {

        /**
         * Human readable information bout location.
         */
        @JsonProperty("formatted_address")
        public final String formattedAddress = null;
    }

}
