/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldType;


//
//	Unit tests for the org.trommel.trommel.Field class
//
@SuppressWarnings("unused")
public class FieldTest 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String NAME = "Name";
	
	
	//
	//	Tests
	//
	
	@Test
	public void testContructorOK() 
	{
		Field field = new Field(NAME, FieldType.categorical);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullFieldName() 
	{
		Field field = new Field(null, FieldType.categorical);
	}
	
	@Test
	public void testGetName() 
	{
		Field field = newCategoricalField();
		
		assertEquals(NAME, field.getName());
	}
	
	@Test
	public void testGetFieldType() 
	{
		Field field = newCategoricalField();
		
		assertEquals(FieldType.categorical, field.getType());
	}

	@Test
	public void testGetInstance() 
	{
		Field field = newCategoricalField();
		
		assertEquals(NAME, field.getInstance().getName());
		assertEquals(FieldType.categorical, field.getInstance().getType());
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
		
	
	//
	//	Private/helper methods
	//
	private Field newCategoricalField()
	{
		Field field = new Field(NAME, FieldType.categorical);
				
		return field;
	}	

	private Field newNumericField()
	{
		Field field = new Field(NAME, FieldType.numeric);
		
		return field;
	}	
}
