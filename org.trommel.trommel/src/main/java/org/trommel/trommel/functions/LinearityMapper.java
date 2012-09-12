/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.Random;

import org.trommel.trommel.Field;
import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.functions.Function;
import org.trommel.trommel.utilities.StringUtilities;


/**
 *	For the Map phase find the interstitial linearity for a {@link org.trommel.trommel.Field} as a value ranging from 0.0 
 *	(no linearity) to 1.0 (very high linearity) for numeric Fields and the Rate of Discovery (ROD) for categorical Fields.
 *	For numeric fields, the interstitial linearity is calculated from a random sample of individual records.
 */
public class LinearityMapper extends Function 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Linearity";
	private static final String NULL_INDICATOR = "null";
	
	
	//
	//	Private members
	//
	private int sampleRate = 10;
	private Random random = new Random();
	
	
	//
	//	Getters/setters
	//
	
	/**
	 * Return the name of the Linearity function.
	 * 
	 * @return The string value of "Linearity".
	 */
	public String getHandlerName()
	{
		return FUNCTION_NAME;
	}

	
	//
	//	Constructors
	//
	
	/**
	 * Construct a LinearityMapper with a default random sample rate of approximately 10% for numeric fields.
	 * 
	 * @param fields The {@link org.trommel.trommel.Field} instances for which linearity will be calculated.
	 * @throws IllegalArgumentException Where fields array is null or empty. Also thrown if any of the fields
	 * are null or empty.
	 */
	public LinearityMapper(Field[] fields)
		throws IllegalArgumentException
	{
		super(fields);
	}

	
	/**
	 * Construct a LinearityMapper with a specified approximate random sample rate.
	 * 
	 * @param fields The {@link org.trommel.trommel.Field} instances for which linearity will be calculated.
	 * @param sampleRate Specifies the approximate random sample rate as an integer in the range of 1-100 
	 * (e.g., 10 = 10% random sample rate), inclusive.
	 * @throws IllegalArgumentException Where sampleRate is not in the range of 1-100, inclusive.
	 */
	public LinearityMapper(Field[] fields, int sampleRate)
			throws IllegalArgumentException
	{		
		super(fields);
	
		// Check for illegal input
		if (sampleRate < 1 || sampleRate > 100)
		{
			throw new IllegalArgumentException("Linearity sample rate of " + Integer.toString(sampleRate) + " is not in range of 1-100.");
		}	
		
		this.sampleRate = sampleRate;
	}


	//
	//	Public methods
	//
	
	/**
	 * Process a single {@link MapRecord} read from the data set for the Map phase of calculating the
	 * linearity of {@link org.trommel.trommel.Field} values. 
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
				// Only write out a record if it falls in the random sample
				if (random.nextInt(100) < sampleRate)
				{
					// Variability is sample standard deviation for numeric fields
					if (StringUtilities.isNullOrEmpty(fieldValue))
					{
						// Write out zeroes for null/empty fields
						record.addFunctionOutput(field.getName(), new FunctionOutput(FUNCTION_NAME, "0"));
					}
					else
					{
						// Write out the value and the square of the value
						record.addFunctionOutput(field.getName(), new FunctionOutput(FUNCTION_NAME, fieldValue));
					}
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
