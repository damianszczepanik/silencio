package pl.szczepanik.silencio.stubs;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Decision;
import pl.szczepanik.silencio.core.ExecutionConfig;
import pl.szczepanik.silencio.decisions.PositiveDecision;

/**
 * Stub of @link {@link ExecutionConfig} that has only stub methods.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class StubExecutionConfig extends ExecutionConfig {

    public StubExecutionConfig(Decision decision, Converter converter) {
        super(decision, new Converter[] { converter });
    }

    public StubExecutionConfig(Converter converter) {
        this(new PositiveDecision(), converter);
    }

    public StubExecutionConfig(Decision decision, Converter[] converter) {
        super(decision, converter);
    }

    public static ExecutionConfig[] asList(Converter[] converters) {
        return new ExecutionConfig[] { new ExecutionConfig(new PositiveDecision(), converters) };
    }

    public static ExecutionConfig[] asList(Converter converter) {
        return new ExecutionConfig[] { new StubExecutionConfig(converter) };
    }

    public static ExecutionConfig[] asList(Decision decision, Converter[] converter) {
        return new ExecutionConfig[] { new ExecutionConfig(decision, converter) };
    }

    public static ExecutionConfig[] asList(Decision[] decision, Converter[] converter) {
        return new ExecutionConfig[] { new ExecutionConfig(decision, converter) };
    }
}
