package pl.szczepanik.silencio.decisions;

import java.util.regex.Pattern;

import pl.szczepanik.silencio.api.Decision;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Implementation of {@link Decision} that returns positive decision if passed key and value matches to declared
 * pattern. If values passed to decision method contains <code>null<code> key or value then relevant pattern will not be
 * checked.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class MatcherDecision implements Decision {

    private static final String ACCEPT_ALL_PATTERN = ".*";

    private final Pattern keyPattern;
    private final Pattern valuePattern;

    public MatcherDecision(String keyPattern, String valuePattern) {
        this.keyPattern = createPattern(keyPattern);
        this.valuePattern = createPattern(valuePattern);
    }

    private Pattern createPattern(String pattern) {
        String newPattern = pattern;
        if (newPattern == null) {
            newPattern = ACCEPT_ALL_PATTERN;
        }
        return Pattern.compile(newPattern);
    }

    public MatcherDecision(String valuePattern) {
        this(null, valuePattern);
    }

    @Override
    public boolean decide(Key key, Value value) {
        boolean result = true;
        if (value.getValue() != null) {
            result &= valuePattern.matcher(value.getValue().toString()).matches();
        }
        if (key.getKey() != null) {
            result &= keyPattern.matcher(key.getKey()).matches();
        }

        return result;
    }

}
