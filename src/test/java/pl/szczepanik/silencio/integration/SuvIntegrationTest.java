package pl.szczepanik.silencio.integration;

import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
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
class SuvIntegrationTest extends GenericTest {

    public Processor processor;

    public String suvInput;

    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { new XMLProcessor(), ResourceLoader.loadAsString("suv.xml") },
                { new JSONProcessor(), ResourceLoader.loadAsString("suv.json") },
                { new PropertiesProcessor(), ResourceLoader.loadAsString("suv.properties") },
        });
    }

    @MethodSource("data")
    @ParameterizedTest(name = "\"{0}\" with \"{1}\"")
    void shouldNotCrashOnDiagnosticTests(Processor processor, String suvInput) {

        initDataWithNameTest(processor, suvInput);

        // given
        ProcessorSmokeChecker checker = new ProcessorSmokeChecker(processor);

        // when & then
        assertThatNoException().isThrownBy(() -> checker.validateWithAllCombinations(suvInput));
    }

    private void initDataWithNameTest(Processor processor, String suvInput) {
        this.processor = processor;
        this.suvInput = suvInput;
    }
}
