package pl.szczepanik.silencio.stubs;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.lang3.ArrayUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Stub of @link {@link pl.szczepanik.silencio.core.AbstractProcessor.ObjectMapper} that has only stub methods.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class StubObjectMapper extends ObjectMapper {

    private Iterator<String> jsons;

    private IOException exception;

    public StubObjectMapper(IOException exception) {
        this.exception = exception;
    }

    public StubObjectMapper(String... jsons) {
        if (ArrayUtils.isEmpty(jsons)) {
            this.jsons = Arrays.asList(jsons).iterator();
        }
    }

    public <T> T readValue(String content, Class<T> valueType) throws IOException {
        if (exception != null) {
            throw exception;
        } else if (jsons == null) {
            return null;
        } else {
            return super.readValue(jsons.next(), valueType);
        }
    }
}
