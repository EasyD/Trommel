/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.mockito.Mockito;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.MapContext;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldInstance;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.functions.VariabilityMapper;
import org.trommel.trommel.scripting.interpreters.MapInterpreter;


//
//	Unit tests for the org.trommel.trommel.functions.VariabilityMapper class
//
@SuppressWarnings("unused")
public class VariabilityMapperTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String HANDLER_NAME = "Variability";
	private static final String NULL_INDICATOR = "null";
	
	//	Field names
	private static final String FIELD1 = "Field1";
	private static final String FIELD2 = "Field2";
	private static final String FIELD3 = "Field3";
	private static final String FIELD4 = "Field4";
	private static final String FIELD5 = "Field5";
	private static final String FIELD6 = "Field6";

	// First row numeric values
	private static final String NUMERIC_FIELD1_VALUE = "15.0";
	private static final String NUMERIC_FIELD2_VALUE = "20.0";
	private static final String NUMERIC_FIELD3_VALUE = "";
	private static final String NUMERIC_FIELD3_VALUE_OUTPUT = "0";

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
	private Field[] numericFields = null;	
	private Field[] categoricalFields = null;	
	private Logger logger = null;
	

	//
	//	Setup/Tear-down
	//
	
	@Before
	public void initialization()
	{
		numericFields = new Field[3];		
		numericFields[0] = new Field(FIELD1, FieldType.numeric);
		numericFields[1] = new Field(FIELD2, FieldType.numeric);
		numericFields[2] = new Field(FIELD3, FieldType.numeric);

	
		categoricalFields = new Field[3];
		categoricalFields[0] = new Field(FIELD1, FieldType.categorical);
		categoricalFields[1] = new Field(FIELD2, FieldType.categorical);
		categoricalFields[2] = new Field(FIELD3, FieldType.categorical);

		logger = Mockito.mock(Logger.class);
		
		Mockito.when(logger.isDebugEnabled()).thenReturn(true);
	}
	
	
	//
	//	Tests
	//
		
	@Test
	public void testConstructorOK() 
	{
		VariabilityMapper var = new VariabilityMapper(logger, numericFields);	
	}
	
	@Test
	public void testGetHandlerName()
	{
		VariabilityMapper var = new VariabilityMapper(logger, numericFields);
		
		assertEquals(HANDLER_NAME, var.getHandlerName());
	}

	@Test
	public void testNumericHandleMapRecord() 
		throws IOException, InterruptedException
	{
		
		@SuppressWarnings("unchecked")
		MapContext<LongWritable, Text, Text, Text> context = Mockito.mock(MapContext.class);
		MapRecord[] records = numericMapRecords();
		VariabilityMapper var = new VariabilityMapper(logger, numericFields);
		String prefix = var.getHandlerName() + "=";
		
		var.handleMapRecord(records[0]);
		
		records[0].serialize(context);
		
		Mockito.verify(context).write(new Text(FIELD1), new Text(prefix + NUMERIC_FIELD1_VALUE));
		Mockito.verify(context).write(new Text(FIELD2), new Text(prefix + NUMERIC_FIELD2_VALUE));
		Mockito.verify(context).write(new Text(FIELD3), new Text(prefix + NUMERIC_FIELD3_VALUE_OUTPUT));

		var.handleMapRecord(records[1]);
		
		records[1].serialize(context);
		
		Mockito.verify(context).write(new Text(FIELD1), new Text(prefix + FIELD4_VALUE));
		Mockito.verify(context).write(new Text(FIELD2), new Text(prefix + FIELD5_VALUE));
		Mockito.verify(context).write(new Text(FIELD3), new Text(prefix + FIELD6_VALUE));
		
		var.handleMapRecord(records[2]);

		records[2].serialize(context);
		
		Mockito.verify(context).write(new Text(FIELD1), new Text(prefix + FIELD7_VALUE));
		Mockito.verify(context).write(new Text(FIELD2), new Text(prefix + FIELD8_VALUE));
		Mockito.verify(context).write(new Text(FIELD3), new Text(prefix + FIELD9_VALUE));
	}

	@Test
	public void testCategoricalHandleMapRecord() 
		throws IOException, InterruptedException
	{
		
		@SuppressWarnings("unchecked")
		MapContext<LongWritable, Text, Text, Text> context = Mockito.mock(MapContext.class);
		MapRecord[] records = categoricalMapRecords();
		VariabilityMapper var = new VariabilityMapper(logger, categoricalFields);
		String prefix = var.getHandlerName() + "=";
		
		var.handleMapRecord(records[0]);
		
		records[0].serialize(context);
		
		Mockito.verify(context).write(new Text(FIELD1), new Text(prefix + FIELD1));
		Mockito.verify(context).write(new Text(FIELD2), new Text(prefix + FIELD2));
		Mockito.verify(context).write(new Text(FIELD3), new Text(prefix + NULL_INDICATOR));

		var.handleMapRecord(records[1]);
		
		records[1].serialize(context);
		
		Mockito.verify(context).write(new Text(FIELD1), new Text(prefix + FIELD4));
		Mockito.verify(context).write(new Text(FIELD2), new Text(prefix + FIELD5));
		Mockito.verify(context).write(new Text(FIELD3), new Text(prefix + FIELD6));
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
		
		mapRecords[0] = new MapRecord(fieldInstances, MapInterpreter.DELIMITER);
		
		// Second MapRecord
		fieldInstances[0] = new FieldInstance(FIELD1, FieldType.numeric, FIELD4_VALUE);
		fieldInstances[1] = new FieldInstance(FIELD2, FieldType.numeric, FIELD5_VALUE);
		fieldInstances[2] = new FieldInstance(FIELD3, FieldType.numeric, FIELD6_VALUE);
		
		mapRecords[1] = new MapRecord(fieldInstances, MapInterpreter.DELIMITER);
		
		// Third MapRecord
		fieldInstances[0] = new FieldInstance(FIELD1, FieldType.numeric, FIELD7_VALUE);
		fieldInstances[1] = new FieldInstance(FIELD2, FieldType.numeric, FIELD8_VALUE);
		fieldInstances[2] = new FieldInstance(FIELD3, FieldType.numeric, FIELD9_VALUE);
		
		mapRecords[2] = new MapRecord(fieldInstances, MapInterpreter.DELIMITER);
		
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
		
		mapRecords[0] = new MapRecord(fieldInstances, MapInterpreter.DELIMITER);
		
		// Second MapRecord
		fieldInstances[0] = new FieldInstance(FIELD1, FieldType.categorical, FIELD4);
		fieldInstances[1] = new FieldInstance(FIELD2, FieldType.categorical, FIELD5);
		fieldInstances[2] = new FieldInstance(FIELD3, FieldType.categorical, FIELD6);
		
		mapRecords[1] = new MapRecord(fieldInstances, MapInterpreter.DELIMITER);
		
		return mapRecords;
	}
}
