/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import org.apache.log4j.Logger;
import org.trommel.trommel.Field;
import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;


/**
 *	For the Map phase find the minimum value in the data set for numeric {@link org.trommel.trommel.Field} instances.
 *	The MinMapper class ignores categorical Fields (i.e., they are not written as Map phase output).
 */
public class MinMapper extends Function 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Min";

	
	//
	//	Getters/setters
	//
	
	/**
	 * Return the name of the Min function.
	 * 
	 * @return The string value of "Min".
	 */
	public String getHandlerName() 
	{
		return FUNCTION_NAME;
	}


	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the MinMapper
	 * to log to the Hadoop Task syslog file.
	 * @param fields The numeric {@link org.trommel.trommel.Field} instances for which Min values will be calculated.
	 * @throws IllegalArgumentException Where logger is null or fields array is null or empty. Also thrown if any of the Fields
	 * are null or empty. All-whitespace strings are considered empty.
	 */
	public MinMapper(Logger logger, Field [] fields)
		throws IllegalArgumentException
	{
		super(logger, fields);
	}
	
	
	//
	//	Public methods
	//
	
	/**
	 * Process a single {@link MapRecord} read from the data set for the Map phase of finding minimum
	 * numeric values.
	 * 
	 * @param record The MapRecord containing the data read from data set and any results of Map phase 
	 * minimum value processing.
	 * @throws IllegalArgumentException Where bubbled up from passed-in MapRecord.
	 */
	public void handleMapRecord(MapRecord record) 
	{
		// Only process the fields specified for the function
		for (Field field : fields)
		{
			// Ignore categorical data
			if (field.isNumeric())
			{
				// Map phase is pretty easy, just spit out the value for the field
				record.addFunctionOutput(field.getName(), new FunctionOutput(FUNCTION_NAME, record.getFieldValue(field.getName())));

			}
		}
	}
}
