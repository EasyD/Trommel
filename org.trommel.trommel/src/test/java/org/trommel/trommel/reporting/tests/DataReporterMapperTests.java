/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.reporting.tests;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.MapContext;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.mockito.Mockito;
import org.trommel.trommel.FieldInstance;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.reporting.DataReporterMapper;


//
//	Unit tests for the org.trommel.trommel.reporting.DataReporterMapper class
//
public class DataReporterMapperTests 
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
		Logger logger = Mockito.mock(Logger.class);
		@SuppressWarnings("unused")
		DataReporterMapper reporter = new DataReporterMapper(logger, null);
	}
	
	@Test
	public void testHandleMapRecord() 
		throws IOException, InterruptedException
	{
		@SuppressWarnings("unchecked")
		MapContext<LongWritable, Text, Text, Text> context = Mockito.mock(MapContext.class);
		MapRecord record = mapRecord();
		Logger logger = Mockito.mock(Logger.class);
		DataReporterMapper reporter = new DataReporterMapper(logger, FIELD1);
		String prefix = reporter.getHandlerName() + "=";
		
		reporter.handleMapRecord(record);
		
		record.serialize(context);
		
		Mockito.verify(context).write(new Text(FIELD1), new Text(prefix + FIELD1_VALUE1));
	}


	//
	//	Private/helper methods
	//	
	private MapRecord mapRecord()
	{
		FieldInstance[] fieldInstances = new FieldInstance[3];
		
		fieldInstances[0] = new FieldInstance(FIELD1, FieldType.categorical, FIELD1_VALUE1);
		fieldInstances[1] = new FieldInstance(FIELD2, FieldType.categorical, FIELD2_VALUE1);
		fieldInstances[2] = new FieldInstance(FIELD3, FieldType.categorical, FIELD3_VALUE1);
		
		return new MapRecord(fieldInstances, DELIMITER);
	}
}
