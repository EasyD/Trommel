/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.trommel.trommel.ReduceRecordHandler;


/**
 *	For Reduce phase, find the maximum value in the data set for numeric {@link org.trommel.trommel.Field} instances.
 */
public class MaxReducer extends ReduceRecordHandler
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
		logger.debug(String.format("MaxReducer has current max value of of %1$f.", maxValue));

		return Double.toString(maxValue);
	}

	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the MaxReducer
	 * to log to the Hadoop Task syslog file.
	 * @throws IllegalArgumentException Where logger is null.
	 */
	public MaxReducer(Logger logger)
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
	 * @throws NumberFormatException Where a numeric {@link org.trommel.trommel.Field} value is not numeric.
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

				// This method is called at scale, optimize logging
				if (logger.isDebugEnabled())
				{
					logger.debug(String.format("MaxReducer.handleReduceRecord found new max value of %1$f.", maxValue));
				}
			}
		}
	}
}
