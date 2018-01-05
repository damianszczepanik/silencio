package pl.szczepanik.silencio.core;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.converters.BlankConverter;
import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.diagnostics.ProcessorSmokeChecker;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
@RunWith(Parameterized.class)
public class BuilderTest_buildTest extends GenericTest {

    @Parameter(value = 0)
    public Format format;

    @Parameterized.Parameters
    public static Collection<Format> formats() {
        return ProcessorSmokeChecker.FORMATS;
    }

    @Test
    public void buildSupportsAllFormats() {

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
}
