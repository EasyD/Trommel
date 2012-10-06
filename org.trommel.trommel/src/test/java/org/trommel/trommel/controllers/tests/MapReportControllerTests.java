/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.controllers.tests;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.controllers.MapReportController;

//
//	Unit tests for the org.trommel.trommel.controllers.MapProfileController class
//
@SuppressWarnings("unused")
public class MapReportControllerTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	
	private static final String FIELD1 = "Field1";
	private static final String FIELD1_VALUE = "42";
	private static final String FIELD2 = "Field2";
	private static final String FIELD2_VALUE = "37";
	private static final String FIELD3 = "Field3";
	private static final String FIELD3_VALUE = "Foo";
	private static final String FIELD4 = "Field4";
	private static final String FIELD4_VALUE = "Bar";
	
	
	//
	//	Private members
	//
	private Logger logger = null;	
	

	//
	//	Setup/Tear-down
	//
	
	@Before
	public void initialization()
	{
		logger = Mockito.mock(Logger.class);
		
		Mockito.when(logger.isDebugEnabled()).thenReturn(true);
	}

	
	//
	//	Tests
	//

	@Test
	public void testConstructorOK() 
	{
		MapReportController controller = new MapReportController(logger, buildFields());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullFieldsArray() 
	{
		MapReportController controller = new MapReportController(logger, null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullLogger() 
	{
		MapReportController controller = new MapReportController(null, buildFields());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullFieldsArrayElement() 
	{
		Field[] fields = buildFields();
		
		fields[2] = null;
		
		MapReportController controller = new MapReportController(logger, fields);
	}
	
	
	@Test
	public void handleMapRecord()
	{
		MapReportController controller = new MapReportController(logger, buildFields());
		MapRecord record = Mockito.mock(MapRecord.class);
	
		// Stub out mock
		Mockito.when(record.getFieldValue(FIELD1)).thenReturn(FIELD1_VALUE);
		Mockito.when(record.getFieldValue(FIELD2)).thenReturn(FIELD2_VALUE);
		Mockito.when(record.getFieldValue(FIELD3)).thenReturn(FIELD3_VALUE);
		Mockito.when(record.getFieldValue(FIELD4)).thenReturn(FIELD4_VALUE);

		controller.handleMapRecord(record);
		
		// Verify that the controller worked as expected
		ArgumentCaptor<String> fieldArgument = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<FunctionOutput> outputArgument = ArgumentCaptor.forClass(FunctionOutput.class);

		Mockito.verify(record, Mockito.atLeastOnce()).addFunctionOutput(fieldArgument.capture(), outputArgument.capture());
		
		assertEquals("DataReporter=" + FIELD1_VALUE, outputArgument.getAllValues().get(0).serialize());
		assertEquals("DataReporter=" + FIELD2_VALUE, outputArgument.getAllValues().get(1).serialize());
		assertEquals("DataReporter=" + FIELD3_VALUE, outputArgument.getAllValues().get(2).serialize());
		assertEquals("DataReporter=" + FIELD4_VALUE, outputArgument.getAllValues().get(3).serialize());
	} 
	

	//
	//	Private/helper methods
	//

	private Field[] buildFields()
	{
		Field[] fields = new Field[4];
		
		fields[0] = new Field(FIELD1, FieldType.numeric);
		fields[1] = new Field(FIELD2, FieldType.numeric);
		fields[2] = new Field(FIELD3, FieldType.categorical);
		fields[3] = new Field(FIELD4, FieldType.categorical);
		
		return fields;
	}
}
