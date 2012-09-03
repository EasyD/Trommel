/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.trommel.trommel.ReduceRecordHandler;

/**
 *	For Reduce phase, find the maximum value in the data set for numeric {@link org.trommel.trommel.Field} instances.
 */
public class MaxReducer implements ReduceRecordHandler
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Max";

	//
	//	Private members
	//

	private double maxValue = Double.MIN_VALUE;
	
	
	//
	//	Getters/setters
	//
	
	/**
	 * Return the current maximum value.
	 * 
	 * @return The current maximum value found as a {@link java.lang.String}.
	 */
	public String getReduceResult()
	{
		return Double.toString(maxValue);
	}


	//
	//	Public methods
	//
	
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
			// Reduce is also pretty easy, grab Max's value from the HashMap and process it.
			double currentValue = Double.parseDouble(record.get(FUNCTION_NAME));
			
			if (currentValue > maxValue)
			{
				maxValue = currentValue;
			}
		}
	}
}
