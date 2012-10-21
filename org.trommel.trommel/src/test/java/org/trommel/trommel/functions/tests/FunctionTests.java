/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions.tests;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mockito.Mockito;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.functions.Function;
import org.trommel.trommel.functions.MaxMapper;


//
//	Unit tests for the org.trommel.trommel.functions.Function class
//
@SuppressWarnings("unused")
public class FunctionTests 
{
	//
	//	Tests
	//
	
	@Test
	public void testConstructorOK() 
	{
		Logger logger = Mockito.mock(Logger.class);
		Field[] fields = new Field[3];
		
		fields[0] = new Field("Foo", FieldType.numeric);
		fields[1] = new Field("Bar", FieldType.numeric);
		fields[2] = new Field("FooBar", FieldType.numeric);

		
		Function function = new MaxMapper(logger, fields);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullArray() 
	{
		Logger logger = Mockito.mock(Logger.class);
		
		Function function = new MaxMapper(logger, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorEmptyArray() 
	{
		Logger logger = Mockito.mock(Logger.class);
		
		Function function = new MaxMapper(logger, new Field[0]);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullArrayElement() 
	{
		Logger logger = Mockito.mock(Logger.class);
		Field[] fields = new Field[3];
		
		fields[0] = new Field("Foo", FieldType.numeric);
		fields[2] = new Field("FooBar", FieldType.numeric);

		
		Function function = new MaxMapper(logger, fields);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testContructArrayWithEmptyNames() 
	{
		Logger logger = Mockito.mock(Logger.class);
		Field[] fields = new Field[4];
		
		fields[0] = new Field("Foo", FieldType.numeric);
		fields[1] = new Field("Bar", FieldType.numeric);
		fields[2] = new Field(" \t", FieldType.numeric);
		fields[3] = new Field("FooBar", FieldType.numeric);

		
		Function function = new MaxMapper(logger, fields);
	}
}
