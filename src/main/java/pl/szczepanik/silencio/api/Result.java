package pl.szczepanik.silencio.api;

import java.io.Reader;

/**
 * Delivers content produced by {@link Generator generators}.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public interface Result {

	/**
	 * Returns content produced by generators.
	 */
	Reader getContent();
}
