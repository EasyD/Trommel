/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import org.trommel.trommel.Field;
import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.utilities.StringUtilities;

/**
 *	Execute the Map phase for the count of empty values for a {@link org.trommel.trommel.Field}.
 */
public class EmptyMapper extends Function
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Empty";


	//
	//	Getters/setters
	//
	
	/**
	 * Return the name of the Empty function.
	 * 
	 * @return The string value of "Empty".
	 */
	public String getHandlerName()
	{
		return FUNCTION_NAME;
	}
		

	//
	//	Constructors
	//
	
	/**
	 * @param fields The {@link org.trommel.trommel.Field} instances for which empty values will be counted.
	 * @throws IllegalArgumentException Where fields array is null or empty. Also thrown if any of the fields
	 * are null or empty. All-whitespace strings are considered empty.
	 */
	public EmptyMapper(Field[] fields)
		throws IllegalArgumentException
	{
		super(fields);
	}

	
	//
	//	Public methods
	//
	
	/**
	 * Process a single {@link MapRecord} read from the data set for the Map phase of finding the
	 * count of empty {@link org.trommel.trommel.Field} values.
	 * 
	 * @param record The MapRecord containing the data read from data set and any results of Map phase 
	 * maximum value processing.
	 * @throws IllegalArgumentException Where bubbled up from passed-in MapRecord.
	 */
	public void handleMapRecord(MapRecord record) 
	{
		// Only process the fields specified for the function
		for (Field field : fields)
		{
			// Only write mapper output for empty field values
			if(StringUtilities.isNullOrEmpty(record.getFieldValue(field.getName())))
			{
				record.addFunctionOutput(field.getName(), new FunctionOutput(FUNCTION_NAME, "1"));
			}
		}
	}
}
