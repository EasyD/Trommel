/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.trommel.trommel.FieldInstance;
import org.trommel.trommel.FieldType;

//
//	Unit tests for the org.trommel.trommel.Field class
//
public class FieldInstanceTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String NAME = "Name";
	private static final String CATEGORICAL_VALUE = "Value";

	//
	//	Tests
	//
	
	@Test
	public void testConstructorNullFieldValueOK()
	{
		@SuppressWarnings("unused")
		FieldInstance fieldInstance = new FieldInstance(NAME, FieldType.categorical, null);		
	}

	@Test
	public void testConstructorWhitespaceFieldValueOK()
	{
		FieldInstance fieldInstance = new FieldInstance(NAME, FieldType.categorical, " \t ");		
		
		assertEquals(0, fieldInstance.getValue().length());
	}

	@Test
	public void testConstructorNumericFieldValueOK()
	{
		String numericValue = "-24.56";
		FieldInstance fieldInstance = new FieldInstance(NAME, FieldType.numeric, numericValue);		
		
		assertEquals(numericValue, fieldInstance.getValue());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorInvalidNumericFieldValue()
	{
		FieldInstance fieldInstance = new FieldInstance(NAME, FieldType.numeric, "Foo");		
		
		assertEquals(0, fieldInstance.getValue().length());
	}
	
	@Test
	public void testGetValue() 
	{
		FieldInstance fieldInstance = newCategoricalFieldInstance();
		
		assertEquals(CATEGORICAL_VALUE, fieldInstance.getValue());
	}

	//
	//	Private/helper methods
	//

	private FieldInstance newCategoricalFieldInstance()
	{
		FieldInstance fieldInstance = new FieldInstance(NAME, FieldType.categorical);
		
		fieldInstance.setValue(CATEGORICAL_VALUE);
		
		return fieldInstance;
	}	
}
