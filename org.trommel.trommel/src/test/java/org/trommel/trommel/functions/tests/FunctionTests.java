/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel.functions.tests;

import org.junit.Test;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldType;

//
//	Unit tests for the org.trommel.trommel.functions.Funciton class
//
public class FunctionTests 
{
	//
	//	Tests
	//
	
	@Test
	public void testConstructorOK() 
	{
		Field[] fields = new Field[3];
		
		fields[0] = new Field("Foo", FieldType.numeric);
		fields[1] = new Field("Bar", FieldType.numeric);
		fields[2] = new Field("FooBar", FieldType.numeric);

		@SuppressWarnings("unused")
		MockFunction function = new MockFunction(fields);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullArray() 
	{
		@SuppressWarnings("unused")
		MockFunction function = new MockFunction(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorEmptyArray() 
	{
		@SuppressWarnings("unused")
		MockFunction function = new MockFunction(new Field[0]);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullArrayElement() 
	{
		Field[] fields = new Field[3];
		
		fields[0] = new Field("Foo", FieldType.numeric);
		fields[2] = new Field("FooBar", FieldType.numeric);

		@SuppressWarnings("unused")
		MockFunction function = new MockFunction(fields);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testContructArrayWithEmptyNames() 
	{
		Field[] fields = new Field[4];
		
		fields[0] = new Field("Foo", FieldType.numeric);
		fields[1] = new Field("Bar", FieldType.numeric);
		fields[2] = new Field(" \t", FieldType.numeric);
		fields[3] = new Field("FooBar", FieldType.numeric);

		
		@SuppressWarnings("unused")
		MockFunction function = new MockFunction(fields);
	}
}
