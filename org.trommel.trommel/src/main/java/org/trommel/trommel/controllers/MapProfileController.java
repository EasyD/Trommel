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
package org.trommel.trommel.controllers;

import java.util.LinkedList;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.trommel.trommel.Field;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.MapRecordHandler;
import org.trommel.trommel.functions.DistinctMapper;
import org.trommel.trommel.functions.EmptyMapper;
import org.trommel.trommel.functions.LinearityMapper;
import org.trommel.trommel.functions.MaxMapper;
import org.trommel.trommel.functions.MinMapper;
import org.trommel.trommel.functions.VariabilityMapper;
import org.trommel.trommel.utilities.StringUtilities;


/**
 * Implementation of the Controller pattern for Trommel scenarios for profiling data during the Map
 * phase of processing.
 */
public class MapProfileController implements MapController
{
	//
	//	Private members
	//
	
	private Logger logger = null;
	private Field[] fields = null;
	private LinkedList<MapRecordHandler> functions = new LinkedList<MapRecordHandler>();
	
	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the instantiated
	 * {@link org.trommel.trommel.MapRecordHandler} instances to log to the Hadoop Task syslog file.
	 * @param fields The {@link org.trommel.trommel.Field} instances to be processed during the Map phase.
	 * @throws IllegalArgumentException Where logger is null or fields array is null or empty. Also thrown if any of the fields
	 * are null or empty.
	 */
	public MapProfileController(Logger logger, Field[] fields)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if (logger == null)
		{
			throw new IllegalArgumentException("Logger cannot be null.");
		}

		this.logger = logger;

		if (fields == null || fields.length == 0)
		{
			this.logger.error("Null or empty Fields array passed to MapProfileController constructor.");
			
			throw new IllegalArgumentException("Fields array cannot be null or empty.");
		}
		
		for (Field field : fields)
		{
			if (field == null)
			{
				this.logger.error("Null Fields array element passed to MapProfileController constructor.");
				
				throw new IllegalArgumentException("Fields array cannot contain null elements.");
			}
			
			logger.debug(String.format("MapProfileController constructed with Field: Name = %1$s, FieldType = %2$s.",
					     field.getName(), field.getType()));
		}
		
		this.fields = fields;
	}
	
	
	//
	//	Public methods
	//
		
	/**
	 * Add a {@link org.trommel.trommel.functions.Function} instance to the controller for Map processing.
	 * 
	 * @param function An {@link org.trommel.trommel.controllers.ProfileFunction} value specifying which 
	 * Function to be added.
	 */
	public void addFunction(ProfileFunction function)
	{
		addFunction(function, null);
	}
	
	/**
	 * Add a {@link org.trommel.trommel.functions.Function} instance to the controller for Map processing.
	 * 
	 * @param function An {@link org.trommel.trommel.controllers.ProfileFunction} value specifying which 
	 * Function to be added.
	 * @param parameter Parameter value to be used in instantiating Function instances.
	 */
	public void addFunction(ProfileFunction function, String parameter)
	{
		logger.debug(String.format("MapProfileController.addFunction called with function = %1$s, param = %2$s",
				                   function, parameter));
		
		switch(function)
		{
			case Max:
				functions.addLast(new MaxMapper(logger, fields));
				break;
			case Min:
				functions.addLast(new MinMapper(logger, fields));
				break;
			case Distinct:
				functions.addLast(new DistinctMapper(logger, fields));
				break;
			case Empty:
				functions.addLast(new EmptyMapper(logger, fields));
				break;
			case Var:
				functions.addLast(new VariabilityMapper(logger, fields));
				break;
			case Lin:
				if (StringUtilities.isNullOrEmpty(parameter))
				{
					// Instantiate the Linearity function with default
					functions.addLast(new LinearityMapper(logger, fields));					
				}
				else
				{
					functions.addLast(new LinearityMapper(logger, fields, Integer.parseInt(parameter)));
				}
				break;
		}
	}
	
	/**
	 * Process one record from the data set. The MapProfileController will invoke one or more 
	 * {@link org.trommel.trommel.MapRecordHandler} instances (i.e., Trommel functions) to profile the data, with 
	 * processing results stored in the {@link org.trommel.trommel.MapRecord}.
	 * 
	 * @param record The MapRecord to process and to be used to store processing results.
	 */
	public void handleMapRecord(MapRecord record) 
	{
		// This method is called at scale, optimize logging
		if (logger.isDebugEnabled())
		{
			logger.debug("MapProfileController.handleMapRecord called.");
		}
		
		ListIterator<MapRecordHandler> iterator = functions.listIterator();
		
		while (iterator.hasNext())
		{
			MapRecordHandler handler = iterator.next();
			
			handler.handleMapRecord(record);
		}
	}
}
