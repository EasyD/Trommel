/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions.tests;

import org.junit.Test;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.functions.Function;
import org.trommel.trommel.functions.MaxMapper;


//
//	Unit tests for the org.trommel.trommel.functions.Function class
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
		Function function = new MaxMapper(fields);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullArray() 
	{
		@SuppressWarnings("unused")
		Function function = new MaxMapper(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorEmptyArray() 
	{
		@SuppressWarnings("unused")
		Function function = new MaxMapper(new Field[0]);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullArrayElement() 
	{
		Field[] fields = new Field[3];
		
		fields[0] = new Field("Foo", FieldType.numeric);
		fields[2] = new Field("FooBar", FieldType.numeric);

		@SuppressWarnings("unused")
		Function function = new MaxMapper(fields);
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
		Function function = new MaxMapper(fields);
	}
}
