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

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.mockito.Mockito;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.MapContext;
import org.junit.Test;
import org.trommel.trommel.FieldInstance;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.scripting.interpreters.MapInterpreter;


//
//	Unit tests for the org.trommel.trommel.MapRecord class
//
@SuppressWarnings("unused")
public class MapRecordTest 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FIELD1 = "Field1";
	private static final String FIELD2 = "Field2";
	private static final String FIELD3 = "Field3";
	private static final String FIELD1_VALUE = "Value1";
	private static final String FIELD2_VALUE = "Value2";
	private static final String FIELD3_VALUE = "Value3";

	
	//
	//	Tests
	//
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNulLFieldsArray()
	{
		
		MapRecord record = new MapRecord(null, MapInterpreter.DELIMITER);
	}
		
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorEmptyFieldsArray()
	{
		
		MapRecord record = new MapRecord(new FieldInstance[0], MapInterpreter.DELIMITER);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorEmptyDelimiter()
	{
		FieldInstance[] fieldInstances = new FieldInstance[1];
		
		fieldInstances[0] = new FieldInstance(FIELD1, FieldType.categorical);
		
		
		MapRecord record = new MapRecord(fieldInstances, " ");
	}
	
	@Test
	public void testConstructorDelimiter()
	{
		FieldInstance[] fieldInstances = new FieldInstance[1];
		
		fieldInstances[0] = new FieldInstance(FIELD1, FieldType.categorical);
		
		
		MapRecord record = new MapRecord(fieldInstances, MapInterpreter.DELIMITER);
	}

	@Test
	public void testAddFunctionOutput() 
	{
		// Placeholder as Record delegates to contained OutputSet instance
		MapRecord record = newRecord();
		
		record.addFunctionOutput(FIELD1, new FunctionOutput("Func1", "Output1"));
	}

	@Test
	public void testSerialize() 
		throws IllegalArgumentException, IOException, InterruptedException
	{
		// Place holder as Record delegates to contained OutputSet instance
		
		@SuppressWarnings("unchecked")
		MapContext<LongWritable, Text, Text, Text> context = Mockito.mock(MapContext.class);
		MapRecord record = newRecord();
		
		record.serialize(context);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetFieldValueNullFieldName() 
	{
		MapRecord record = newRecord();
		
		record.getFieldValue(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetFieldValueNullUnrecorgnizedFieldName() 
	{
		MapRecord record = newRecord();
		
		record.getFieldValue("Foo");
	}

	@Test
	public void testGetFieldValue() 
	{
		MapRecord record = newRecord();
		
		assertEquals(FIELD1_VALUE, record.getFieldValue(FIELD1));
	}
	
	
	//
	//	Private/helper methods
	//
	private MapRecord newRecord()
	{
		FieldInstance[] fieldInstances = new FieldInstance[3];
		
		fieldInstances[0] = new FieldInstance(FIELD1, FieldType.categorical, FIELD1_VALUE);
		fieldInstances[1] = new FieldInstance(FIELD2, FieldType.categorical, FIELD2_VALUE);
		fieldInstances[2] = new FieldInstance(FIELD3, FieldType.categorical, FIELD3_VALUE);
		
		return new MapRecord(fieldInstances, MapInterpreter.DELIMITER);
	}
}
