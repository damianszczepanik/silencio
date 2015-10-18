package pl.szczepanik.silencio.converters;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.IntegrityException;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;
import pl.szczepanik.silencio.utils.IOUtility;

/**
 * Converter that changes each passed value into random words taken from Wikipedia. Internet access required when using
 * this converter. Because this and other reasons this converter should not be used on production.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class WikipediaConverter implements Converter {

    /**
     * Address that returns random word from Wikipedia. It gets mobile version that is smaller because only subject is
     * used by this converter.
     */
    private static final String URL_ADDRESS = "https://en.m.wikipedia.org/wiki/Special:Random";

    private final URL url = IOUtility.createURL(URL_ADDRESS);

    /**
     * Keeps passed values and corresponding numbers;
     */
    private final Map<Object, String> convertedValues = new HashMap<>();

    /**
     * Keeps words that have been already used. This duplicates {@link WikipediaConverter#convertedValues} collection to
     * speed up searching.
     */
    private final Set<String> words = new LinkedHashSet<>();

    @Override
    public Value convert(Key key, Value value) {

        String newValue = convertedValues.get(value.getValue());
        if (newValue != null) {
            return new Value(newValue);
        } else {
            // this loop theoretically may never ends but Wikipedia has millions of words to ask for
            while (true) {
                newValue = generateNextString();
                // check if this word was not generated for other key
                if (words.contains(newValue)) {
                    continue;
                }

                convertedValues.put(value.getValue(), newValue);
                words.add(newValue);
                return new Value(newValue);
            }
        }
    }

    private String generateNextString() {
        final String startPattern = "<h1 id=\"section_0\">";
        final String endPattern = "</h1>";

        String page = IOUtility.urlToString(url);

        // extract characters between opening and closing h1 tag
        String title = StringUtils.substringBetween(page, startPattern, endPattern);
        if (title == null) {
            throw new IntegrityException("Could not find header pattern for page: " + page);
        }
        // some headlines are wrapped with italics - remove them
        String rawTitle = title.replaceFirst("<i>(.+)</i>", "$1");
        // remove all no digits or letters characters and trailing spaces
        return rawTitle.replaceAll("[^\\d\\w]", " ").trim();
    }

    @Override
    public void init() {
        convertedValues.clear();
        words.clear();
    }

}
