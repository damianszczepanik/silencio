package pl.szczepanik.silencio.api;

import java.io.Reader;

/**
 * Delivers content produced by {@link Processor processors}.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public interface Product {

	/**
	 * Returns content produced by processors.
	 */
	Reader getContent();
}
