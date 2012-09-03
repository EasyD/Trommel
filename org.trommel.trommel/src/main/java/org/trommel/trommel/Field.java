/*
 *	TODO - Insert license blurb here
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
//	private String value;
	
	
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
	
	
//	/**
//	 * @return The Field's current value as a {@link String}.
//	 */
//	public String getValue() 
//	{
//		return value;
//	}
//
//	/**
//	 * @param value The Field's value as a {@link String}.
//	 * @exception IllegalArgumentException Where value is a non-numeric string when the Field is 
//	 * specified as numeric.
//	 */
//	public void setValue(String value) 
//		throws IllegalArgumentException
//	{
//		if (StringUtilities.isNullOrEmpty(value))
//		{
//			if (value != null)
//			{
//				this.value = value.trim();
//			}
//			else
//			{
//				this.value = null;
//			}
//		}
//		
//		if (fieldType == FieldType.numeric)
//		{
//			try
//			{
//				Double.parseDouble(value);
//			}
//			catch (NumberFormatException e)
//			{
//				throw new IllegalArgumentException("Value of " + value + "is not numeric.");
//			}
//		}
//		
//		this.value = value;
//	}

	
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
	
//	/**
//	 * @param name The name of the Field (i.e., the column) in the data set.
//	 * @param fieldType The type of values stored in the Field.
//	 * @exception IllegalArgumentException Where name is null or an empty string. All-whitespace strings are considered empty.
//	 */
//	public Field(String name, FieldType fieldType, String value)
//		throws IllegalArgumentException
//	{
//		// Check for illegal input
//		if (StringUtilities.isNullOrEmpty(name))
//		{
//			throw new IllegalArgumentException(FIELD_NAME_ERROR_STRING);
//		}
//		
//		this.name = name;
//		this.fieldType = fieldType;
//		
//		setValue(value);
//	}
	
	
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
