package pl.szczepanik.silencio.core;

import lombok.Getter;

/**
 * Key for the {@link Value}. This type is immutable. This class wraps passed key but it allows also to store metadata
 * or change behavior when requirements changes.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class Key {

    @Getter
    private final String key;

    public Key(String key) {
        this.key = key;
    }

}
