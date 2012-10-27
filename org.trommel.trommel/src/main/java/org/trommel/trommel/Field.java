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
  
import org.trommel.trommel.utilities.StringUtilities;


/**
 * The specification of a field (i.e., a column) in a data set to be processed by Trommel.
 */
public class Field 
{	
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FIELD_NAME_ERROR_STRING = "Field names cannot be null or an empty string.";

	
	//
	//	Private members
	//
	protected String name;
	protected FieldType fieldType;
	
	
	//
	//	Getters/setters
	//
	
	/**
	 * @return The Field's name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return The Field's {@link FieldType}.
	 */
	public FieldType getType()
	{
		return fieldType;
	}
	
	/**
	 * @return A new {@link FieldInstance} constructed from the Field.
	 */
	public FieldInstance getInstance()
	{
		return new FieldInstance(name, fieldType);
	}
	
		
	//
	//	Constructors
	//
	
	/**
	 * @param name The name of the Field (i.e., the column) in the data set.
	 * @param fieldType The type of values stored in the Field.
	 * @exception IllegalArgumentException Where name is null or an empty string. All-whitespace strings are considered empty.
	 */
	public Field(String name, FieldType fieldType)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if (StringUtilities.isNullOrEmpty(name))
		{
			throw new IllegalArgumentException(FIELD_NAME_ERROR_STRING);
		}
		
		this.name = name;
		this.fieldType = fieldType;
	}

	
	//
	//	Public methods
	//
	
	/**
	 * @return True if the Field contains a categorical value, false otherwise.
	 */
	public boolean isCategorical()
	{
		return fieldType == FieldType.categorical;
	}
	
	/**
	 * @return True if the Field contains a numeric value, false otherwise.
	 */
	public boolean isNumeric()
	{
		return fieldType == FieldType.numeric;
	}
}
