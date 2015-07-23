package silencio.processors;

/**
 * Allows to identify type of the content. Used to validate if two processors
 * with different data type are not used together.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 * 
 */
public interface DataType {

    /**
     * Name of the processor which should be unique to identify the type.
     * 
     * @return name of the processor
     */
    public String getTypeName();
}
