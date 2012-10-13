/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import org.apache.log4j.Logger;
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
	private static final String HANDLER_NAME = "Variability";
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
		return HANDLER_NAME;
	}

	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the VariabilityMapper
	 * to log to the Hadoop Task syslog file.
	 * @param fields The {@link org.trommel.trommel.Field} instances for which variability will be calculated.
	 * @throws IllegalArgumentException Where logger is null or fields array is null or empty. Also thrown if any of the fields
	 * are null or empty.
	 */
	public VariabilityMapper(Logger logger, Field[] fields)
		throws IllegalArgumentException
	{
		super(logger, fields);
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

			if (field.isNumeric() && StringUtilities.isNullOrEmpty(fieldValue))
			{
				// Write out zeroes for null/empty fields
				fieldValue = "0";
			}
			else if(StringUtilities.isNullOrEmpty(fieldValue))
			{
				// The absence of value is included in Rate of Discovery, write out null indicator
				fieldValue = NULL_INDICATOR;
			}

			// Write out value
			record.addFunctionOutput(field.getName(), new FunctionOutput(HANDLER_NAME, fieldValue));

			// This method is called at scale, optimize logging
			if (logger.isDebugEnabled())
			{
				logger.debug(String.format("VariabilitMapper.handleMapRecord added output of fieldValue %1$s for Field %2$s.",
						                   fieldValue, field.getName()));
			}				
		}
	}
}
