package pl.szczepanik.silencio.utils;

import static org.assertj.core.api.Assertions.fail;

import java.util.Properties;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class PropertiesUtility {

    /**
     * Assert two properties files according to keys and values are equal (comments are skipped).
     *
     * @param prop1 first properties to compare
     * @param prop2 second properties to compare
     */
    public static void assertEqual(Properties prop1, Properties prop2) {
        boolean keysEqual = prop1.keySet().equals(prop2.keySet());
        if (!keysEqual) {
            fail("Keys form both properties are not equal!");
        }

        for (Object key : prop1.keySet()) {
            String value1 = prop1.getProperty(key.toString());
            String value2 = prop2.getProperty(key.toString());
            if (!value1.equals(value2)) {
                fail(String.format("Value for key '%s' is '%s', expected: '%s'", key, value1, value2));
            }
        }
    }
}
