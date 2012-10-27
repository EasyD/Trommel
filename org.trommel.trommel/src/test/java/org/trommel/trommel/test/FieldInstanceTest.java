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
package org.trommel.trommel.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.trommel.trommel.FieldInstance;
import org.trommel.trommel.FieldType;


//
//	Unit tests for the org.trommel.trommel.Field class
//
public class FieldInstanceTest 
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
