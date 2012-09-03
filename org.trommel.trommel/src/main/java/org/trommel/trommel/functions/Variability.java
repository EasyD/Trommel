/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.trommel.trommel.Field;
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
	private int recordCount = 0;
	private double sumOfSquares = 0;
	private double sumOfValues = 0;
	
	
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
		// In the Reduce phase there should only ever be one field
		if (fields[0].isNumeric())
		{
			// Return the sample standard deviation as a String
			return Double.toString(Math.sqrt(((recordCount * sumOfSquares) - (sumOfValues * sumOfValues)) / 
					                          (recordCount * (recordCount - 1))));
		}
		else
		{
			return null;
		}
	}

	//
	//	Constructors
	//
	
	/**
	 * @param fields The {@link org.trommel.trommel.Field} instances for which variability will be calculated.
	 * @throws IllegalArgumentException Where fields array is null or empty. Also thrown if any of the fields
	 * are null or empty. All-whitespace strings are considered empty.
	 */
	public Variability(Field[] fields)
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
		for (Field field : fields)
		{
			String fieldValue = record.getFieldValue(field.getName());

			if (field.isNumeric())
			{
				// Variability is sample standard deviation for numeric fields
				if (StringUtilities.isNullOrEmpty(fieldValue))
				{
					// Write out zeroes for null/empty fields
					record.addFunctionOutput(field.getName(), new FunctionOutput(FUNCTION_NAME, "0"));
				}
				else
				{
					// Write out the value and the square of the value using a class-specific delimiter
					double numericValue = Double.parseDouble(fieldValue);
					StringBuffer buffer = new StringBuffer();
					
					buffer.append(numericValue);
					buffer.append(DELIMITER);
					buffer.append(numericValue * numericValue);
					
					// Write out the value and the square of the value
					record.addFunctionOutput(field.getName(), new FunctionOutput(FUNCTION_NAME, buffer.toString()));
				}
			}
			else
			{
				// Variability is Rate of Discovery (ROD) for categorical fields, just write out the value if's not 
				// null/empty
				if (!StringUtilities.isNullOrEmpty(fieldValue))
				{
					record.addFunctionOutput(field.getName(), new FunctionOutput(FUNCTION_NAME, fieldValue));
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
			// In the Reduce phase there should only ever be one field
			if (fields[0].isNumeric())
			{
				// Parse numeric values
				String[] values = record.get(FUNCTION_NAME).split(DELIMITER);
				double numericValue = Double.parseDouble(values[0]);
				double squareValue = Double.parseDouble(values[1]);
				
				// Increment counts
				sumOfSquares += squareValue;
				sumOfValues += numericValue;
				++recordCount;
			}
			else
			{
				
			}
		}
	}
}
