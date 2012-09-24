/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import org.trommel.trommel.Field;
import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;

/**
 *	For Map phase, find the maximum value in the data set for numeric {@link org.trommel.trommel.Field} instances.
 */
public class MaxMapper extends Function 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Max";


	//
	//	Getters/setters
	//
	
	/**
	 * Return the name of the Max function.
	 * 
	 * @return The string value of "Max".
	 */
	public String getHandlerName()
	{
		return FUNCTION_NAME;
	}

	
	//
	//	Constructors
	//
	
	/**
	 * @param fields The numeric {@link org.trommel.trommel.Field} instances for which Max values will be calculated.
	 * @throws IllegalArgumentException Where fields array is null or empty. Also thrown if any of the fields
	 * are null or empty. All-whitespace strings are considered empty.
	 */
	public MaxMapper(Field[] fields)
		throws IllegalArgumentException
	{
		super(fields);
	}
	
	
	//
	//	Public methods
	//
	
	/**
	 * Process a single {@link MapRecord} read from the data set for the Map phase of finding maximum
	 * numeric values.
	 * 
	 * @param record The MapRecord containing the data read from data set and any results of Map phase 
	 * maximum value processing.
	 * @throws IllegalArgumentException Where bubbled up from passed-in MapRecord.
	 */
	public void handleMapRecord(MapRecord record)
		throws IllegalArgumentException
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
