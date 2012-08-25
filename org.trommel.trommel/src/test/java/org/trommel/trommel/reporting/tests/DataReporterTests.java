/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.reporting.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.Text;
import org.junit.Test;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.reporting.DataReporter;
import org.trommel.trommel.tests.MockOutputCollector;

//
//	Unit tests for the org.trommel.trommel.reporting.DataReporter class
//
public class DataReporterTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String DELIMITER = "*|*";
	private static final String FIELD1 = "Field1";
	private static final String FIELD2 = "Field2";
	private static final String FIELD3 = "Field3";
	private static final String FIELD1_VALUE1 = "Value1";
	private static final String FIELD2_VALUE1 = "Value4";
	private static final String FIELD3_VALUE1 = "Value5";

	
	//
	//	Tests
	//

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullField() 
	{
		@SuppressWarnings("unused")
		DataReporter reporter = new DataReporter(null);
	}
	
	@Test
	public void testHandleMapRecord() 
		throws IOException
	{
		MapRecord record = mapRecord();
		DataReporter reporter = new DataReporter(FIELD1);
		MockOutputCollector<Text, Text> outputCollector = new MockOutputCollector<Text, Text>();
		String prefix = reporter.getHandlerName() + "=";
		
		reporter.handleMapRecord(record);
		
		record.serialize(outputCollector);
		
		assertEquals(1, outputCollector.getKeys().size());
		assertEquals(1, outputCollector.getValues().size());
		assertEquals(FIELD1, outputCollector.getKeys().get(0).toString());
		assertEquals(prefix + FIELD1_VALUE1, outputCollector.getValues().get(0).toString());
	}
	
	@Test
	public void testGetReduceResult() 
	{
		DataReporter reporter = new DataReporter(FIELD1);
		String header = "FIELD\tCONTENT\tCOUNT\n";
		String line1 = FIELD1 + "\t" + FIELD1_VALUE1 + "\t3\n";
		
		reporter.handleReduceRecord(reduceRecord());
		reporter.handleReduceRecord(reduceRecord());
		reporter.handleReduceRecord(reduceRecord());

		String reduceValue = reporter.getReduceResult();
		
		assertTrue(reduceValue.contains(header));
		assertTrue(reduceValue.contains(line1));
	}


	//
	//	Private/helper methods
	//	
	private MapRecord mapRecord()
	{
		Field[] fields = new Field[3];
		
		fields[0] = new Field(FIELD1, FieldType.categorical, FIELD1_VALUE1);
		fields[1] = new Field(FIELD2, FieldType.categorical, FIELD2_VALUE1);
		fields[2] = new Field(FIELD3, FieldType.categorical, FIELD3_VALUE1);
		
		return new MapRecord(fields, DELIMITER);
	}
	
	private HashMap<String, String> reduceRecord()
	{
		HashMap<String, String> record = new HashMap<String, String>();

		record.put("Foo", "FooValue");
		record.put("Bar", "BarValue");
		record.put("DataReporter", FIELD1_VALUE1);
		record.put("FooBar", "FooBarValue");
		
		return record;
	}
}
