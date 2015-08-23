package pl.szczepanik.silencio.api;

/**
 * Value that should be converted by {@link Strategy}. This type is immutable. This class wraps passed object but it
 * allows also to store metadata or change behavior when requirements changes.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class Value {

    private final Object valueObject;

    public Value(Object valueObject) {
        this.valueObject = valueObject;
    }

    /**
     * Returns object used as parameter during construction.
     */
    public Object getObject() {
        return valueObject;
    }
}
