/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.trommel.trommel.ReduceRecordHandler;


/**
 *	For the Reduce phase report the number of total records required to achieve the best level of confidence regarding
 *	how much of the specified variability in a {@link org.trommel.trommel.Field} has been captured.
 *	NOTE - This class can only be used in conjunction with a {@link ConfidenceReducer} instance.
 */
public class RequiredReducer extends ReduceRecordHandler 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String HANDLER_NAME = "Required";
	
		
	//
	//	Private members
	//
	private ConfidenceReducer confidenceReducer = null;
	
	
	//
	//	Getters/setters
	//
	
	/**
	 * Return the name of the Distinct function.
	 * 
	 * @return The string value of "Required".
	 */
	public String getHandlerName()
	{
		return HANDLER_NAME;
	}

	/**
	 * Return the current calculation of {@link org.trommel.trommel.Field} confidence based on the
	 * n-number of consecutive successful tests, per the equation n = log(1-c)/log(c) where c represents
	 * the confidence level.
	 * 
	 * @return The current confidence calculation as a {@link java.lang.String}.
	 */
	public String getReduceResult()
	{
		logger.debug(String.format("RequiredReducer has current record count of %1$s.", confidenceReducer.getConfidenceRecordCount()));

		return confidenceReducer.getConfidenceRecordCount();
	}
	
	
	//
	//	Constructors
	//

	/**
	 * Instantiate a RequiredReducer that reports the total number of records processed to arrived at the best
	 * confidence that all the specified variability for a {@link org.trommel.trommel.Field} has been captured.
	 * 
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the RequiredReducer
	 * @param confidenceReducer The {@link ConfidenceReducer} instance to be used in reporting required record counts.
	 * @throws IllegalArgumentException Where logger or confidenceReducer is null.
	 */
	public RequiredReducer(Logger logger, ConfidenceReducer confidenceReducer)
		throws IllegalArgumentException
	{
		super(logger);
		
		// Check for illegal input
		if (confidenceReducer == null)
		{
			logger.error("ConfidenceReducer constructor was passed a null ConfidenceReducer.");
			
			throw new IllegalArgumentException("ConfidenceReducer cannot be null.");
		}
		
		this.confidenceReducer = confidenceReducer;
	}
	

	//
	//	Public methods
	//
	
	/**
	 * Process a single record read from the post-Map phase data for the Reduce phase of processing. In the case
	 * of RequiredReducer, this method involves no processing.
	 * 
	 * @param record {@link java.util.HashMap} of parsed data in the form of <"FunctionName", "OutputValue">.
	 */
	public void handleReduceRecord(HashMap<String, String> record) 
	{ }		
}
