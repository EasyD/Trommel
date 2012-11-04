/*
 * Copyright 2012 Dave Langer
 *    
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package org.trommel.trommel.functions;

import org.apache.log4j.Logger;
import org.trommel.trommel.Field;
import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;


/**
 *	For Map phase, find the maximum value in the data set for numeric {@link org.trommel.trommel.Field} instances.
 *	The MaxMapper class ignores categorical Fields (i.e., they are not written as Map phase output).
 */
public class MaxMapper extends Function 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String HANDLER_NAME = "Max";

	
	//
	//	Private members
	//

	private double[] maxValues = null;


	//
	//	Getters/setters
	//
	
	/**
	 * Return the name of the Max function.
	 * 
	 * @return The string value of "Max".
	 */
	public String getHandlerName()
	{
		return HANDLER_NAME;
	}

	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the MaxMapper
	 * to log to the Hadoop Task syslog file.
	 * @param fields The numeric {@link org.trommel.trommel.Field} instances for which Max values will be calculated.
	 * @throws IllegalArgumentException Where logger is null or fields array is null or empty. Also thrown if any of the Fields
	 * are null or empty. All-whitespace strings are considered empty.
	 */
	public MaxMapper(Logger logger, Field[] fields)
		throws IllegalArgumentException
	{
		super(logger, fields);
		
		maxValues = new double[fields.length];
		
		for (int i = 0; i < fields.length; ++i)
		{
			maxValues[i] = Double.MIN_VALUE;
		}
	}
	
	
	//
	//	Public methods
	//
	
	/**
	 * Process a single {@link MapRecord} read from the data set for the Map phase of finding maximum
	 * numeric values.
	 * 
	 * @param record The MapRecord containing the data read from data set and any results of Map phase 
	 * maximum value processing.
	 * @throws IllegalArgumentException Where bubbled up from passed-in MapRecord.
	 */
	public void handleMapRecord(MapRecord record)
		throws IllegalArgumentException
	{
		// Only process the fields specified for the function
		for (int i = 0; i < fields.length; ++i)
		{
			Field field = fields[i];
			
			// Ignore categorical data
			if (field.isNumeric())
			{
				double value = Double.parseDouble(record.getFieldValue(field.getName()));
				
				// Optimization - write out as few values as possible.
				if (value > maxValues[i])
				{					
					FunctionOutput functionOutput = new FunctionOutput(HANDLER_NAME, record.getFieldValue(field.getName()));
					
					maxValues[i] = value;
					
					record.addFunctionOutput(field.getName(), functionOutput);
	
					// This method is called at scale, optimize logging
					if (logger.isDebugEnabled())
					{
						logger.debug(String.format("MaxMapper.handleMapRecord added output of fieldValue %1$s for Field %2$s.",
								                   record.getFieldValue(field.getName()), field.getName()));
					}				
				}
			}
		}
	}
}
