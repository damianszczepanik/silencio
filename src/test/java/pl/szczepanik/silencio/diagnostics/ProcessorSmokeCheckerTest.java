package pl.szczepanik.silencio.diagnostics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.io.StringReader;

import org.junit.Test;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.stubs.StubAbstractProcessor;
import pl.szczepanik.silencio.stubs.StubConverter;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ProcessorSmokeCheckerTest {
    
    @Test
    public void shouldReportFailureWhenProcessorFails() {
        
        final String errorMessage = "Ups, I did it again!";

        // given
        StubAbstractProcessor processor = new StubAbstractProcessor(Format.JSON,
                new Converter[] { new StubConverter() }) {
            @Override
            public void realProcess() {
                throw new RuntimeException(errorMessage);
            }
        };

        // then
        try {
            ProcessorSmokeChecker.validateProcessor(processor, new StringReader(""));
            fail("Excepted exception");
        } catch (ProcessorException e) {
            assertThat(e.getCause().getMessage()).isEqualTo(errorMessage);
        }
    }

}
