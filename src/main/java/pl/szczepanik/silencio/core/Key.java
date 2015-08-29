package pl.szczepanik.silencio.core;

/**
 * Key for the {@link Value}. This type is immutable. This class wraps passed key but it allows also to store metadata
 * or change behavior when requirements changes.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class Key {

    private final String key;

    public Key(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
