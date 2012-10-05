/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.trommel.trommel.ReduceRecordHandler;


/**
 *	For the Reduce phase find the minimum value in the data set for numeric {@link org.trommel.trommel.Field} instances.
 */
public class MinReducer extends ReduceRecordHandler 
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
		logger.debug(String.format("MinReducer has current min value of of %1$f.", minValue));

		return Double.toString(minValue);
	}

	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the MinReducer
	 * to log to the Hadoop Task syslog file.
	 * @throws IllegalArgumentException Where logger is null.
	 */
	public MinReducer(Logger logger)
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
			// Reduce is also pretty easy, grab Min's value from the HashMap and process it.
			double currentValue = Double.parseDouble(record.get(FUNCTION_NAME));
			
			if (currentValue < minValue)
			{
				minValue = currentValue;

				// This method is called at scale, optimize logging
				if (logger.isDebugEnabled())
				{
					logger.debug(String.format("MinReducer.handleReduceRecord found new min value of %1$f.", minValue));
				}
			}
		}
	}
}
