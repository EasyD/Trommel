/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;

/**
 *	For numeric {@link org.trommel.trommel.Field} instances, find the minimum value in the data set.
 */
public class Min extends Function 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Min";

	//
	//	Private members
	//

	private double minValue = Double.MAX_VALUE;
	
	
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

	/**
	 * Return the current Minimum value.
	 * 
	 * @return The current Minimum value found as a {@link java.lang.String}.
	 */
	public String getReduceResult()
	{
		return Double.toString(minValue);
	}


	
	//
	//	Constructors
	//
	
	/**
	 * @param fields The numeric {@link org.trommel.trommel.Field} instances for which Min values will be calculated.
	 * @throws IllegalArgumentException Where fields array is null or empty. Also thrown if any of the fields
	 * are null or empty. All-whitespace strings are considered empty.
	 */
	public Min(String[] fields)
		throws IllegalArgumentException
	{
		super(fields);
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
		for (String field : fieldNames)
		{
			// Map phase is pretty easy, just spit out the value for the field
			record.addFunctionOutput(field, new FunctionOutput(FUNCTION_NAME, record.getFieldValue(field)));
		}
	}


	/**
	 * Process a single record read from the post-Map phase data for the Reduce phase of processing.
	 * 
	 * @param record {@link java.util.HashMap} of parsed data in the form of <"FunctionName", "OutputValue">.
	 * @throws NumberFormatException Where a {@link org.trommel.trommel.Field} value is not numeric.
	 */
	public void handleReduceRecord(HashMap<String, String> record) 
		throws NumberFormatException
	{
		if (record.containsKey(FUNCTION_NAME))
		{		
			// Reduce is also pretty easy, grab Min's value from the HashMap and process it.
			double currentValue = Double.parseDouble(record.get(FUNCTION_NAME));
			
			if (currentValue < minValue)
			{
				minValue = currentValue;
			}
		}
	}
}
