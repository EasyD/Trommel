/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel.utilities;

/**
 *	Utility class hosting a collection of handy methods for working/validating string objects.
 */
public final class StringUtilities 
{	
	//
	// Public methods
	//
	
	/**
	 * @param string The object to evaluate.
	 * @return True if string is null or empty. Strings consisting only of whitespace are treated as empty.
	 */
	public static boolean isNullOrEmpty(String string)
	{
		return string == null || string.trim().length() == 0;
	}
}
