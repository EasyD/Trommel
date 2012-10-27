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
 * The representation of a {@link Field} instance (i.e., a column value) in a data set to be processed by Trommel.
 */
public final class FieldInstance extends Field
{
	//
	//	Private members
	//
	private String value;
	
	
	//
	//	Getters/setters
	//
	
	/**
	 * @return The FieldInstances's current value as a {@link java.lang.String}.
	 */
	public String getValue() 
	{
		return value;
	}

	/**
	 * @param value The FieldInstances's value as a {@link java.lang.String}.
	 * @exception IllegalArgumentException Where value is a non-numeric string when the FieldInstance is 
	 * specified as numeric.
	 */
	public void setValue(String value) 
		throws IllegalArgumentException
	{
		if (StringUtilities.isNullOrEmpty(value))
		{
			if (value != null)
			{
				this.value = value.trim();
			}
			else
			{
				this.value = null;
			}
		}
		else
		{
			
			if (fieldType == FieldType.numeric)
			{
				try
				{
					Double.parseDouble(value);
				}
				catch (NumberFormatException e)
				{
					throw new IllegalArgumentException("Value of " + value + " is not numeric.");
				}
			}
			
			this.value = value;
		}
	}

	
	//
	//	Constructors
	//
	
	/**
	 * @param name The name of the FieldInstance (i.e., the column name) in the data set.
	 * @param fieldType The type of values stored in the FieldInstance (i.e. numeric or categorical).
	 * @exception IllegalArgumentException Where name is null or an empty string. All-whitespace strings are considered empty.
	 */
	public FieldInstance(String name, FieldType fieldType)
		throws IllegalArgumentException
	{
		super(name, fieldType);
	}
	
	/**
	 * @param name The name of the Field (i.e., the column) in the data set.
	 * @param fieldType The type of values stored in the Field.
	 * @exception IllegalArgumentException Where name is null or an empty string. All-whitespace strings are considered empty.
	 */
	public FieldInstance(String name, FieldType fieldType, String value)
		throws IllegalArgumentException
	{
		super(name, fieldType);
		
		setValue(value);
	}	
}
