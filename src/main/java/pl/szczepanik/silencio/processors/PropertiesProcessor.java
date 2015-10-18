package pl.szczepanik.silencio.processors;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.core.AbstractProcessor;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.processors.visitors.PropertiesVisitor;

/**
 * Provides processor that supports Properties format.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class PropertiesProcessor extends AbstractProcessor {

    private final Properties properties = new Properties();

    private final PropertiesVisitor visitor = new PropertiesVisitor();

    /** Creates new processor for properties file. */
    public PropertiesProcessor() {
        super(Format.PROPERTIES);
    }

    @Override
    public void realLoad(Reader reader) {
        properties.clear();
        try {
            properties.load(reader);
        } catch (IOException | IllegalArgumentException e) {
            throw new ProcessorException(e);
        }
    }

    @Override
    public void realProcess() {
        visitor.setConfiguration(configuration);
        visitor.process(properties);
    }

    @Override
    public void realWrite(Writer writer) {
        try {
            // stores in no deterministic order
            properties.store(writer, null);
        } catch (IOException e) {
            throw new ProcessorException(e);
        }
    }

}
