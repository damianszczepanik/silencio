package pl.szczepanik.silencio.core;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.converters.BlankConverter;
import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.diagnostics.ProcessorSmokeChecker;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class BuilderTest_buildTest extends GenericTest {

    public Format format;

    public static Collection<Format> data() {
        return ProcessorSmokeChecker.FORMATS;
    }

    @MethodSource("data")
    @ParameterizedTest(name = "\"{0}\"")
    public void buildSupportsAllFormats(Format format) {

        initDataWithNameTest(format);
        // given
        Builder builder = new Builder(format);
        builder.with(new PositiveDecision(), new BlankConverter());

        // when
        Processor processor = builder.build();

        // then
        assertThat(processor.getFormat()).isEqualTo(format);
        // just make sure that processor is created
        assertThat(processor).isNotNull();
    }

    private void initDataWithNameTest(Format format) {
        this.format = format;
    }
}
