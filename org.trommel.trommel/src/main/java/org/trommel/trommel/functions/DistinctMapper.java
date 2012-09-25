/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.trommel.trommel.Field;
import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;


/**
 *	Execute the Map phase for finding the count of distinct values for a {@link org.trommel.trommel.Field}.
 */
public class DistinctMapper extends Function
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Distinct";

	
	//
	//	Private members
	//
	private HashMap<String, Integer> distinctValues = new HashMap<String, Integer>();
	
	
	//
	//	Getters/setters
	//
	
	/**
	 * Return the name of the Distinct function.
	 * 
	 * @return The string value of "Distinct".
	 */
	public String getHandlerName()
	{
		return FUNCTION_NAME;
	}

	
	//
	//	Constructors
	//
	
	/**
	 * @param fields The {@link org.trommel.trommel.Field} instances for which distinct values will be counted.
	 * @throws IllegalArgumentException Where fields array is null or empty. Also thrown if any of the fields
	 * are null or empty. All-whitespace strings are considered empty.
	 */
	public DistinctMapper(Field[] fields)
		throws IllegalArgumentException
	{
		super(fields);
	}
	
	
	//
	//	Public methods
	//
		
	/**
	 * Process a single {@link MapRecord} read from the data set for the Map phase of finding the
	 * count of distinct {@link org.trommel.trommel.Field} values.
	 * 
	 * @param record The MapRecord containing the data read from data set and any results of Map phase 
	 * maximum value processing.
	 * @throws IllegalArgumentException Where bubbled up from passed-in MapRecord.
	 */
	public void handleMapRecord(MapRecord record) 
	{
		// Only process the fields specified for the function
		for (Field field : fields)
		{
			String fieldValue = record.getFieldValue(field.getName());
			
			// Only add output for distinct values found
			if (!distinctValues.containsKey(fieldValue))
			{
				distinctValues.put(fieldValue, null);
				
				record.addFunctionOutput(field.getName(), new FunctionOutput(FUNCTION_NAME, fieldValue));
			}			
		}
	}
}
