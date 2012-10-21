/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.reporting.tests;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.MapContext;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldInstance;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.reporting.DataReporterMapper;
import org.trommel.trommel.scripting.interpreters.MapInterpreter;


//
//	Unit tests for the org.trommel.trommel.reporting.DataReporterMapper class
//
@SuppressWarnings("unused")
public class DataReporterMapperTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	
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

	
	//
	//	Private members
	//
	private Field[] fields = null;
	private Logger logger = null;
	

	//
	//	Setup/Tear-down
	//
	
	@Before
	public void initialization()
	{
		fields = new Field[3];
		
		fields[0] = new Field(FIELD1, FieldType.numeric);
		fields[1] = new Field(FIELD2, FieldType.numeric);
		fields[2] = new Field(FIELD3, FieldType.numeric);
		
		logger = Mockito.mock(Logger.class);
		
		Mockito.when(logger.isDebugEnabled()).thenReturn(true);
	}
	
	
	//
	//	Tests
	//

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullFieldArray() 
	{
		
		DataReporterMapper reporter = new DataReporterMapper(logger, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorEmptyFieldArray() 
	{
		
		DataReporterMapper reporter = new DataReporterMapper(logger, new Field[0]);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorEmptyFieldArrayNullElement() 
	{
		
		DataReporterMapper reporter = new DataReporterMapper(logger, new Field[3]);
	}

	@Test
	public void testHandleMapRecord() 
		throws IOException, InterruptedException
	{
		
		@SuppressWarnings("unchecked")
		MapContext<LongWritable, Text, Text, Text> context = Mockito.mock(MapContext.class);
		MapRecord[] records = mapRecords();
		DataReporterMapper reporter = new DataReporterMapper(logger, fields);
		String prefix = reporter.getHandlerName() + "=";
		
		// Verify first record
		reporter.handleMapRecord(records[0]);
		
		records[0].serialize(context);
		
		Mockito.verify(context).write(new Text(FIELD1), new Text(prefix + FIELD1_VALUE));
		Mockito.verify(context).write(new Text(FIELD2), new Text(prefix + FIELD2_VALUE));
		Mockito.verify(context).write(new Text(FIELD3), new Text(prefix + FIELD3_VALUE));

		// Verify second record
		reporter.handleMapRecord(records[1]);
		
		records[1].serialize(context);
		
		Mockito.verify(context).write(new Text(FIELD1), new Text(prefix + FIELD4_VALUE));
		Mockito.verify(context).write(new Text(FIELD2), new Text(prefix + FIELD5_VALUE));
		Mockito.verify(context).write(new Text(FIELD3), new Text(prefix + FIELD6_VALUE));
	}


	//
	//	Private/helper methods
	//	

	private MapRecord[] mapRecords()
	{
		MapRecord[] mapRecords = new MapRecord[3];
		FieldInstance[] fieldInstances = new FieldInstance[3];
		
		// First MapRecord
		fieldInstances[0] = new FieldInstance(FIELD1, FieldType.numeric, FIELD1_VALUE);
		fieldInstances[1] = new FieldInstance(FIELD2, FieldType.numeric, FIELD2_VALUE);
		fieldInstances[2] = new FieldInstance(FIELD3, FieldType.numeric, FIELD3_VALUE);
		
		mapRecords[0] = new MapRecord(fieldInstances, MapInterpreter.DELIMITER);
		
		// Second MapRecord
		fieldInstances[0] = new FieldInstance(FIELD1, FieldType.numeric, FIELD4_VALUE);
		fieldInstances[1] = new FieldInstance(FIELD2, FieldType.numeric, FIELD5_VALUE);
		fieldInstances[2] = new FieldInstance(FIELD3, FieldType.numeric, FIELD6_VALUE);
		
		mapRecords[1] = new MapRecord(fieldInstances, MapInterpreter.DELIMITER);
		
		return mapRecords;
	}
}
