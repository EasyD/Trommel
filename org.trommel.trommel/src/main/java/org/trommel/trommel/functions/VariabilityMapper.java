/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import org.trommel.trommel.Field;
import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.utilities.StringUtilities;

/**
 *	For the Map phase find the variability for a {@link org.trommel.trommel.Field} as the sample 
 *	standard deviation for numeric Fields and the Rate of Discovery (ROD) for categorical Fields.
 */
public class VariabilityMapper extends Function 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Variability";
	private static final String DELIMITER = ":";
	private static final String NULL_INDICATOR = "null";


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

	
	//
	//	Constructors
	//
	
	/**
	 * @param fields The {@link org.trommel.trommel.Field} instances for which variability will be calculated.
	 * @throws IllegalArgumentException Where fields array is null or empty. Also thrown if any of the fields
	 * are null or empty. All-whitespace strings are considered empty.
	 */
	public VariabilityMapper(Field[] fields)
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
					String output = "0" + DELIMITER + "0";
					
					record.addFunctionOutput(field.getName(), new FunctionOutput(FUNCTION_NAME, output));
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
				// Variability is Rate of Discovery (ROD) for categorical fields 
				if(StringUtilities.isNullOrEmpty(fieldValue))
				{
					// The absence of value is included in ROD, write out null indicator
					record.addFunctionOutput(field.getName(), new FunctionOutput(FUNCTION_NAME, NULL_INDICATOR));					
				}
				else
				{
					record.addFunctionOutput(field.getName(), new FunctionOutput(FUNCTION_NAME, fieldValue));
				}
			}
		}
	}
}
