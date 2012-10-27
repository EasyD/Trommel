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
 *	Execute the Reduce phase for the count of empty values for a {@link org.trommel.trommel.Field}.
 */
public class EmptyReducer extends ReduceRecordHandler
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String HANDLER_NAME = "Empty";


	//
	//	Private members
	//
	private int emptyCount = 0;
	
	
	//
	//	Getters/setters
	//
	
	/**
	 * Return the name of the Distinct function.
	 * 
	 * @return The string value of "Empty".
	 */
	public String getHandlerName()
	{
		return HANDLER_NAME;
	}

	/**
	 * Return the current count of empty values.
	 * 
	 * @return The current empty count as a {@link java.lang.String}.
	 */
	public String getReduceResult()
	{
		logger.debug(String.format("EmptyReducer has current count of %1$d.", emptyCount));
		
		return Integer.toString(emptyCount);
	}

	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the EmptyReducer
	 * to log to the Hadoop Task syslog file.
	 * @throws IllegalArgumentException Where logger is null.
	 */
	public EmptyReducer(Logger logger)
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
	 */
	public void handleReduceRecord(HashMap<String, String> record) 
	{
		if (record.containsKey(HANDLER_NAME))
		{	
			// Reduce phase processing is easy, increment the count
			++emptyCount;

			// This method is called at scale, optimize logging
			if (logger.isDebugEnabled())
			{
				logger.debug("EmptyReducer.handleReduceRecord found an empty field value.");
			}
		}
	}
}
