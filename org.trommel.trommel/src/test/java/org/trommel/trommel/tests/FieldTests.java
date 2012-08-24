/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldType;

//
//	Unit tests for the org.trommel.trommel.Field class
//
public class FieldTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String NAME = "Name";
	private static final String CATEGORICAL_VALUE = "Value";
	private static final String NUMERIC_VALUE = "10";
	
	
	//
	//	Tests
	//
	
	@Test
	public void testContructorOK() 
	{
		@SuppressWarnings("unused")
		Field field = new Field(NAME, FieldType.categorical);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullFieldName() 
	{
		@SuppressWarnings("unused")
		Field field = new Field(null, FieldType.categorical);
	}
	
	@Test
	public void testContructorThreeParamsOK() 
	{
		@SuppressWarnings("unused")
		Field field = new Field(NAME, FieldType.categorical, CATEGORICAL_VALUE);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorThreeParamsNullFieldName() 
	{
		@SuppressWarnings("unused")
		Field field = new Field(null, FieldType.categorical, CATEGORICAL_VALUE);
	}

	@Test
	public void testGetName() 
	{
		Field field = newCategoricalField();
		
		assertEquals(NAME, field.getName());
	}
	
	@Test
	public void testGetValue() 
	{
		Field field = newCategoricalField();
		
		assertEquals(CATEGORICAL_VALUE, field.getValue());
	}
	
	@Test
	public void testIsCategoricalWhenCategorical() 
	{
		Field field = newCategoricalField();
		
		assertEquals(true, field.isCategorical());
	}
	
	@Test
	public void testIsNumericWhenCategorical() 
	{
		Field field = newCategoricalField();
		
		assertEquals(false, field.isNumeric());
	}
	
	@Test
	public void testIsCategoricalWhenNumeric() 
	{
		Field field = newNumericField();
		
		assertEquals(false, field.isCategorical());
	}
	
	@Test
	public void testIsNumericWhenNumeric() 
	{
		Field field = newNumericField();
				
		assertEquals(true, field.isNumeric());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetFieldValueNullValue()
	{
		Field field = newCategoricalField();
		
		field.setValue(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetFieldValueNonNumericValue()
	{
		Field field = newNumericField();
		
		field.setValue(CATEGORICAL_VALUE);
	}
	
	//
	//	Private/helper methods
	//
	private Field newCategoricalField()
	{
		Field field = new Field(NAME, FieldType.categorical);
		
		field.setValue(CATEGORICAL_VALUE);
		
		return field;
	}	

	private Field newNumericField()
	{
		Field field = new Field(NAME, FieldType.numeric);
		
		field.setValue(NUMERIC_VALUE);
		
		return field;
	}	
}
