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
import org.trommel.trommel.functions.Max;
import org.trommel.trommel.tests.MockOutputCollector;

//
//	Unit tests for the org.trommel.trommel.functions.Max class
//
public class MaxTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String DELIMITER = "*|*";
	private static final String FUNCTION_NAME = "Max";
	
	// First row fields and values
	private static final String FIELD1 = "Field1";
	private static final String FIELD2 = "Field2";
	private static final String FIELD3 = "Field3";
	private static final String FIELD1_VALUE = "15.0";
	private static final String FIELD2_VALUE = "20.0";
	private static final String FIELD3_VALUE = "30.0";

	//	Second row values
	private static final String FIELD4_VALUE = "10.0";
	private static final String FIELD5_VALUE = "25.0";
	private static final String FIELD6_VALUE = "35.0";

	//	Third row values
	private static final String FIELD7_VALUE = "11.0";
	private static final String FIELD8_VALUE = "22.0";
	private static final String FIELD9_VALUE = "39.0";

	
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
		Max max = new Max(fieldNames);	
	}
	
	@Test
	public void testGetHandlerName()
	{
		Max max = new Max(fieldNames);
		
		assertEquals(FUNCTION_NAME, max.getHandlerName());
	}

	@Test
	public void testHandleMapRecord() 
		throws IOException
	{
		MapRecord[] records = mapRecords();
		Max max = new Max(fieldNames);
		MockOutputCollector<Text, Text> outputCollector = new MockOutputCollector<Text, Text>();
		String prefix = max.getHandlerName() + "=";
		
		max.handleMapRecord(records[0]);
		
		records[0].serialize(outputCollector);
		
		assertEquals(FIELD3, outputCollector.getKeys().get(0).toString());
		assertEquals(FIELD2, outputCollector.getKeys().get(1).toString());
		assertEquals(FIELD1, outputCollector.getKeys().get(2).toString());
		assertEquals(prefix + FIELD3_VALUE, outputCollector.getValues().get(0).toString());
		assertEquals(prefix + FIELD2_VALUE, outputCollector.getValues().get(1).toString());
		assertEquals(prefix + FIELD1_VALUE, outputCollector.getValues().get(2).toString());

		max.handleMapRecord(records[1]);
		
		outputCollector = new MockOutputCollector<Text, Text>();
		
		records[1].serialize(outputCollector);
		
		assertEquals(FIELD3, outputCollector.getKeys().get(0).toString());
		assertEquals(FIELD2, outputCollector.getKeys().get(1).toString());
		assertEquals(FIELD1, outputCollector.getKeys().get(2).toString());
		assertEquals(prefix + FIELD6_VALUE, outputCollector.getValues().get(0).toString());
		assertEquals(prefix + FIELD5_VALUE, outputCollector.getValues().get(1).toString());
		assertEquals(prefix + FIELD4_VALUE, outputCollector.getValues().get(2).toString());
	}

	@Test
	public void testGetReduceResult() 
	{
		List<HashMap<String,String>> records = reduceRecords();
		Max max = new Max(fieldNames);
		
		for (HashMap<String,String> record : records)
		{
			max.handleReduceRecord(record);
		}
		
		assertEquals(FIELD1_VALUE, max.getReduceResult());
	}

	//
	//	Private/helper methods
	//	
	private MapRecord[] mapRecords()
	{
		MapRecord[] mapRecords = new MapRecord[3];
		Field[] fields = new Field[3];
		
		// First MapRecord
		fields[0] = new Field(FIELD1, FieldType.numeric, FIELD1_VALUE);
		fields[1] = new Field(FIELD2, FieldType.numeric, FIELD2_VALUE);
		fields[2] = new Field(FIELD3, FieldType.numeric, FIELD3_VALUE);
		
		mapRecords[0] = new MapRecord(fields, DELIMITER);
		
		// Second MapRecord
		fields[0] = new Field(FIELD1, FieldType.numeric, FIELD4_VALUE);
		fields[1] = new Field(FIELD2, FieldType.numeric, FIELD5_VALUE);
		fields[2] = new Field(FIELD3, FieldType.numeric, FIELD6_VALUE);
		
		mapRecords[1] = new MapRecord(fields, DELIMITER);
		
		// Third MapRecord
		fields[0] = new Field(FIELD1, FieldType.numeric, FIELD7_VALUE);
		fields[1] = new Field(FIELD2, FieldType.numeric, FIELD8_VALUE);
		fields[2] = new Field(FIELD3, FieldType.numeric, FIELD9_VALUE);
		
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
		records.add(1, new HashMap<String, String>());
		
		records.get(1).put(FUNCTION_NAME, FIELD4_VALUE);

		// Third Record
		records.add(2, new HashMap<String, String>());
		
		records.get(2).put(FUNCTION_NAME, FIELD7_VALUE);
		
		return records;
	}	
}
