package pl.szczepanik.silencio.api;

/**
 * Contract for all generators, converters, obfuscators, etc.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public interface Generator {

	/**
	 * Gets name of the generator.
	 */
	public String getType();

	/**
	 * Takes another generator that will be processed before current one is processed.
	 * @param generator generator to wrap
	 * @return current generator
	 */
	public Generator decorate(Generator generator);
	
	/**
	 * Executes current generator.
	 * @return result of the generator
	 */
	public Result process();
}
