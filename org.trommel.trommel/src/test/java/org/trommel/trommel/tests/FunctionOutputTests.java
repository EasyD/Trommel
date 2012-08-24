/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.trommel.trommel.FunctionOutput;

//
//Unit tests for the org.trommel.trommel.FunctionOutput class
//
public class FunctionOutputTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Function Name";
	private static final String OUTPUT = "OUTPUT";
	
	
	//
	//	Tests
	//
	
	@Test
	public void testConstructorOK() 
	{
		@SuppressWarnings("unused")
		FunctionOutput output = new FunctionOutput(FUNCTION_NAME, OUTPUT);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorEmptyFunctionName() 
	{
		@SuppressWarnings("unused")
		FunctionOutput output = new FunctionOutput("", OUTPUT);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullOutput() 
	{
		@SuppressWarnings("unused")
		FunctionOutput output = new FunctionOutput(FUNCTION_NAME, null);
	}

	@Test
	public void testSerialize()
	{
		FunctionOutput output = newFunctionOutput();
		String serializedOutput = FUNCTION_NAME + "=" + OUTPUT;
		
		assertEquals(serializedOutput, output.serialize());
	}
	
	
	//
	//	Private/helper methods
	//
	private FunctionOutput newFunctionOutput()
	{
		return new FunctionOutput(FUNCTION_NAME, OUTPUT);
	}	
}
