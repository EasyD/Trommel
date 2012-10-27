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
import org.trommel.trommel.MapRecordHandler;
import org.trommel.trommel.Field;


/**
 *	Abstract base class for Trommel functions.
 */
public abstract class Function extends MapRecordHandler 
{
	//
	//	Private members
	//
	protected Field[] fields;
	
	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the Function to log to the 
	 * Hadoop Task syslog file.
	 * @param fields The {@link org.trommel.trommel.Field} instances that will be processed by the Function.
	 * @throws IllegalArgumentException Where logger is null or fields array is null or empty. Also thrown if an array element
	 * is null.
	 */
	public Function(Logger logger, Field[] fields)
		throws IllegalArgumentException
	{
		super(logger);
		
		// Check for illegal input
		if (fields == null)
		{
			logger.error("Function constructor passed a null Fields array.");
			
			throw new IllegalArgumentException("Fields array cannot be null.");
		}
		
		if (fields.length == 0)
		{
			logger.error("Function constructor passed an empty Fields array.");
			
			throw new IllegalArgumentException("Fields array cannot be empty.");
		}
		
		for (Field field : fields)
		{
			if (field == null)
			{
				logger.error("Function constructor passed a Fields array with a null element.");
				
				throw new IllegalArgumentException("Field array element cannot be null or empty.");
			}
		}
		
		this.fields = fields;
	}
}
