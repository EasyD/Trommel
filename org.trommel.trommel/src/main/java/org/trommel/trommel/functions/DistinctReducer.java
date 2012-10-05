/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.trommel.trommel.ReduceRecordHandler;

/**
 *	Execute the Reduce phase for finding the count of distinct values for a {@link org.trommel.trommel.Field}.
 */
public class DistinctReducer extends ReduceRecordHandler
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
		logger.debug(String.format("DistinctReducer has current distinct count of %1$d.", distinctValues.size()));
		
		return Integer.toString(distinctValues.size());
	}
	
	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the DistinctReducer
	 * to log to the Hadoop Task syslog file.
	 * @throws IllegalArgumentException Where logger is null.
	 */
	public DistinctReducer(Logger logger)
		throws IllegalArgumentException
	{
		super(logger);
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

				// This method is called at scale, optimize logging
				if (logger.isDebugEnabled())
				{
					logger.debug(String.format("DistinctReducer.handleReduceRecord found distinct value %1$s.", fieldValue));
				}
			}
		}
	}
}
