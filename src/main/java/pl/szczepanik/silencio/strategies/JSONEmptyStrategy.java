package pl.szczepanik.silencio.strategies;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.core.AbstractStrategy;
import pl.szczepanik.silencio.core.Element;

/**
 * Provides strategy for the JSON format that converts each passed value into empty string ("").
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class JSONEmptyStrategy extends AbstractStrategy {

    public JSONEmptyStrategy(Format format) {
        super(format);
    }

    @Override
    public Element convert(Element value) {
        return new Element("", "");
    }

    @Override
    public void reset() {
    }

}
