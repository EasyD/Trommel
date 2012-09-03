/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.trommel.trommel.functions.MinReducer;

//
//	Unit tests for the org.trommel.trommel.functions.MinReducer class
//
public class MinReducerTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Min";
	
	// Field values
	private static final String FIELD1_VALUE = "15.0";
	private static final String FIELD2_VALUE = "10.0";
	private static final String FIELD3_VALUE = "11.0";

	
	//
	//	Tests
	//
	
	@Test
	public void testGetReduceResult() 
	{
		List<HashMap<String,String>> records = reduceRecords();
		MinReducer min = new MinReducer();
		
		for (HashMap<String,String> record : records)
		{
			min.handleReduceRecord(record);
		}
		
		assertEquals(FIELD2_VALUE, min.getReduceResult());
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
