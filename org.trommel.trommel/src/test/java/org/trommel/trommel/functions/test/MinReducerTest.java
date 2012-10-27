/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.trommel.trommel.functions.MinReducer;


//
//	Unit tests for the org.trommel.trommel.functions.MinReducer class
//
public class MinReducerTest 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String HANDLER_NAME = "Min";
	
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
	public void testGetHandlerName()
	{
		MinReducer min = new MinReducer(logger);

		assertEquals(HANDLER_NAME, min.getHandlerName());
	}
	
	@Test
	public void testGetReduceResultOK() 
	{
		List<HashMap<String,String>> records = reduceRecords();
		MinReducer min = new MinReducer(logger);
		
		for (HashMap<String,String> record : records)
		{
			min.handleReduceRecord(record);
		}
		
		assertEquals(FIELD2_VALUE, min.getReduceResult());
	}

	
	@Test
	public void testGetReduceResultEmptyString() 
	{
		List<HashMap<String,String>> records = new ArrayList<HashMap<String,String>>();
		MinReducer min = new MinReducer(logger);
		
		for (HashMap<String,String> record : records)
		{
			min.handleReduceRecord(record);
		}
		
		assertEquals("", min.getReduceResult());
	}
	
	//
	//	Private/helper methods
	//
	
	private List<HashMap<String,String>> reduceRecords()
	{
		ArrayList<HashMap<String,String>> records = new ArrayList<HashMap<String, String>>();
		
		// First Record
		records.add(0, new HashMap<String, String>());
		
		records.get(0).put(HANDLER_NAME, FIELD1_VALUE);
				
		// Second Record
		records.add(1, new HashMap<String, String>());
		
		records.get(1).put(HANDLER_NAME, FIELD2_VALUE);

		// Third Record
		records.add(2, new HashMap<String, String>());
		
		records.get(2).put(HANDLER_NAME, FIELD3_VALUE);
		
		return records;
	}	
}
