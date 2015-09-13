package pl.szczepanik.silencio.diagnostics;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.mocks.AbstractProcessorCrashOnRealProcess;
import pl.szczepanik.silencio.stubs.StubConverter;
import pl.szczepanik.silencio.stubs.StubProcessor;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ProcessorSmokeCheckerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void shouldPassWithStubProcessor() {

        // given
        StubProcessor processor = new StubProcessor(Format.JSON, new Converter[] { new StubConverter() });

        // then
        new ProcessorSmokeChecker(processor).validateWithAllConverters("");
    }

    @Test
    public void shouldPassWithSetProcessor() {

        // given
        StubProcessor processor = new StubProcessor(Format.JSON, new Converter[] { new StubConverter() });

        // then
        new ProcessorSmokeChecker(processor).validateWithSetsOfConverters("");
    }

    @Test
    public void shouldThrowProcessorExceptionWhenValidationFails() {
        
        final String errorMessage = "Ups, I did it again!";

        // given
        AbstractProcessorCrashOnRealProcess processor = new AbstractProcessorCrashOnRealProcess(Format.JSON,
                new Converter[] { new StubConverter() }, errorMessage);

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage(errorMessage);
        new ProcessorSmokeChecker(processor).validateWithAllConverters("");
    }

}
