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
package org.trommel.trommel.reporting;

import org.apache.log4j.Logger;
import org.trommel.trommel.Field;
import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.MapRecordHandler;


//	TODO - Need to refactor for multiple fields!
/**
 *	Execute the Map phase for reporting value frequencies (i.e., counts) for a specified {@link org.trommel.trommel.Field} 
 *	in a data set.
 */
public class DataReporterMapper extends MapRecordHandler 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String REPORTER_NAME = "DataReporter";

	
	//
	//	Private members
	//
	protected Field[] fields;
	

	//
	//	Getters/setters
	//
	
	/**
	 * Return the name of the DataReporter.
	 * 
	 * @return The string value of "DataReporter".
	 */
	public String getHandlerName()
	{
		return REPORTER_NAME;
	}

		
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the DataReporterMapper
	 * to log to the Hadoop Task syslog file.
	 * @param fields The {@link org.trommel.trommel.Field} instances that will be processed by the DataReporterMapper.
	 * @throws IllegalArgumentException Where logger is null or fields array is null or empty. Also thrown if an array element
	 * is null.
	 */
	public DataReporterMapper(Logger logger, Field[] fields)
		throws IllegalArgumentException
	{		
		super(logger);
		
		
		// Check for illegal input
		if (fields == null)
		{
			logger.error("DataReporterMapper constructor passed a null Fields array.");
			
			throw new IllegalArgumentException("Fields array cannot be null.");
		}
		
		if (fields.length == 0)
		{
			logger.error("DataReporterMapper constructor passed an empty Fields array.");
			
			throw new IllegalArgumentException("Fields array cannot be empty.");
		}
		
		for (Field field : fields)
		{
			if (field == null)
			{
				logger.error("DataReporterMapper constructor passed a Fields array with a null element.");
				
				throw new IllegalArgumentException("Field array element cannot be null or empty.");
			}
		}
		
		this.fields = fields;
	}
	
	
	//
	//	Public methods
	//
	
	/**
	 * Process a single {@link org.trommel.trommel.MapRecord} read from the data set for the Map phase of building a data
	 * report (i.e., field value frequencies).
	 * 
	 * @param record The MapRecord containing the data read from data set and any results of Map phase 
	 * maximum value processing.
	 * @throws IllegalArgumentException Where bubbled up from passed-in MapRecord.
	 */
	public void handleMapRecord(MapRecord record) 
	{
		for(Field field : fields)
		{			
			// Data reporting is pretty easy, just output the values by field for counting in Reduce phase
			record.addFunctionOutput(field.getName(), new FunctionOutput(REPORTER_NAME, record.getFieldValue(field.getName()))); 
	
			// This method is called at scale, optimize logging
			if (logger.isDebugEnabled())
			{
				logger.debug(String.format("DataReporterMapper.handleMapRecord added output of fieldValue %1$s for Field %2$s.",
						                   record.getFieldValue(field.getName()), field.getName()));
			}				
		}
	}
}
