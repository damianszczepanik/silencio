package pl.szczepanik.silencio.integration;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.diagnostics.ProcessorSmokeChecker;
import pl.szczepanik.silencio.processors.JSONProcessor;
import pl.szczepanik.silencio.processors.PropertiesProcessor;
import pl.szczepanik.silencio.processors.XMLProcessor;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
@RunWith(Parameterized.class)
public class SuvIntegrationTest extends GenericTest {

    @Parameter(value = 0)
    public Processor processor;

    @Parameter(value = 1)
    public String suvInput;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new XMLProcessor(), ResourceLoader.loadAsString("suv.xml")},
                {new JSONProcessor(), ResourceLoader.loadAsString("suv.json")},
                {new PropertiesProcessor(), ResourceLoader.loadAsString("suv.properties")},
        });
    }

    @Test
    public void shouldNotCrashOnDiagnosticTests() {

        // given
        ProcessorSmokeChecker checker = new ProcessorSmokeChecker(processor);

        // when
        checker.validateWithAllCombinations(suvInput);

        // then
        // no exception
    }
}
