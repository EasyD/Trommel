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
package org.trommel.trommel;

import org.apache.log4j.Logger;


/**
 * The interface for all Trommel constructs (i.e., Functions and Data Reporters) that process
 * records during the Map phase.
 */
public abstract class MapRecordHandler extends RecordHandler 
{
	//
	//	Getters/setters
	//
	
	/**
	 * Return the RecordHandler's name (e.g., "Max").
	 * 
	 * @return Name of record handler.
	 */
	public abstract String getHandlerName();
	
	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instances that will be used by the MapRecordHandler
	 * to log to the Hadoop Task syslog file.
	 * @throws IllegalArgumentException Where logger is null or empty.
	 */
	public MapRecordHandler(Logger logger)
		throws IllegalArgumentException
	{
		super(logger);
	}

	
	//
	//	Public methods
	//
	
	/**
	 * Process a single {@link MapRecord} read from the data set for the Map phase of processing.
	 * 
	 * @param record The MapRecord containing the data read from data set and any results of Map phase 
	 * Trommel processing.
	 */
	public abstract void handleMapRecord(MapRecord record);
}
