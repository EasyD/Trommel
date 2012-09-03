/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.trommel.trommel.ReduceRecordHandler;

/**
 *	Execute the Reduce phase for finding the count of distinct values for a {@link org.trommel.trommel.Field}.
 */
public class DistinctReducer implements ReduceRecordHandler
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
	 * Return the current count of distinct values.
	 * 
	 * @return The current distinct count as a {@link java.lang.String}.
	 */
	public String getReduceResult()
	{
		return Integer.toString(distinctValues.size());
	}
	
	//
	//	Public methods
	//

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
