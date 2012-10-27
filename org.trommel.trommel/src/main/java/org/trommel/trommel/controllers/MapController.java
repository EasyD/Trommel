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

import org.trommel.trommel.MapRecord;


/**
 * Interface that defines the common functionality for all Map phase Trommel Controllers.
 */
public interface MapController 
{
	/**
	 * Process one record from the data set. The MapController will invoke one or more {@link org.trommel.trommel.MapRecordHandler}
	 * instances (i.e., Trommel functions) to process the data, with processing results stored in the 
	 * {@link org.trommel.trommel.MapRecord}.
	 * 
	 * @param record The MapRecord to process and to be used to store processing results.
	 */
	public void handleMapRecord(MapRecord record);
}
