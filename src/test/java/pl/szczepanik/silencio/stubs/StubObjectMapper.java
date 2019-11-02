package pl.szczepanik.silencio.stubs;

import java.util.Arrays;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Stub of @link {@link ObjectMapper} that has only stub methods.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class StubObjectMapper extends ObjectMapper {

    private Iterator<String> jsons;

    private JsonMappingException exception;

    public StubObjectMapper(JsonMappingException exception) {
        this.exception = exception;
    }

    public StubObjectMapper(String... jsons) {
        if (ArrayUtils.isEmpty(jsons)) {
            this.jsons = Arrays.asList(jsons).iterator();
        }
    }

    public <T> T readValue(String content, Class<T> valueType)throws JsonProcessingException, JsonMappingException {
        if (exception != null) {
            throw exception;
        } else if (jsons == null) {
            return null;
        } else {
            return super.readValue(jsons.next(), valueType);
        }
    }
}
