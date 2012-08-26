/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.junit.Test;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.functions.Empty;
import org.trommel.trommel.tests.MockOutputCollector;

//
//	Unit tests for the org.trommel.trommel.functions.Empty class
//
public class EmptyTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String DELIMITER = "*|*";
	private static final String FUNCTION_NAME = "Empty";
	
	// First row fields and values
	private static final String FIELD1 = "Field1";
	private static final String FIELD2 = "Field2";
	private static final String FIELD3 = "Field3";
	private static final String FIELD1_VALUE = "15.0";
	private static final String FIELD2_VALUE = " ";
	private static final String FIELD3_VALUE = "30.0";

	//	Second row values
	private static final String FIELD4_VALUE = "10.0";
	private static final String FIELD5_VALUE = "25.0";
	private static final String FIELD6_VALUE = " \t ";

	//	Third row values
	private static final String FIELD7_VALUE = "\t";
	private static final String FIELD8_VALUE = "20.0";
	private static final String FIELD9_VALUE = "30.0";

	//
	//	Private members
	//
	String[] fieldNames = {FIELD1, FIELD2, FIELD3};	
	
	
	//
	//	Tests
	//
		
	@Test
	public void testConstructorOK() 
	{
		@SuppressWarnings("unused")
		Empty empty = new Empty(fieldNames);	
	}
	
	@Test
	public void testGetHandlerName()
	{
		Empty empty = new Empty(fieldNames);
		
		assertEquals(FUNCTION_NAME, empty.getHandlerName());
	}
	

	@Test
	public void testHandleMapRecord() 
		throws IOException
	{
		MapRecord[] records = mapRecords();
		Empty empty = new Empty(fieldNames);
		MockOutputCollector<Text, Text> outputCollector = new MockOutputCollector<Text, Text>();
		
		empty.handleMapRecord(records[0]);
		
		records[0].serialize(outputCollector);
		
		assertEquals(1, outputCollector.getKeys().size());
		assertEquals(1, outputCollector.getValues().size());

		empty.handleMapRecord(records[1]);
		
		records[1].serialize(outputCollector);
		
		assertEquals(2, outputCollector.getKeys().size());
		assertEquals(2, outputCollector.getValues().size());

		empty.handleMapRecord(records[2]);
		
		records[2].serialize(outputCollector);
		
		assertEquals(3, outputCollector.getKeys().size());
		assertEquals(3, outputCollector.getValues().size());
	}

	@Test
	public void testGetReduceResult() 
	{
		List<HashMap<String,String>> records = reduceRecords();
		Empty empty = new Empty(fieldNames);
		
		for (HashMap<String, String> record : records)
		{
			empty.handleReduceRecord(record);
		}
		
		assertEquals("2", empty.getReduceResult());
	}


	//
	//	Private/helper methods
	//	
	private MapRecord[] mapRecords()
	{
		MapRecord[] mapRecords = new MapRecord[3];
		Field[] fields = new Field[3];
		
		// First MapRecord
		fields[0] = new Field(FIELD1, FieldType.categorical, FIELD1_VALUE);
		fields[1] = new Field(FIELD2, FieldType.categorical, FIELD2_VALUE);
		fields[2] = new Field(FIELD3, FieldType.categorical, FIELD3_VALUE);
		
		mapRecords[0] = new MapRecord(fields, DELIMITER);
		
		// Second MapRecord
		fields[0] = new Field(FIELD1, FieldType.categorical, FIELD4_VALUE);
		fields[1] = new Field(FIELD2, FieldType.categorical, FIELD5_VALUE);
		fields[2] = new Field(FIELD3, FieldType.categorical, FIELD6_VALUE);
		
		mapRecords[1] = new MapRecord(fields, DELIMITER);
		
		// Third MapRecord
		fields[0] = new Field(FIELD1, FieldType.categorical, FIELD7_VALUE);
		fields[1] = new Field(FIELD2, FieldType.categorical, FIELD8_VALUE);
		fields[2] = new Field(FIELD3, FieldType.categorical, FIELD9_VALUE);
		
		mapRecords[2] = new MapRecord(fields, DELIMITER);
		
		return mapRecords;
	}
	
	private List<HashMap<String,String>> reduceRecords()
	{
		ArrayList<HashMap<String,String>> records = new ArrayList<HashMap<String, String>>();
		
		// First Record
		records.add(0, new HashMap<String, String>());
		
		records.get(0).put(FUNCTION_NAME, FIELD1_VALUE);
				
		// Second Record
		records.add(0, new HashMap<String, String>());
		
		records.get(0).put("Foo", "-1.0");
				
		// Third Record
		records.add(2, new HashMap<String, String>());
		
		records.get(2).put(FUNCTION_NAME, FIELD1_VALUE);
		
		return records;
	}	
}
