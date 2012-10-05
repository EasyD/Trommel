/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.functions.DistinctReducer;


//
//	Unit tests for the org.trommel.trommel.functions.DistinctReducer class
//
public class DistinctReducerTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Distinct";
	private static final String OTHER_FUNCTION_NAME = "Foo";
	
	// Reduce phase fields and values values
	private static final String FIELD1 = "Field1";
	private static final String FIELD2 = "Field2";
	private static final String FIELD3 = "Field3";
	private static final String FIELD1_VALUE = "15.0";
	private static final String FIELD2_VALUE = "20.0";
	private static final String FIELD3_VALUE = "20.0";
	private static final String OTHER_FIELD_VALUE = "-1.0";

	
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
	public void testGetReduceResult() 
	{
		List<HashMap<String,String>> records = reduceRecords();
		DistinctReducer distinct = new DistinctReducer(logger);
		
		for (HashMap<String, String> record : records)
		{
			distinct.handleReduceRecord(record);
		}
		
		assertEquals("2", distinct.getReduceResult());
	}
	
		
	//
	//	Private/helper methods
	//	
	
	private List<HashMap<String,String>> reduceRecords()
	{
		ArrayList<HashMap<String,String>> records = new ArrayList<HashMap<String, String>>();
		
		// First Record
		records.add(0, new HashMap<String, String>());
		
		records.get(0).put(FUNCTION_NAME, FIELD1_VALUE);
				
		// Second Record
		records.add(1, new HashMap<String, String>());
		
		records.get(1).put(OTHER_FUNCTION_NAME, OTHER_FIELD_VALUE);
				
		// Third Record
		records.add(2, new HashMap<String, String>());
		
		records.get(2).put(FUNCTION_NAME, FIELD2_VALUE);

		// Fourth Record
		records.add(3, new HashMap<String, String>());
		
		records.get(3).put(FUNCTION_NAME, FIELD3_VALUE);
		
		return records;
	}	
}
