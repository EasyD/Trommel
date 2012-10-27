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
 *	For the Map phase find the minimum value in the data set for numeric {@link org.trommel.trommel.Field} instances.
 *	The MinMapper class ignores categorical Fields (i.e., they are not written as Map phase output).
 */
public class MinMapper extends Function 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String HANDLER_NAME = "Min";

	
	//
	//	Getters/setters
	//
	
	/**
	 * Return the name of the Min function.
	 * 
	 * @return The string value of "Min".
	 */
	public String getHandlerName() 
	{
		return HANDLER_NAME;
	}


	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the MinMapper
	 * to log to the Hadoop Task syslog file.
	 * @param fields The numeric {@link org.trommel.trommel.Field} instances for which Min values will be calculated.
	 * @throws IllegalArgumentException Where logger is null or fields array is null or empty. Also thrown if any of the Fields
	 * are null or empty. All-whitespace strings are considered empty.
	 */
	public MinMapper(Logger logger, Field [] fields)
		throws IllegalArgumentException
	{
		super(logger, fields);
	}
	
	
	//
	//	Public methods
	//
	
	/**
	 * Process a single {@link MapRecord} read from the data set for the Map phase of finding minimum
	 * numeric values.
	 * 
	 * @param record The MapRecord containing the data read from data set and any results of Map phase 
	 * minimum value processing.
	 * @throws IllegalArgumentException Where bubbled up from passed-in MapRecord.
	 */
	public void handleMapRecord(MapRecord record) 
	{
		// Only process the fields specified for the function
		for (Field field : fields)
		{
			// Ignore categorical data
			if (field.isNumeric())
			{
				// Map phase is pretty easy, just spit out the value for the field
				record.addFunctionOutput(field.getName(), new FunctionOutput(HANDLER_NAME, record.getFieldValue(field.getName())));

				// This method is called at scale, optimize logging
				if (logger.isDebugEnabled())
				{
					logger.debug(String.format("MinMapper.handleMapRecord added output of fieldValue %1$s for Field %2$s.",
							                   record.getFieldValue(field.getName()), field.getName()));
				}				
			}
		}
	}
}
