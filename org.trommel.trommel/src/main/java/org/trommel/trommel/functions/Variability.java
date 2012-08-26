/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.trommel.trommel.FieldType;
import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.utilities.StringUtilities;

/**
 *	Find the variability for a {@link org.trommel.trommel.Field} as the sample standard deviation
 *	for numeric Fields and the Rate of Discovery (ROD) for categorical Fields.
 */
public class Variability extends Function
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Variability";
	private static final String DELIMITER = ":";

	
	//
	//	Private members
	//
	private HashMap<String, Integer> distinctValues = new HashMap<String, Integer>();
	
	
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
	 * Return the current count of distinct values.
	 * 
	 * @return The current distinct count as a {@link java.lang.String}.
	 */
	public String getReduceResult()
	{
		return Integer.toString(distinctValues.size());
	}

	//
	//	Constructors
	//
	
	/**
	 * @param fields The {@link org.trommel.trommel.Field} names for which variability will be calculated.
	 * @throws IllegalArgumentException Where fields array is null or empty. Also thrown if any of the fields
	 * are null or empty. All-whitespace strings are considered empty.
	 */
	public Variability(String[] fields)
		throws IllegalArgumentException
	{
		super(fields);
	}


	//
	//	Public methods
	//
	
	/**
	 * Process a single {@link MapRecord} read from the data set for the Map phase of calculating the
	 * variability of {@link org.trommel.trommel.Field} values.
	 * 
	 * @param record The MapRecord containing the data read from data set and any results of Map phase 
	 * maximum value processing.
	 * @throws IllegalArgumentException Where bubbled up from passed-in MapRecord.
	 * @throws NumberFormatException Where a numeric {@link org.trommel.trommel.Field} value is, in fact, 
	 * not numeric.
	 */
	public void handleMapRecord(MapRecord record) 
			throws IllegalArgumentException, NumberFormatException
	{
		// Only process the fields specified for the function
		for (String field : fieldNames)
		{
			String fieldValue = record.getFieldValue(field);

			if (record.getFieldType(field) == FieldType.numeric)
			{
				// Variability is sample standard deviation for numeric fields
				if (StringUtilities.isNullOrEmpty(fieldValue))
				{
					// Write out zeroes for null/empty fields
					record.addFunctionOutput(field, new FunctionOutput(FUNCTION_NAME, "0"));
				}
				else
				{
					// Write out the FieldType, the value, and the square of the value using a class-specific delimiter
					double numericValue = Double.parseDouble(fieldValue);
					StringBuffer buffer = new StringBuffer();
					
					buffer.append(FieldType.numeric.toString());
					buffer.append(DELIMITER);
					buffer.append(numericValue);
					buffer.append(DELIMITER);
					buffer.append(numericValue * numericValue);
					
					// Write out the value and the square of the value
					record.addFunctionOutput(field, new FunctionOutput(FUNCTION_NAME, buffer.toString()));
				}
			}
			else
			{
				// Variability is Rate of Discovery (ROD) for categorical fields, just write out the FieldType
				// and value if's not null/empty
				if (!StringUtilities.isNullOrEmpty(fieldValue))
				{
					StringBuffer buffer = new StringBuffer();
					
					buffer.append(FieldType.numeric.toString());
					buffer.append(DELIMITER);
					buffer.append(fieldValue);

					record.addFunctionOutput(field, new FunctionOutput(FUNCTION_NAME, buffer.toString()));
				}
			}
		}
	}

	/**
	 * Process a single record read from the post-Map phase data for the Reduce phase of processing.
	 * 
	 * @param record {@link java.util.HashMap} of parsed data in the form of <"FunctionName", "OutputValue">.
	 */
	public void handleReduceRecord(HashMap<String, String> record) 
	{
		if (record.containsKey(FUNCTION_NAME))
		{		
			// TODO - Implement
		}
	}
}
