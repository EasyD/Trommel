/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.log4j.Logger;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.ReduceRecordHandler;


/**
 *	For the Map phase find the interstitial linearity for a {@link org.trommel.trommel.Field} as as a value ranging from 0.0 
 *	(no linearity) to 1.0 (very high linearity) for numeric Fields and the Rate of Discovery (ROD) for categorical Fields. 
 *	For numeric fields, the interstitial linearity is calculated from an approximate random sample of individual records
 *	sampled during the Map phase.
 */
public class LinearityReducer extends ReduceRecordHandler 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Linearity";

	
	//
	//	Private members
	//
	private FieldType fieldType; 
	private int recordCount = 0;
	private SimpleRegression regression = null;
	private Double previousValue = null;
	private HashMap<String, Integer> discoveredValues = null; 
	
		
	//
	//	Getters/setters
	//
	
	/**
	 * Return the current calculation of {@link org.trommel.trommel.Field} interstitial linerarity.
	 * 
	 * @return The current interstitial linearity calculation as a {@link java.lang.String}. A value of "-1.0" indicates
	 * that random sampling failed to produce any records for processing, while a value of "NaN" indicates not enough
	 * data points were processed to produce a calculation.
	 */
	public String getReduceResult()
	{
		// It's possible that we won't get any records from the Map phase due to random sampling
		if (recordCount == 0)
		{
			return "-1.0";
		}
		
		if (fieldType == FieldType.numeric)
		{
			// Return the absolute value of the Pearson's correlation coefficient for the interstitial differences
			return Double.toString(Math.abs(regression.getR()));
		}
		else
		{
			// Rate of Discovery is simply the unique discovered values divided by record count
			return Double.toString((double)discoveredValues.keySet().size() / recordCount);
		}
	}
	
	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the EmptyReducer
	 * to log to the Hadoop Task syslog file.
	 * @param fieldType Specifies if LinearityReducer is processing numeric or categorical data.
	 * @throws IllegalArgumentException Where logger is null.
	 */
	public LinearityReducer(Logger logger, FieldType fieldType)
		throws IllegalArgumentException
	{
		super(logger);
		
		this.fieldType = fieldType;
		
		if (this.fieldType == FieldType.numeric)
		{
			regression = new SimpleRegression();
		}
		else
		{
			discoveredValues = new HashMap<String, Integer>();
		}
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
		if (record.containsKey(FUNCTION_NAME))
		{	
			++recordCount;

			if (fieldType == FieldType.numeric)
			{
				Double currentValue = new Double(record.get(FUNCTION_NAME));

				// Only add intersitial differences to the SimpleRegression instance
				if (recordCount > 1)
				{
					double temp = currentValue - previousValue;
					
					regression.addData(recordCount - 1, temp);
				}
				
				previousValue = currentValue;
			}
			else
			{
				if(!discoveredValues.containsKey(record.get(FUNCTION_NAME)))
				{
					// New value discovered
					discoveredValues.put(record.get(FUNCTION_NAME), 1);
				}
			}
		}
	}
}
