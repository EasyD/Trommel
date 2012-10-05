/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.controllers.tests;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.controllers.MapProfileController;
import org.trommel.trommel.controllers.ProfileFunction;


//
//	Unit tests for the org.trommel.trommel.controllers.MapProfileController class
//
public class MapProfileControllerTests 
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
	//	Tests
	//
	
	@Test
	public void testConstructorOK() 
	{
		Logger logger = Mockito.mock(Logger.class);
		@SuppressWarnings("unused")
		MapProfileController controller = new MapProfileController(logger, buildFields());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullLogger() 
	{
		@SuppressWarnings("unused")
		MapProfileController controller = new MapProfileController(null, buildFields());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorEmptyArray() 
	{
		Logger logger = Mockito.mock(Logger.class);
		@SuppressWarnings("unused")
		MapProfileController controller = new MapProfileController(logger, new Field[0]);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullArrayMember() 
	{
		Logger logger = Mockito.mock(Logger.class);
		Field[] fields = buildFields();
		
		fields[3] = null;
		
		@SuppressWarnings("unused")
		MapProfileController controller = new MapProfileController(logger, fields);
	}
		
	@Test
	public void handleMapRecord()
	{
		Logger logger = Mockito.mock(Logger.class);
		MapProfileController controller = new MapProfileController(logger, buildFields());
		MapRecord record = Mockito.mock(MapRecord.class);

		// Stub out mock
		Mockito.when(record.getFieldValue(FIELD1)).thenReturn(FIELD1_VALUE);
		Mockito.when(record.getFieldValue(FIELD2)).thenReturn(FIELD2_VALUE);
		Mockito.when(record.getFieldValue(FIELD3)).thenReturn(FIELD3_VALUE);
		Mockito.when(record.getFieldValue(FIELD4)).thenReturn(FIELD4_VALUE);
		
		// Build up controller and handle mocked MapRecord
		controller.addFunction(ProfileFunction.Max);
		controller.addFunction(ProfileFunction.Min);
		controller.addFunction(ProfileFunction.Distinct);
		controller.addFunction(ProfileFunction.Empty);
		controller.addFunction(ProfileFunction.Var);
		controller.addFunction(ProfileFunction.Lin);
		controller.addFunction(ProfileFunction.Lin, "50");
		
		controller.handleMapRecord(record);
		
		// Verify that the controller worked as expected
		ArgumentCaptor<String> fieldArgument = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<FunctionOutput> outputArgument = ArgumentCaptor.forClass(FunctionOutput.class);

		Mockito.verify(record, Mockito.atLeastOnce()).addFunctionOutput(fieldArgument.capture(), outputArgument.capture());
		
		assertEquals("Max=" + FIELD1_VALUE, outputArgument.getAllValues().get(0).serialize());
		assertEquals("Max=" + FIELD2_VALUE, outputArgument.getAllValues().get(1).serialize());
		assertEquals("Min=" + FIELD1_VALUE, outputArgument.getAllValues().get(2).serialize());
		assertEquals("Min=" + FIELD2_VALUE, outputArgument.getAllValues().get(3).serialize());
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
