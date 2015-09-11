package pl.szczepanik.silencio.diagnostics;

import java.io.StringReader;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.stubs.StubAbstractProcessor;
import pl.szczepanik.silencio.stubs.StubConverter;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ProcessorSmokeCheckerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
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
        thrown.expect(ProcessorException.class);
        thrown.expectMessage(errorMessage);
        ProcessorSmokeChecker.validateProcessor(processor, new StringReader(""));
    }

}
