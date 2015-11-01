package pl.szczepanik.silencio.decisions;

import java.util.regex.Pattern;

import pl.szczepanik.silencio.api.Decision;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Implementation of {@link Decision} that returns positive decision if passed key and value matches (WHOLE string) to
 * declared pattern. If value or key pattern is <code>null</code> then relevant pattern will not be checked assuming
 * that pattern matches.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class MatcherDecision implements Decision {

    private final Pattern keyPattern;
    private final Pattern valuePattern;

    /**
     * Creates new decision with key and value patterns.
     * 
     * @param keyPattern
     *            pattern for key
     * @param valuePattern
     *            pattern for value
     */
    public MatcherDecision(String keyPattern, String valuePattern) {
        this.keyPattern = createPattern(keyPattern);
        this.valuePattern = createPattern(valuePattern);
    }

    /**
     * Creates new decision with key and value patterns.
     * 
     * @param valuePattern
     *            pattern for value
     */
    public MatcherDecision(String valuePattern) {
        this(null, valuePattern);
    }

    private Pattern createPattern(String pattern) {
        if (pattern == null) {
            return null;
        }
        return Pattern.compile(pattern);
    }

    @Override
    public boolean decide(Key key, Value value) {
        boolean shouldConvert = true;

        if (key.getKey() != null && keyPattern != null) {
            shouldConvert &= keyPattern.matcher(key.getKey()).matches();
        }

        if (value.getValue() != null && valuePattern != null) {
            shouldConvert &= valuePattern.matcher(value.getValue().toString()).matches();
        }

        return shouldConvert;
    }

}
