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
 *	Missing values are treated as zeroes for numeric fields and null values for categorical Fields
 * 	for the purposes of calculation.
 */
public class VariabilityMapper extends Function 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Variability";
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
	 * are null or empty.
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
					record.addFunctionOutput(field.getName(), new FunctionOutput(FUNCTION_NAME, "0"));
				}
				else
				{
					// Write out the value 
					record.addFunctionOutput(field.getName(), new FunctionOutput(FUNCTION_NAME, fieldValue));
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
