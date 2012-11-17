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

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.trommel.trommel.Field;
import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.utilities.StringUtilities;


/**
 *	Execute the Map phase for finding the count of distinct values for a {@link org.trommel.trommel.Field}.
 *  Empty Fields are ignored.
 */
public class DistinctMapper extends Function
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String HANDLER_NAME = "Distinct";

	
	//
	//	Private members
	//

	private HashMap<String, HashMap<String, Integer>> distinctValues = null;


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
		return HANDLER_NAME;
	}

	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the DistinctMapper
	 * to log to the Hadoop Task syslog file.
	 * @param fields The {@link org.trommel.trommel.Field} instances for which distinct values will be counted.
	 * @throws IllegalArgumentException Where logger is null or fields array is null or empty. Also thrown if any of the fields
	 * are null or empty. All-whitespace strings are considered empty.
	 */
	public DistinctMapper(Logger logger, Field[] fields)
		throws IllegalArgumentException
	{
		super(logger, fields);
		
		distinctValues = new HashMap<String, HashMap<String, Integer>>();
		
		for (Field field : fields)
		{
			distinctValues.put(field.getName(), new HashMap<String,Integer>());
		}
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
			
			// Only process non-empty Fields
			if (!StringUtilities.isNullOrEmpty(fieldValue) && !distinctValues.get(field.getName()).containsKey(fieldValue))
			{
				distinctValues.get(field.getName()).put(fieldValue, null);
				
				// Map phase is pretty easy, just spit out the value for the field
				record.addFunctionOutput(field.getName(), new FunctionOutput(HANDLER_NAME, fieldValue));
				
				// This method is called at scale, optimize logging
				if (logger.isDebugEnabled())
				{
					logger.debug(String.format("DistinctMapper.handleMapRecord found distinct value %1$s for Field %2$s.",
							                   fieldValue, field.getName()));
				}
			}
		}
	}
}
