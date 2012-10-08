/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.trommel.trommel.ReduceRecordHandler;


/**
 *	Execute the Reduce phase for the count of empty values for a {@link org.trommel.trommel.Field}.
 */
public class EmptyReducer extends ReduceRecordHandler
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
	 * Return the current count of empty values.
	 * 
	 * @return The current empty count as a {@link java.lang.String}.
	 */
	public String getReduceResult()
	{
		logger.debug(String.format("EmptyReducer has current count of %1$d.", emptyCount));
		
		return Integer.toString(emptyCount);
	}

	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the EmptyReducer
	 * to log to the Hadoop Task syslog file.
	 * @throws IllegalArgumentException Where logger is null.
	 */
	public EmptyReducer(Logger logger)
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
			// Reduce phase processing is easy, increment the count
			++emptyCount;

			// This method is called at scale, optimize logging
			if (logger.isDebugEnabled())
			{
				logger.debug("EmptyReducer.handleReduceRecord found an empty field value.");
			}
		}
	}
}
