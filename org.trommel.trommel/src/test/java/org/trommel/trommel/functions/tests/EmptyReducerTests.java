/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.trommel.trommel.functions.EmptyReducer;

//
//	Unit tests for the org.trommel.trommel.functions.EmptyMapper class
//
public class EmptyReducerTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Empty";
	private static final String OTHER_FUNCTION_NAME = "Foo";
	
	// Field values
	private static final String FIELD_VALUE = "1";


	//
	//	Tests
	//

	@Test
	public void testGetReduceResult() 
	{
		List<HashMap<String,String>> records = reduceRecords();
		EmptyReducer empty = new EmptyReducer();
		
		for (HashMap<String, String> record : records)
		{
			empty.handleReduceRecord(record);
		}
		
		assertEquals("3", empty.getReduceResult());
	}

	
	//
	//	Private/helper methods
	//	
	
	private List<HashMap<String,String>> reduceRecords()
	{
		ArrayList<HashMap<String,String>> records = new ArrayList<HashMap<String, String>>();
		
		// First Record
		records.add(0, new HashMap<String, String>());
		
		records.get(0).put(FUNCTION_NAME, FIELD_VALUE);
				
		// Second Record
		records.add(0, new HashMap<String, String>());
		
		records.get(0).put(OTHER_FUNCTION_NAME, FIELD_VALUE);
				
		// Third Record
		records.add(2, new HashMap<String, String>());
		
		records.get(2).put(FUNCTION_NAME, FIELD_VALUE);
				
		// Fourth Record
		records.add(3, new HashMap<String, String>());
		
		records.get(3).put(FUNCTION_NAME, FIELD_VALUE);
		
		return records;
	}	
}
