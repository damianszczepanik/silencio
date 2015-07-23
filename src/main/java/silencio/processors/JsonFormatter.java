package silencio.processors;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 * 
 */
public class JsonFormatter extends AbstractFormatter {

    private final String separator;

    public JsonFormatter(String separator) {
        this.separator = separator;
    }

    public JsonFormatter() {
        this("  ");
    }

    public DataType getDataType() {
        return JSON_DATA_TYPE;
    }

    @Override
    public void process(Map content, Writer writter) {
        try {
            writter.append(content.toString());
        } catch (IOException e) {
            throw new ProcessorException(e);
        }

    }

}
