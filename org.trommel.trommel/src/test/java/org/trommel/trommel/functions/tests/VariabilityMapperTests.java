/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.junit.BeforeClass;
import org.junit.Test;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldInstance;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.functions.VariabilityMapper;
import org.trommel.trommel.tests.MockOutputCollector;

//
//	Unit tests for the org.trommel.trommel.functions.VariabilityMapper class
//
public class VariabilityMapperTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String DELIMITER = "*|*";
	private static final String FUNCTION_NAME = "Variability";
	private static final String NULL_INDICATOR = "null";
	
	//	Field names
	private static final String FIELD1 = "Field1";
	private static final String FIELD2 = "Field2";
	private static final String FIELD3 = "Field3";

	// First row numeric values
	private static final String NUMERIC_FIELD1_VALUE = "15.0";
	private static final String NUMERIC_FIELD1_VALUE_OUTPUT = "15.0:225.0";
	private static final String NUMERIC_FIELD2_VALUE = "20.0";
	private static final String NUMERIC_FIELD2_VALUE_OUTPUT = "20.0:400.0";
	private static final String NUMERIC_FIELD3_VALUE = "";
	private static final String NUMERIC_FIELD3_VALUE_OUTPUT = "0:0";

	//	Second row numeric values
	private static final String FIELD4_VALUE = "10.0";
	private static final String FIELD5_VALUE = "25.0";
	private static final String FIELD6_VALUE = "35.0";

	//	Third row numeric values
	private static final String FIELD7_VALUE = "11.0";
	private static final String FIELD8_VALUE = "22.0";
	private static final String FIELD9_VALUE = "39.0";

	
	//
	//	Private members
	//
	private static Field[] numericFields = null;	
	private static Field[] categoricalFields = null;	
	

	//
	//	Setup/Tear-down
	//
	
	@BeforeClass
	public static void initialization()
	{
		numericFields = new Field[3];		
		numericFields[0] = new Field(FIELD1, FieldType.numeric);
		numericFields[1] = new Field(FIELD2, FieldType.numeric);
		numericFields[2] = new Field(FIELD3, FieldType.numeric);

	
		categoricalFields = new Field[3];
		categoricalFields[0] = new Field(FIELD1, FieldType.categorical);
		categoricalFields[1] = new Field(FIELD2, FieldType.categorical);
		categoricalFields[2] = new Field(FIELD3, FieldType.categorical);
}
	
	
	//
	//	Tests
	//
		
	@Test
	public void testConstructorOK() 
	{
		@SuppressWarnings("unused")
		VariabilityMapper var = new VariabilityMapper(numericFields);	
	}
	
	@Test
	public void testGetHandlerName()
	{
		VariabilityMapper var = new VariabilityMapper(numericFields);
		
		assertEquals(FUNCTION_NAME, var.getHandlerName());
	}

	@Test
	public void testNumericHandleMapRecord() 
		throws IOException
	{
		MapRecord[] records = numericMapRecords();
		VariabilityMapper var = new VariabilityMapper(numericFields);
		MockOutputCollector<Text, Text> outputCollector = new MockOutputCollector<Text, Text>();
		String prefix = var.getHandlerName() + "=";
		
		var.handleMapRecord(records[0]);
		
		records[0].serialize(outputCollector);
		
		assertEquals(FIELD3, outputCollector.getKeys().get(0).toString());
		assertEquals(FIELD2, outputCollector.getKeys().get(1).toString());
		assertEquals(FIELD1, outputCollector.getKeys().get(2).toString());
		assertEquals(prefix + NUMERIC_FIELD3_VALUE_OUTPUT, outputCollector.getValues().get(0).toString());
		assertEquals(prefix + NUMERIC_FIELD2_VALUE_OUTPUT, outputCollector.getValues().get(1).toString());
		assertEquals(prefix + NUMERIC_FIELD1_VALUE_OUTPUT, outputCollector.getValues().get(2).toString());

		var.handleMapRecord(records[1]);
		
		outputCollector = new MockOutputCollector<Text, Text>();
		
		records[1].serialize(outputCollector);
		
		assertEquals(3, outputCollector.getKeys().size());
		assertEquals(3, outputCollector.getValues().size());		
		
		var.handleMapRecord(records[2]);

		outputCollector = new MockOutputCollector<Text, Text>();

		records[2].serialize(outputCollector);
		
		assertEquals(3, outputCollector.getKeys().size());
		assertEquals(3, outputCollector.getValues().size());		
	}

	@Test
	public void testCategoricalHandleMapRecord() 
		throws IOException
	{
		MapRecord[] records = categoricalMapRecords();
		VariabilityMapper var = new VariabilityMapper(categoricalFields);
		MockOutputCollector<Text, Text> outputCollector = new MockOutputCollector<Text, Text>();
		String prefix = var.getHandlerName() + "=";
		
		var.handleMapRecord(records[0]);
		
		records[0].serialize(outputCollector);
		
		assertEquals(FIELD3, outputCollector.getKeys().get(0).toString());
		assertEquals(FIELD2, outputCollector.getKeys().get(1).toString());
		assertEquals(FIELD1, outputCollector.getKeys().get(2).toString());
		assertEquals(prefix + NULL_INDICATOR, outputCollector.getValues().get(0).toString());
		assertEquals(prefix + FIELD2, outputCollector.getValues().get(1).toString());
		assertEquals(prefix + FIELD1, outputCollector.getValues().get(2).toString());

		var.handleMapRecord(records[1]);
		
		outputCollector = new MockOutputCollector<Text, Text>();
		
		records[1].serialize(outputCollector);
		
		assertEquals(3, outputCollector.getKeys().size());
		assertEquals(3, outputCollector.getValues().size());		
		
		var.handleMapRecord(records[2]);

		outputCollector = new MockOutputCollector<Text, Text>();

		records[2].serialize(outputCollector);
		
		assertEquals(3, outputCollector.getKeys().size());
		assertEquals(3, outputCollector.getValues().size());		
	}

	
	//
	//	Private/helper methods
	//	

	private MapRecord[] numericMapRecords()
	{
		MapRecord[] mapRecords = new MapRecord[3];
		FieldInstance[] fieldInstances = new FieldInstance[3];
		
		// First MapRecord
		fieldInstances[0] = new FieldInstance(FIELD1, FieldType.numeric, NUMERIC_FIELD1_VALUE);
		fieldInstances[1] = new FieldInstance(FIELD2, FieldType.numeric, NUMERIC_FIELD2_VALUE);
		fieldInstances[2] = new FieldInstance(FIELD3, FieldType.numeric, NUMERIC_FIELD3_VALUE);
		
		mapRecords[0] = new MapRecord(fieldInstances, DELIMITER);
		
		// Second MapRecord
		fieldInstances[0] = new FieldInstance(FIELD1, FieldType.numeric, FIELD4_VALUE);
		fieldInstances[1] = new FieldInstance(FIELD2, FieldType.numeric, FIELD5_VALUE);
		fieldInstances[2] = new FieldInstance(FIELD3, FieldType.numeric, FIELD6_VALUE);
		
		mapRecords[1] = new MapRecord(fieldInstances, DELIMITER);
		
		// Third MapRecord
		fieldInstances[0] = new FieldInstance(FIELD1, FieldType.numeric, FIELD7_VALUE);
		fieldInstances[1] = new FieldInstance(FIELD2, FieldType.numeric, FIELD8_VALUE);
		fieldInstances[2] = new FieldInstance(FIELD3, FieldType.numeric, FIELD9_VALUE);
		
		mapRecords[2] = new MapRecord(fieldInstances, DELIMITER);
		
		return mapRecords;
	}

	private MapRecord[] categoricalMapRecords()
	{
		MapRecord[] mapRecords = new MapRecord[3];
		FieldInstance[] fieldInstances = new FieldInstance[3];
		
		// First MapRecord
		fieldInstances[0] = new FieldInstance(FIELD1, FieldType.categorical, FIELD1);
		fieldInstances[1] = new FieldInstance(FIELD2, FieldType.categorical, FIELD2);
		fieldInstances[2] = new FieldInstance(FIELD3, FieldType.categorical, "");
		
		mapRecords[0] = new MapRecord(fieldInstances, DELIMITER);
		
		// Second MapRecord
		fieldInstances[0] = new FieldInstance(FIELD1, FieldType.categorical, FIELD1);
		fieldInstances[1] = new FieldInstance(FIELD2, FieldType.categorical, FIELD2);
		fieldInstances[2] = new FieldInstance(FIELD3, FieldType.categorical, FIELD3);
		
		mapRecords[1] = new MapRecord(fieldInstances, DELIMITER);
		
		// Third MapRecord
		fieldInstances[0] = new FieldInstance(FIELD1, FieldType.categorical, FIELD1);
		fieldInstances[1] = new FieldInstance(FIELD2, FieldType.categorical, FIELD2);
		fieldInstances[2] = new FieldInstance(FIELD3, FieldType.categorical, FIELD3);
		
		mapRecords[2] = new MapRecord(fieldInstances, DELIMITER);
		
		return mapRecords;
	}
}