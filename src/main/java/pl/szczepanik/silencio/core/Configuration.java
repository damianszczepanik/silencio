package pl.szczepanik.silencio.core;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Keeps information about whole conversion process.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class Configuration {

    private final Execution[] executions;

    public Configuration(Execution[] executions) {
        validateConfiguration(executions);

        this.executions = executions;
    }

    public Configuration(Execution execution) {
        this(new Execution[] { execution });
    }

    public Execution[] getExecutions() {
        return executions;
    }

    private void validateConfiguration(Execution[] executions) {
        if (ArrayUtils.isEmpty(executions)) {
            throw new IntegrityException("Executions must not be empty!");
        }
    }
}
