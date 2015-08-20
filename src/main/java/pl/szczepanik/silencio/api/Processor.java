package pl.szczepanik.silencio.api;

/**
 * Contract for processor that converts content.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public interface Processor {

	/**
	 * Gets format of the processor.
	 */
	public Format getFormat();

	/**
	 * Name of the processor.
	 * @return name of the processor.
	 */
	public String getName();

    /**
	 * Takes another processor that will be processed before current one is processed.
	 * @param processor processor to wrap
	 * @return current processor
	 */
	public Processor decorate(Processor processor);
	
	/**
	 * Executes current processor.
	 * @return result of the processor
	 */
	public Product process();
}
