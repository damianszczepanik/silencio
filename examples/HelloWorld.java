import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import silencio.Builder;
import silencio.processors.JsonFormatter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HelloWorld {

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

        // load json file
        InputStream resource = HelloWorld.class.getResourceAsStream("/json/truck.json");
        ObjectMapper mapper = new ObjectMapper();
        Map content = mapper.readValue(resource, Map.class);

        // setup builder
        Builder builder = new Builder(new JsonFormatter());
        Writer writter = new StringWriter();

        // process conversion
        builder.parse(content, writter);
        System.out.println(writter);
    }
}
