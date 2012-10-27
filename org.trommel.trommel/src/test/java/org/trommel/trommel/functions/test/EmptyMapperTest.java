/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions.test;

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
import org.trommel.trommel.functions.EmptyMapper;
import org.trommel.trommel.scripting.interpreters.MapInterpreter;


//
//	Unit tests for the org.trommel.trommel.functions.EmptyMapper class
//
public class EmptyMapperTest 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String HANDLER_NAME = "Empty";
	
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

	@Test
	public void testConstructorOK() 
	{
		@SuppressWarnings("unused")
		EmptyMapper empty = new EmptyMapper(logger, fields);	
	}
	
	@Test
	public void testGetHandlerName()
	{
		EmptyMapper empty = new EmptyMapper(logger, fields);
		
		assertEquals(HANDLER_NAME, empty.getHandlerName());
	}
	
	
	@Test
	public void testHandleMapRecord() 
		throws IOException, InterruptedException
	{
		
		@SuppressWarnings("unchecked")
		MapContext<LongWritable, Text, Text, Text> context = Mockito.mock(MapContext.class);
		MapRecord[] records = mapRecords();
		EmptyMapper empty = new EmptyMapper(logger, fields);
		String prefix = empty.getHandlerName() + "=";
		
		empty.handleMapRecord(records[0]);
		
		records[0].serialize(context);
		
		Mockito.verify(context).write(new Text(FIELD2), new Text(prefix + "1"));

		empty.handleMapRecord(records[1]);
		
		records[1].serialize(context);
		
		Mockito.verify(context).write(new Text(FIELD3), new Text(prefix + "1"));

		empty.handleMapRecord(records[2]);
		
		records[2].serialize(context);
		
		Mockito.verify(context).write(new Text(FIELD1), new Text(prefix + "1"));
	}

	
	//
	//	Private/helper methods
	//	
	
	private MapRecord[] mapRecords()
	{
		MapRecord[] mapRecords = new MapRecord[3];
		FieldInstance[] fieldIntances = new FieldInstance[3];
		
		// First MapRecord
		fieldIntances[0] = new FieldInstance(FIELD1, FieldType.categorical, FIELD1_VALUE);
		fieldIntances[1] = new FieldInstance(FIELD2, FieldType.categorical, FIELD2_VALUE);
		fieldIntances[2] = new FieldInstance(FIELD3, FieldType.categorical, FIELD3_VALUE);
		
		mapRecords[0] = new MapRecord(fieldIntances, MapInterpreter.DELIMITER);
		
		// Second MapRecord
		fieldIntances[0] = new FieldInstance(FIELD1, FieldType.categorical, FIELD4_VALUE);
		fieldIntances[1] = new FieldInstance(FIELD2, FieldType.categorical, FIELD5_VALUE);
		fieldIntances[2] = new FieldInstance(FIELD3, FieldType.categorical, FIELD6_VALUE);
		
		mapRecords[1] = new MapRecord(fieldIntances, MapInterpreter.DELIMITER);
		
		// Third MapRecord
		fieldIntances[0] = new FieldInstance(FIELD1, FieldType.categorical, FIELD7_VALUE);
		fieldIntances[1] = new FieldInstance(FIELD2, FieldType.categorical, FIELD8_VALUE);
		fieldIntances[2] = new FieldInstance(FIELD3, FieldType.categorical, FIELD9_VALUE);
		
		mapRecords[2] = new MapRecord(fieldIntances, MapInterpreter.DELIMITER);
		
		return mapRecords;
	}
}