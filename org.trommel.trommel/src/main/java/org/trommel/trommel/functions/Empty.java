/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.utilities.StringUtilities;

/**
 *	Find the count of empty values for a {@link org.trommel.trommel.Field}.
 */
public class Empty extends Function 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Empty";

	
	//
	//	Private members
	//
	private int emptyCount = 0;
	
	
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
		
	/**
	 * Return the current count of empty values.
	 * 
	 * @return The current empty count as a {@link java.lang.String}.
	 */
	public String getReduceResult()
	{
		return Integer.toString(emptyCount);
	}

	
	//
	//	Constructors
	//
	
	/**
	 * @param fields The {@link org.trommel.trommel.Field} names for which empty values will be counted.
	 * @throws IllegalArgumentException Where fields array is null or empty. Also thrown if any of the fields
	 * are null or empty. All-whitespace strings are considered empty.
	 */
	public Empty(String[] fields)
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
		for (String field : fieldNames)
		{
			// Only write mapper output for empty field values
			if(StringUtilities.isNullOrEmpty(record.getFieldValue(field)))
			{
				record.addFunctionOutput(field, new FunctionOutput(FUNCTION_NAME, "1"));
			}
		}
	}

	/**
	 * Process a single record read from the post-Map phase data for the Reduce phase of processing.
	 * 
	 * @param record {@link java.util.HashMap} of parsed data in the form of <"FunctionName", "OutputValue">.
	 */
	public void handleReduceRecord(HashMap<String, String> record) 
	{
		if (record.containsKey(FUNCTION_NAME))
		{	
			// Reduce phase processing is easy, increment the count
			++emptyCount;
		}
	}

}
