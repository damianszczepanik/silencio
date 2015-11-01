package pl.szczepanik.silencio.mocks;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.core.AbstractProcessor;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.stubs.StubProcessor;

/**
 * Mock for {@link AbstractProcessor} that crashes when {@link AbstractProcessorCrashOnRealProcess#realProcess()} method
 * is invoked.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class AbstractProcessorCrashOnRealProcess extends StubProcessor {

    private final String errorMessage;

    public AbstractProcessorCrashOnRealProcess(Format format, Converter[] converters, String errorMEssage) {
        super(format, converters);
        this.errorMessage = errorMEssage;
    }

    @Override
    public void realProcess() {
        throw new ProcessorException(errorMessage);
    }
}
