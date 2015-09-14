package pl.szczepanik.silencio.core;

/**
 * Value that should be converted by {@link Converter}. This class wraps passed object but it allows also to store
 * metadata or change behavior when requirements changes.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class Value {

    private final Object value;

    public Value(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
