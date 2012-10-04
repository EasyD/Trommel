/*
 * 	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import org.apache.log4j.Logger;
import org.trommel.trommel.MapRecordHandler;
import org.trommel.trommel.Field;


/**
 *	Abstract base class for Trommel functions.
 */
public abstract class Function extends MapRecordHandler 
{
	//
	//	Private members
	//
	protected Field[] fields;
	
	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the Function to log to the 
	 * Hadoop Task syslog file.
	 * @param fields The {@link org.trommel.trommel.Field} instances that will be processed by the Function.
	 * @throws IllegalArgumentException Where logger is null or fields array is null or empty. Also thrown if an array element
	 * is null.
	 */
	public Function(Logger logger, Field[] fields)
		throws IllegalArgumentException
	{
		super(logger);
		
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
