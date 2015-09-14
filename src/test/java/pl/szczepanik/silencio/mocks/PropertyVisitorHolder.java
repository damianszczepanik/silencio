package pl.szczepanik.silencio.mocks;

import java.util.Properties;

import pl.szczepanik.silencio.processors.visitors.PropertiesVisitor;

/**
 * Counts how many {@link #process(Properties)} method has been invoked.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class PropertyVisitorHolder extends PropertiesVisitor {

    private Properties properties;

    public PropertyVisitorHolder() {
        super(null);
    }

    @Override
    public void process(Properties properties) {
        this.properties = properties;
    }

    public Properties getProperties() {
        return properties;
    }
}
