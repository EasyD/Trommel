/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.trommel.trommel.ReduceRecordHandler;


/**
 *	For the Reduce phase find the confidence level that all the specified variability for a {@link org.trommel.trommel.Field} 
 *	has been captured (e.g., 95% of all the variability for a numeric field). The ConfidenceReducer class relies on the 
 *	convergence of variability to determine confidence levels. For example, if the specified target variability is 95%, the
 *	ConfidenceReducer would report 95% confidence that 95% of the variability has been captured if 59 consecutive tests show
 *	no more than 5% variability.
 *	NOTE - This class can only be used in conjunction with a {@link VariabilityReducer} instance.
 */
public class ConfidenceReducer extends ReduceRecordHandler 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String HANDLER_NAME = "Confidence";
	private static final double DEFAULT_CONVERGENCE_VALUE = -1.0;
	
	
	//
	//	Private members
	//
	private double variabilityTarget = 0.0;
	private double variabilityRange = 0.0;
	private VariabilityReducer variabilityReducer = null;
	private int recordCount = 0;
	private double convergenceValue = DEFAULT_CONVERGENCE_VALUE;
	private int convergedTestCount = 0;
	private int bestConfidence = -1;
	private int bestConfidenceRecordCount = 0;
	
	
	//
	//	Getters/setters
	//
	
	/**
	 * Return the name of the Distinct function.
	 * 
	 * @return The string value of "Conf".
	 */
	public String getHandlerName()
	{
		return HANDLER_NAME;
	}

	/**
	 * Return the current calculation of {@link org.trommel.trommel.Field} confidence based on the
	 * number of consecutive successful tests, per the equation n = log(1-c)/log(c) where c represents
	 * the confidence level.
	 * 
	 * @return The current confidence calculation as a {@link java.lang.String}.
	 */
	public String getReduceResult()
	{
		// Return the better of the two - current confidence or the best found previously
		updateBestConfidence();
		
		return Integer.toString(bestConfidence);
	}
	
	
	/**
	 * @return The current record count required to get the best confidence level for the specified
	 * variability target.
	 */
	public String getConfidenceRecordCount()
	{
		updateBestConfidence();
		
		return Integer.toString(bestConfidenceRecordCount);
	}
	
		
	//
	//	Constructors
	//
	
	/**
	 * Instantiate a ConfidenceReducer that reports the confidence that 95% of the variability of a
	 * {@link org.trommel.trommel.Field} has been captured.
	 * 
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the ConfidenceReducer
	 * to log to the Hadoop Task syslog file.
	 * @param variabilityReducer The {@link VariabilityReducer} instance to be used in confidence calculations.
	 * @throws IllegalArgumentException Where variabilityTarget is not a value ranging from 1 to 99, inclusive. Also
	 * thrown if logger or variabilityReducer is null.
	 */
	public ConfidenceReducer(Logger logger, VariabilityReducer variabilityReducer)
		throws IllegalArgumentException
	{
		this(logger, 95, variabilityReducer);
	}	

	/**
	 * Instantiate a ConfidenceReducer that reports the confidence that the specified target of variability of a
	 * {@link org.trommel.trommel.Field} has been captured.
	 * 
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the ConfidenceReducer
	 * to log to the Hadoop Task syslog file.
	 * @param variabilityReducer The {@link VariabilityReducer} instance to be used in confidence calculations.
	 * @param variabilityTarget The target amount of captured variability for which confidence is reported (e.g.,
	 * "how confident am I that 95% of the field's variability has been captured?")
	 * @param variabilityReducer The {@link VariabilityReducer} instance to be used in confidence calculations.
	 * @throws IllegalArgumentException Where variabilityTarget is not a value ranging from 1 to 99, inclusive. Also
	 * thrown if logger or variabilityReducer is null.
	 */
	public ConfidenceReducer(Logger logger, int variabilityTarget, VariabilityReducer variabilityReducer)
		throws IllegalArgumentException
	{
		super(logger);
		
		// Check for illegal input
		if (variabilityTarget < 1 || variabilityTarget > 99)
		{
			logger.error(String.format("ConfidenceReducer constructor was passed an invalid variability target of %1$d",
					                   variabilityTarget));
			
			throw new IllegalArgumentException("VariabilityTarget must be between 1 and 99, inclusive.");
		}
		
		if (variabilityReducer == null)
		{
			logger.error("ConfidenceReducer constructor was passed a null VariabilityReducer.");

			throw new IllegalArgumentException("VariabilityReducer cannot be null.");
		}
		
		this.variabilityTarget = variabilityTarget / 100.0;
		this.variabilityRange = (1.0 - this.variabilityTarget) / 2.0;
		this.variabilityReducer = variabilityReducer;
	}
	

	//
	//	Public methods
	//
	
	/**
	 * Process a single record read from the post-Map phase data for the Reduce phase of processing.
	 * 
	 * @param record {@link java.util.HashMap} of parsed data in the form of <"FunctionName", "OutputValue">.
	 * @exception NumberFormatException Where values are not numeric.
	 */
	public void handleReduceRecord(HashMap<String, String> record) 
		throws NumberFormatException
	{
		if (record.containsKey(variabilityReducer.getHandlerName()))
		{	
			++recordCount;
			
			if (convergenceValue == DEFAULT_CONVERGENCE_VALUE)
			{
				convergenceValue = Double.parseDouble(variabilityReducer.getReduceResult());
			}
			else
			{
				double lowConvergenceRange = convergenceValue * (1.0 - variabilityRange);
				double highConvergenceRange = convergenceValue * (1.0 + variabilityRange);
				double value = Double.parseDouble(variabilityReducer.getReduceResult());
				
				if (lowConvergenceRange <= value && value <= highConvergenceRange)
				{
					++convergedTestCount;
				}
				else
				{
					// We've exceeded the variability target range, if it's the best 
					// confidence so far, store it
					updateBestConfidence();
					
					// Start over with the new value
					convergenceValue = value;
					convergedTestCount = 0;
				}
			}
		}
	}	
	
	
	//
	//	Private/helper methods
	//
	
	private void updateBestConfidence()
	{
		if (currentConfidenceLevel() > bestConfidence)
		{
			bestConfidence = currentConfidenceLevel();
			bestConfidenceRecordCount = recordCount;
			
			logger.debug(String.format("ConfidenceReducer.updateBestConfidence found new best confidence level of %1$d using %2$d records",
					                   bestConfidence, bestConfidenceRecordCount));
		}
	}
		
	private int currentConfidenceLevel()
	{
		// Leverage the equation of n = loc(1-c)/log(c) for determining the confidence level based
		// on the consecutive tests passed based on the specified variability target
		if (convergedTestCount <= 30)
		{
			switch(convergedTestCount)
			{
				case 0:
					return 0;
				case 1:
					// With this number of consecutive tests we can be no more optimistic than 50% confident
					// that the specified variability has been captured
					return 50;
				case 2:
					// With this number of consecutive tests we can be no more optimistic than 61% confident
					// that the specified variability has been captured
					return 61;
				case 3:
					return 68;
				case 4:
					return 74;
				case 5:
					return 75;
				case 6:
					return 77;
				case 7:
					return 79;
				case 8:
					return 81;
				case 9:
					return 82;
				case 10:
					return 83;
				case 11:
					return 84;
				case 12:
				case 13:
					return 85;
				case 14:
					return 86;
				case 15:
				case 16:
					return 87;
				case 17:
				case 18:
					return 88;
				case 19:
				case 20:
				case 21:
					return 89;
				case 22:
				case 23:
				case 24:
				case 25:
					return 90;
				case 26:
				case 27:
				case 28:
				case 29:
				case 30:
					return 91;
			}		
		}
		else if (31 <= convergedTestCount && convergedTestCount <= 36)
		{
			return 92;
		}
		else if (37 <= convergedTestCount && convergedTestCount <= 45)
		{
			return 93;
		}
		else if (46 <= convergedTestCount && convergedTestCount <= 58)
		{
			return 94;
		}
		else if (59 <= convergedTestCount && convergedTestCount <= 78)
		{
			return 95;
		}
		else if (79 <= convergedTestCount && convergedTestCount <= 115)
		{
			return 96;
		}
		else if (116 <= convergedTestCount && convergedTestCount <= 193)
		{
			return 97;
		}
		else if (194 <= convergedTestCount && convergedTestCount <= 458)
		{
			return 98;
		}
		else
		{
			return 99;
		}
		
		return 0;
	}
}
