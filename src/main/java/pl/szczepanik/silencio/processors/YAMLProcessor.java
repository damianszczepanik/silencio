package pl.szczepanik.silencio.processors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.processors.visitors.YAMLVisitor;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

/**
 * Provides processor that supports YAML format.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class YAMLProcessor extends AbstractProcessor {

    private final ObjectMapper mapper;

    private Map<String, Object> yamlStructure;

    private final YAMLVisitor visitor = new YAMLVisitor();

    /** Creates new processor for YAML file. */
    public YAMLProcessor() {
        super(Format.YAML);

        mapper = new ObjectMapper(new YAMLFactory());
        // this prevents printing eg. 2.20 as 2.2
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
    }

    @Override
    public void realLoad(Reader reader) {
        try {
            yamlStructure = mapper.readValue(reader, new TypeReference<Map<String, Object>>() { });
        } catch (IOException e) {
            throw new ProcessorException(e);
        }
        // when input file is empty
        if (yamlStructure == null) {
            yamlStructure = Collections.emptyMap();
        }
    }

    @Override
    public void realProcess() {
        visitor.setConfiguration(configuration);
        visitor.processYaml(yamlStructure);
    }

    @Override
    public void realWrite(Writer writer) {
        ObjectWriter objectWriter = mapper.writer().with(SerializationFeature.INDENT_OUTPUT);
        try {
            objectWriter.writeValue(writer, yamlStructure);
        } catch (IOException e) {
            throw new ProcessorException(e);
        }

    }

}
