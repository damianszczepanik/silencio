package pl.szczepanik.silencio.converters;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.IntegrityException;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.core.Value;
import pl.szczepanik.silencio.utils.CommonUtility;
import pl.szczepanik.silencio.utils.IOUtility;

/**
 * Converter changes passed values into geo location such as cities, countries or villages.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 * 
 * @see <a href="https://developers.google.com/maps/documentation/geocoding/intro?csw=1">Google documentation</a>
 */
public class GeoLocationConverter implements Converter {

    static final String EXCEPTION_MESSAGE_EMPTY_JSON = "Could not create JSON file for coordinations %s,%s!";

    /** String format used to retrieve JSON file with geo information. */
    private static final String URL_ADDRESS_FORMAT = "http://maps.googleapis.com/maps/api/geocode/json?language=en&components=locality&latlng=%s,%s";

    /** OK status code for response. */
    private static final String OK_STATUS = "OK";

    /** Keeps passed values and corresponding numbers. */
    private final Map<Object, String> values = new HashMap<>();

    /**
     * Keeps words that have been already used. This duplicates {@link WikipediaConverter#values} collection to
     * speed up searching.
     */
    private final Set<String> words = new LinkedHashSet<>();

    private final ObjectMapper mapper = new ObjectMapper();

    private final Random locationGenerator = new Random();

    @Override
    public Value convert(Key key, Value value) {
        String newValue = values.get(value.getValue());
        if (newValue != null) {
            return new Value(newValue);
        } else {
            while (true) {
                waitForNextLocation();
                GeoLocationJSON newLocation = generateNextLocation(generateNextUSLatitude(), generateNextUSLongitude());
                assertGeoIsValid(newLocation);

                // usually there are more but use first item from results
                newValue = newLocation.results[0].formattedAddress;
                // check if this word was not generated for other key
                if (words.contains(newValue)) {
                    continue;
                }

                values.put(value.getValue(), newValue);
                words.add(newValue);

                // return value from first item - could be any but first seems to be less susceptible for duplication
                break;
            }
            return new Value(newValue);
        }
    }

    /**
     * Slows down generation to prevent exceeding limitation.
     * 
     * @see https://developers.google.com/maps/documentation/geocoding/usage-limits
     */
    private void waitForNextLocation() {
        // no more than 10 requests per second
        final int waitTime = 250;
        CommonUtility.saveWait(waitTime);
    }

    private String generateNextUSLatitude() {
        final int south = 32;
        final int north = 50;
        final int precision = 100;
        return (locationGenerator.nextInt(north - south) + south) + "." + locationGenerator.nextInt(precision);
    }

    private String generateNextUSLongitude() {
        final int east = -83;
        final int west = -119;
        final int precision = 100;
        return -(locationGenerator.nextInt(-(west - east)) - east) + "." + locationGenerator.nextInt(precision);
    }

    private GeoLocationJSON generateNextLocation(String latitude, String longitude) {
        try {
            String url = String.format(URL_ADDRESS_FORMAT, latitude, longitude);
            String json = IOUtility.urlToString(new URL(url));
            if (StringUtils.isNotBlank(json)) {
                GeoLocationJSON locationJson = mapper.readValue(json, GeoLocationJSON.class);
                if (locationJson == null) {
                    throw new ProcessorException(String.format(EXCEPTION_MESSAGE_EMPTY_JSON, latitude, longitude));
                } else {
                    return locationJson;
                }
            } else {
                throw new IntegrityException(String.format("URL '%s' returned empty content!", url));
            }
        } catch (IOException e) {
            throw new IntegrityException(e.getMessage());
        }
    }

    @Override
    public void init() {
        values.clear();
        words.clear();
    }

    private void assertGeoIsValid(GeoLocationJSON location) {
        if (!OK_STATUS.equals(location.status)) {
            throw new IntegrityException(
                    String.format("Result status is '%s' but expected '%s'", location.status, OK_STATUS));
        }
    }
}

