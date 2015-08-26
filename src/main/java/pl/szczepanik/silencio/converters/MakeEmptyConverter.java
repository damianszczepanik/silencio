package pl.szczepanik.silencio.converters;

import pl.szczepanik.silencio.api.Strategy;
import pl.szczepanik.silencio.core.Element;

/**
 * Provides strategy for the JSON format that converts each passed value into empty string ("").
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class MakeEmptyConverter implements Strategy {

    @Override
    public Element convert(Element value) {
        return new Element("", "");
    }

    @Override
    public void init() {
    }

}
