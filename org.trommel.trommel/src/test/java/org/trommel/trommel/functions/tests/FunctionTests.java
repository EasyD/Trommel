/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel.functions.tests;

import org.junit.Test;

//
//	Unit tests for the org.trommel.trommel.functions.Funciton class
//
public class FunctionTests 
{
	//
	//	Tests
	//
	
	@Test
	public void testContructorOK() 
	{
		String[] fieldNames = {"Foo", "Bar", "FooBar"};
		
		@SuppressWarnings("unused")
		MockFunction function = new MockFunction(fieldNames);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testContructNullArray() 
	{
		@SuppressWarnings("unused")
		MockFunction function = new MockFunction(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testContructEmptyArray() 
	{
		@SuppressWarnings("unused")
		MockFunction function = new MockFunction(new String[0]);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testContructArrayWithEmptyNames() 
	{
		String[] fieldNames = {"Foo", "Bar", " \t", "FooBar"};
		
		@SuppressWarnings("unused")
		MockFunction function = new MockFunction(fieldNames);
	}
}
