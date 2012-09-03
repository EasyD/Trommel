/*
 * 	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import org.trommel.trommel.MapRecordHandler;
import org.trommel.trommel.Field;

/**
 *	Abstract base class for all Trommel functions.
 */
public abstract class Function implements MapRecordHandler 
{
	//
	//	Private members
	//
	protected Field[] fields;
	
	
	//
	//	Constructors
	//
	
	/**
	 * @param fields The {@link org.trommel.trommel.Field} instances that will be processed by the Function.
	 * @throws IllegalArgumentException Where fields array is null or empty. Also thrown if an array element
	 * is null.
	 */
	public Function(Field[] fields)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if (fields == null)
		{
			throw new IllegalArgumentException("Fields array cannot be null.");
		}
		
		if (fields.length == 0)
		{
			throw new IllegalArgumentException("Fields array cannot be empty.");
		}
		
		for (Field field : fields)
		{
			if (field == null)
			{
				throw new IllegalArgumentException("Field array element cannot be null or empty.");
			}
		}
		
		this.fields = fields;
	}
}
