/*
 * 	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import org.trommel.trommel.RecordHandler;
import org.trommel.trommel.utilities.StringUtilities;

/**
 *	Abstract base class for all Trommel functions.
 */
public abstract class Function implements RecordHandler 
{
	//
	//	Private members
	//
	protected String[] fieldNames;
	
	
	//
	//	Constructors
	//
	
	/**
	 * @param fieldNames The fields that will be processed by the Function.
	 * @throws IllegalArgumentException Where fields array is null or empty. Also thrown if any of the fields
	 * are null or empty. All-whitespace strings are considered empty.
	 */
	public Function(String[] fieldNames)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if (fieldNames == null)
		{
			throw new IllegalArgumentException("Fields array cannot be null.");
		}
		
		if (fieldNames.length == 0)
		{
			throw new IllegalArgumentException("Fields array cannot be empty.");
		}
		
		for (String field : fieldNames)
		{
			if (StringUtilities.isNullOrEmpty(field))
			{
				throw new IllegalArgumentException("Field name cannot be null or empty.");
			}
		}
		
		this.fieldNames = fieldNames;
	}
}
