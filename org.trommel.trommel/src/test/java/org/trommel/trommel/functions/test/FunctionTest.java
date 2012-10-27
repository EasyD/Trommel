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
package org.trommel.trommel.functions.test;

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
public class FunctionTest 
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
