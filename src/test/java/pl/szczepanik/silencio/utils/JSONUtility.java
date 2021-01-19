package pl.szczepanik.silencio.utils;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class JSONUtility {

    public static boolean matchesJsonToPattern(String jsonContent, String patternFile) {
        String pattern = ResourceLoader.loadAsString(patternFile);
        return jsonContent.matches(pattern);
    }
}
