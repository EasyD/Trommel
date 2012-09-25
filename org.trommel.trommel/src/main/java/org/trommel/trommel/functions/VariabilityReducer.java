/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.ReduceRecordHandler;


/**
 *	For the Reduce phase find the variability for a {@link org.trommel.trommel.Field} as the sample 
 *	standard deviation for numeric Fields and the Rate of Discovery (ROD) for categorical Fields.
 *	Missing values are treated as zeroes for numeric fields and null values for categorical Fields
 * 	for the purposes of calculation.
 */
public class VariabilityReducer implements ReduceRecordHandler 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Variability";

	
	//
	//	Private members
	//
	private FieldType fieldType; 
	private int recordCount = 0;
	private SummaryStatistics stats = null;
	private HashMap<String, Integer> discoveredValues = null; 
	
			
	//
	//	Getters/setters
	//
	
	/**
	 * Return the name of the Variability function.
	 * 
	 * @return The string value of "Variability".
	 */
	public String getHandlerName()
	{
		return FUNCTION_NAME;
	}

	/**
	 * Return the current calculation of {@link org.trommel.trommel.Field} variability.
	 * 
	 * @return The current variability calculation as a {@link java.lang.String}.
	 */
	public String getReduceResult()
	{
		if (fieldType == FieldType.numeric)
		{
			// Return the sample standard deviation as a String
			return Double.toString(stats.getStandardDeviation());
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
	 * @param fieldType Specifies if VariabilityReducer is processing numeric or categorical data.
	 */
	public VariabilityReducer(FieldType fieldType)
	{
		this.fieldType = fieldType;
		
		if (this.fieldType == FieldType.numeric)
		{
			stats = new SummaryStatistics();
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
				// Add numeric value to the SummaryStatistics instance
				stats.addValue(Double.parseDouble(record.get(FUNCTION_NAME)));
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
