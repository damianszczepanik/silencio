package pl.szczepanik.silencio.processors;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.core.AbstractProcessor;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.core.Value;

/**
 * Provides processor that supports Properties format.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class PropertiesProcessor extends AbstractProcessor {

    private final Properties properties = new Properties();

    public PropertiesProcessor(Converter[] converters) {
        super(Format.JSON, converters);
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
        initConverties();
        for (Object key : properties.keySet()) {
            Value newValue = processValue(key.toString(), properties.getProperty(key.toString()));
            properties.put(key, newValue == null ? null : newValue.getValue().toString());
        }
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
