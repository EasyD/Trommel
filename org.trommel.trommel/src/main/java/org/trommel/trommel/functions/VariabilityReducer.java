/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.trommel.trommel.FieldType;
import org.trommel.trommel.ReduceRecordHandler;

/**
 *	For the Map phase find the variability for a {@link org.trommel.trommel.Field} as the sample 
 *	standard deviation for numeric Fields and the Rate of Discovery (ROD) for categorical Fields.
 */
public class VariabilityReducer implements ReduceRecordHandler 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Variability";
	private static final String DELIMITER = ":";

	
	//
	//	Private members
	//
	private FieldType fieldType; 
	private int recordCount = 0;
	private double sumOfSquares = 0;
	private double sumOfValues = 0;
	private HashMap<String, Integer> discoveredValues = new HashMap<String, Integer>(); 
	
	
	//
	//	Getters/setters
	//
	
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
			return Double.toString(Math.sqrt(((recordCount * sumOfSquares) - (sumOfValues * sumOfValues)) / 
				                              (recordCount * (recordCount - 1))));
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
				// Parse numeric values
				String[] values = record.get(FUNCTION_NAME).split(DELIMITER);
				double numericValue = Double.parseDouble(values[0]);
				double squareValue = Double.parseDouble(values[1]);
				
				// Increment counts
				sumOfSquares += squareValue;
				sumOfValues += numericValue;
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
