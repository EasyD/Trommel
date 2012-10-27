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


/**
 * Interface that defines the functionality for classes that parse fields from raw data read from the 
 * data set. 
 */
public interface FieldParser 
{
	/**
	 * Parse field values from a record of raw data read from the data set. 
	 * 
	 * @param record One record's worth of raw data read from the data set.
	 * @return Parsed field values as a {@link java.lang.String} array.
	 * @throws IllegalArgumentException Where record is null or empty. All-whitespace strings are considered
	 * empty.
	 */
	public String[] parse(String record)
		throws IllegalArgumentException;
}
