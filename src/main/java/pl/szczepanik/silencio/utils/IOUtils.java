package pl.szczepanik.silencio.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import pl.szczepanik.silencio.core.IntegrityException;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public final class IOUtils {

    /**
     * Loads and returns content accessible via given URL.
     * 
     * @param url
     *            address to load
     * @return loaded content
     * @throws IOException
     *             when input/output operation fails
     */
    public static String urlToString(URL url) {
        try {
            return org.apache.commons.io.IOUtils.toString(url);
        } catch (IOException e) {
            throw new IntegrityException(e.getMessage());
        }
    }

    /**
     * Creates new {@link java.net.URL}.
     * 
     * @param url
     *            address from which the URL should be created
     * @return created URL
     */
    public static URL createURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new IntegrityException(e.getMessage());
        }
    }

}
