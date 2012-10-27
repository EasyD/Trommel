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
import org.trommel.trommel.ReduceRecordHandler;


/**
 *	For Reduce phase, find the maximum value in the data set for numeric {@link org.trommel.trommel.Field} instances.
 */
public class MaxReducer extends ReduceRecordHandler
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String HANDLER_NAME = "Max";

	//
	//	Private members
	//

	private double maxValue = Double.MIN_VALUE;
	
	
	//
	//	Getters/setters
	//
	
	/**
	 * Return the name of the Distinct function.
	 * 
	 * @return The string value of "Max".
	 */
	public String getHandlerName()
	{
		return HANDLER_NAME;
	}

	/**
	 * Return the current maximum value. If no maximum value found (e.g., all categorical values), then
	 * an empty string is returned.
	 * 
	 * @return The current maximum value found as a {@link java.lang.String}.
	 */
	public String getReduceResult()
	{
		if (maxValue == Double.MIN_VALUE)
		{
			logger.debug(String.format("MaxReducer found no max value"));

			return "";			
		}
		else
		{
			logger.debug(String.format("MaxReducer has current max value of of %1$f.", maxValue));

			return Double.toString(maxValue);
		}		
	}

	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the MaxReducer
	 * to log to the Hadoop Task syslog file.
	 * @throws IllegalArgumentException Where logger is null.
	 */
	public MaxReducer(Logger logger)
		throws IllegalArgumentException
	{
		super(logger);
	}


	//
	//	Public methods
	//
	
	/**
	 * Process a single record read from the post-Map phase data for the Reduce phase of processing.
	 * 
	 * @param record {@link java.util.HashMap} of parsed data in the form of <"FunctionName", "OutputValue">.
	 * @throws NumberFormatException Where a numeric {@link org.trommel.trommel.Field} value is not numeric.
	 */
	public void handleReduceRecord(HashMap<String, String> record) 
		throws NumberFormatException
	{
		if (record.containsKey(HANDLER_NAME))
		{		
			// Reduce is also pretty easy, grab Max's value from the HashMap and process it.
			double currentValue = Double.parseDouble(record.get(HANDLER_NAME));
			
			if (currentValue > maxValue)
			{
				maxValue = currentValue;

				// This method is called at scale, optimize logging
				if (logger.isDebugEnabled())
				{
					logger.debug(String.format("MaxReducer.handleReduceRecord found new max value of %1$f.", maxValue));
				}
			}
		}
	}
}
