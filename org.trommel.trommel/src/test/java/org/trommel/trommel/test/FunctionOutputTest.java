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
import org.trommel.trommel.FunctionOutput;


//
//	Unit tests for the org.trommel.trommel.FunctionOutput class
//
@SuppressWarnings("unused")
public class FunctionOutputTest 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String HANDLER_NAME = "Function Name";
	private static final String OUTPUT = "OUTPUT";
	
	
	//
	//	Tests
	//
	
	@Test
	public void testConstructorOK() 
	{
		
		FunctionOutput output = new FunctionOutput(HANDLER_NAME, OUTPUT);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorEmptyFunctionName() 
	{
		
		FunctionOutput output = new FunctionOutput("", OUTPUT);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullOutput() 
	{
		
		FunctionOutput output = new FunctionOutput(HANDLER_NAME, null);
	}

	@Test
	public void testSerialize()
	{
		FunctionOutput output = newFunctionOutput();
		String serializedOutput = HANDLER_NAME + "=" + OUTPUT;
		
		assertEquals(serializedOutput, output.serialize());
	}
	
	
	//
	//	Private/helper methods
	//
	private FunctionOutput newFunctionOutput()
	{
		return new FunctionOutput(HANDLER_NAME, OUTPUT);
	}	
}
