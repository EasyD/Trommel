/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;

/**
 *	Find the count of distinct values for a {@link org.trommel.trommel.Field}.
 */
public class Distinct extends Function 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Distinct";

	
	//
	//	Private members
	//
	private HashMap<String, Integer> distinctValues = new HashMap<String, Integer>();
	
	
	//
	//	Getters/setters
	//
	
	/**
	 * Return the name of the Distinct function.
	 * 
	 * @return The string value of "Distinct".
	 */
	public String getHandlerName()
	{
		return FUNCTION_NAME;
	}
		
	/**
	 * Return the current count of distinct values.
	 * 
	 * @return The current distinct count as a {@link java.lang.String}.
	 */
	public String getReduceResult()
	{
		return Integer.toString(distinctValues.size());
	}

	
	//
	//	Constructors
	//
	
	/**
	 * @param fields The {@link org.trommel.trommel.Field} names for which distinct values will be counted.
	 * @throws IllegalArgumentException Where fields array is null or empty. Also thrown if any of the fields
	 * are null or empty. All-whitespace strings are considered empty.
	 */
	public Distinct(String[] fields)
		throws IllegalArgumentException
	{
		super(fields);
	}

	
	//
	//	Public methods
	//
	
	/**
	 * Process a single {@link MapRecord} read from the data set for the Map phase of finding the
	 * count of distinct {@link org.trommel.trommel.Field} values.
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
			String fieldValue = record.getFieldValue(field);
			
			// Only add output for distinct values found
			if (!distinctValues.containsKey(fieldValue))
			{
				distinctValues.put(fieldValue, null);
				
				record.addFunctionOutput(field, new FunctionOutput(FUNCTION_NAME, fieldValue));
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
			String fieldValue = record.get(FUNCTION_NAME);
			
			if (!distinctValues.containsKey(fieldValue))
			{
				distinctValues.put(fieldValue, null);
			}
		}
	}
}
