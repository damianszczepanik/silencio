package pl.szczepanik.silencio.core;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Keeps information about whole conversion process.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class Configuration {

    private final Execution[] executions;

    /**
     * Creates configuration with passed list of executions.
     * 
     * @param executions
     *            executions used by this configuration
     */
    public Configuration(Execution[] executions) {
        validateConfiguration(executions);

        this.executions = executions;
    }

    /**
     * Creates configuration with passed execution.
     * 
     * @param execution
     *            execution used by this configuration
     */
    public Configuration(Execution execution) {
        this(new Execution[] { execution });
    }

    /**
     * Gets all configurations.
     * 
     * @return all configurations
     */
    public Execution[] getExecutions() {
        return executions;
    }

    private void validateConfiguration(Execution[] executions) {
        if (ArrayUtils.isEmpty(executions)) {
            throw new IntegrityException("Executions must not be empty!");
        }
    }
}
