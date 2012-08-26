/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.junit.Test;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;

//
//	Unit tests for the org.trommel.trommel.MapRecord class
//
public class MapRecordTests 
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
	private static final String DELIMITER = "*|*";

	//
	//	Tests
	//
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNulLFieldsArray()
	{
		@SuppressWarnings("unused")
		MapRecord record = new MapRecord(null, DELIMITER);
	}
		
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorEmptyFieldsArray()
	{
		@SuppressWarnings("unused")
		MapRecord record = new MapRecord(new Field[0], DELIMITER);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorEmptyDelimiter()
	{
		Field[] fields = new Field[1];
		
		fields[0] = new Field(FIELD1, FieldType.categorical);
		
		@SuppressWarnings("unused")
		MapRecord record = new MapRecord(fields, " ");
	}
	
	@Test
	public void testConstructorDelimiter()
	{
		Field[] fields = new Field[1];
		
		fields[0] = new Field(FIELD1, FieldType.categorical);
		
		@SuppressWarnings("unused")
		MapRecord record = new MapRecord(fields, "*|*");
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
		throws IllegalArgumentException, IOException
	{
		// Placeholder as Record delegates to contained OutputSet instance
		MapRecord record = newRecord();
		
		record.serialize(new MockOutputCollector<Text, Text>());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetFieldTypeNullFieldName() 
	{
		MapRecord record = newRecord();
		
		record.getFieldType(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetFieldTypeNullUnrecorgnizedFieldName() 
	{
		MapRecord record = newRecord();
		
		record.getFieldType("Foo");
	}

	@Test
	public void testGetFieldType() 
	{
		MapRecord record = newRecord();
		
		assertEquals(FieldType.categorical, record.getFieldType(FIELD1));
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
		Field[] fields = new Field[3];
		
		fields[0] = new Field(FIELD1, FieldType.categorical, FIELD1_VALUE);
		fields[1] = new Field(FIELD2, FieldType.categorical, FIELD2_VALUE);
		fields[2] = new Field(FIELD3, FieldType.categorical, FIELD3_VALUE);
		
		return new MapRecord(fields, DELIMITER);
	}
}
