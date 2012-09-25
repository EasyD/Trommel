/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.functions.VariabilityReducer;


//
//	Unit tests for the org.trommel.trommel.functions.VariabilityReducer class
//
public class VariabilityReducerTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Variability";
	
	// Field values
	private static final String ROW1_NUMERIC_VALUE = "12";
	private static final String ROW2_NUMERIC_VALUE = "5";
	private static final String ROW3_NUMERIC_VALUE = "16";
	private static final String ROW4_NUMERIC_VALUE = "17";
	private static final String ROW5_NUMERIC_VALUE = "18";
	private static final String ROW6_NUMERIC_VALUE = "29";


	//
	//	Tests
	//
	
	@Test
	public void testConstructorOK()
	{
		@SuppressWarnings("unused")
		VariabilityReducer var = new VariabilityReducer(FieldType.numeric);			
	}
	
	@Test
	public void testNumericGetReduceResult()
	{
		List<HashMap<String, String>> records = numericReduceRecords();
		VariabilityReducer var = new VariabilityReducer(FieldType.numeric);
		
		for (HashMap<String, String> record : records)
		{
			var.handleReduceRecord(record);
		}
		
		assertEquals("7.884584115009914", var.getReduceResult());
	}
	
	@Test
	public void testCategoricalGetReduceResult()
	{
		List<HashMap<String, String>> records = categoricalReduceRecords();
		VariabilityReducer var = new VariabilityReducer(FieldType.categorical);
		
		for (HashMap<String, String> record : records)
		{
			var.handleReduceRecord(record);
		}
		
		assertEquals("0.6", var.getReduceResult());
	}

	
	//
	//	Private/helper methods
	//
	
	private List<HashMap<String,String>> numericReduceRecords()
	{
		ArrayList<HashMap<String,String>> records = new ArrayList<HashMap<String, String>>();
		
		// Add six records
		records.add(0, new HashMap<String, String>());
		records.get(0).put(FUNCTION_NAME, ROW1_NUMERIC_VALUE);
				
		records.add(1, new HashMap<String, String>());
		records.get(1).put(FUNCTION_NAME, ROW2_NUMERIC_VALUE);
				
		records.add(2, new HashMap<String, String>());
		records.get(2).put(FUNCTION_NAME, ROW3_NUMERIC_VALUE);
				
		records.add(3, new HashMap<String, String>());
		records.get(3).put(FUNCTION_NAME, ROW4_NUMERIC_VALUE);
				
		records.add(4, new HashMap<String, String>());
		records.get(4).put(FUNCTION_NAME, ROW5_NUMERIC_VALUE);
				
		records.add(5, new HashMap<String, String>());
		records.get(5).put(FUNCTION_NAME, ROW6_NUMERIC_VALUE);
				
		return records;
	}	
		
	private List<HashMap<String,String>> categoricalReduceRecords()
	{
		ArrayList<HashMap<String,String>> records = new ArrayList<HashMap<String, String>>();
		
		// Add nine records
		records.add(0, new HashMap<String, String>());
		records.get(0).put(FUNCTION_NAME, ROW1_NUMERIC_VALUE);
				
		records.add(1, new HashMap<String, String>());
		records.get(1).put(FUNCTION_NAME, ROW2_NUMERIC_VALUE);
				
		records.add(2, new HashMap<String, String>());
		records.get(2).put(FUNCTION_NAME, ROW3_NUMERIC_VALUE);
				
		records.add(3, new HashMap<String, String>());
		records.get(3).put(FUNCTION_NAME, ROW4_NUMERIC_VALUE);
				
		records.add(4, new HashMap<String, String>());
		records.get(4).put(FUNCTION_NAME, ROW5_NUMERIC_VALUE);
				
		records.add(5, new HashMap<String, String>());
		records.get(5).put(FUNCTION_NAME, ROW6_NUMERIC_VALUE);
		
		// Add some duplicate values in for ROD calculation
		records.add(6, new HashMap<String, String>());
		records.get(6).put(FUNCTION_NAME, ROW3_NUMERIC_VALUE);
				
		records.add(7, new HashMap<String, String>());
		records.get(7).put(FUNCTION_NAME, ROW4_NUMERIC_VALUE);
				
		records.add(8, new HashMap<String, String>());
		records.get(8).put(FUNCTION_NAME, ROW5_NUMERIC_VALUE);
				
		records.add(9, new HashMap<String, String>());
		records.get(9).put(FUNCTION_NAME, ROW6_NUMERIC_VALUE);
				
		return records;
	}	
}
