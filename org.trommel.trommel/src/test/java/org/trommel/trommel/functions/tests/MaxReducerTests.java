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
import org.trommel.trommel.functions.MaxReducer;

//
//	Unit tests for the org.trommel.trommel.functions.MaxReducer class
//
public class MaxReducerTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Max";
	
	// Field values
	private static final String FIELD1_VALUE = "15.0";
	private static final String FIELD2_VALUE = "10.0";
	private static final String FIELD3_VALUE = "11.0";

	
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
	public void testGetReduceResult() 
	{
		List<HashMap<String,String>> records = reduceRecords();
		MaxReducer max = new MaxReducer(logger);
		
		for (HashMap<String,String> record : records)
		{
			max.handleReduceRecord(record);
		}
		
		assertEquals(FIELD1_VALUE, max.getReduceResult());
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
		
		records.get(1).put(FUNCTION_NAME, FIELD2_VALUE);

		// Third Record
		records.add(2, new HashMap<String, String>());
		
		records.get(2).put(FUNCTION_NAME, FIELD3_VALUE);
		
		return records;
	}	
}
