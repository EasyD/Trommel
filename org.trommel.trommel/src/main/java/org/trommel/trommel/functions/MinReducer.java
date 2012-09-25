/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.trommel.trommel.ReduceRecordHandler;


/**
 *	For the Reduce phase find the minimum value in the data set for numeric {@link org.trommel.trommel.Field} instances.
 */
public class MinReducer implements ReduceRecordHandler 
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
	 * Return the current Minimum value.
	 * 
	 * @return The current Minimum value found as a {@link java.lang.String}.
	 */
	public String getReduceResult()
	{
		return Double.toString(minValue);
	}

	
	//
	//	Public methods
	//

	/**
	 * Process a single record read from the post-Map phase data for the Reduce phase of processing.
	 * 
	 * @param record {@link java.util.HashMap} of parsed data in the form of <"FunctionName", "OutputValue">.
	 * @throws NumberFormatException Where a numeric {@link org.trommel.trommel.Field} value is not numeric.
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
