package pl.szczepanik.silencio.mocks;

import java.util.Properties;

import pl.szczepanik.silencio.processors.visitors.PropertiesVisitor;

/**
 * Counts how many {{@link #} {@link #process(Properties)} method has been invoked.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class PropertyVisitorHolder extends PropertiesVisitor {

    private Properties properties;

    @Override
    public void process(Properties properties) {
        this.properties = properties;
        super.process(properties);
    }

    public Properties getProperties() {
        return properties;
    }
}
