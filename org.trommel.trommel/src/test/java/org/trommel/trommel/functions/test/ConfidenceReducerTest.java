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
import org.trommel.trommel.FieldType;
import org.trommel.trommel.functions.ConfidenceReducer;
import org.trommel.trommel.functions.VariabilityReducer;


//
//	Unit tests for the org.trommel.trommel.functions.ConfidenceReducer class
//
@SuppressWarnings("unused")
public class ConfidenceReducerTest 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String CONF_HANDLER_NAME = "Confidence";
	private static final String VAR_HANDLER_NAME = "Variability";

	
	// Field values
	private static final String ROW1_VALUE = "12";
	private static final String ROW2_VALUE = "5";
	private static final String ROW3_VALUE = "16";
	private static final String ROW4_VALUE = "17";
	private static final String ROW5_VALUE = "18";
	private static final String ROW6_VALUE = "29";
	private static final String ROW7_VALUE = "14";
	private static final String ROW8_VALUE = "22";
	private static final String ROW9_VALUE = "32";
	
	
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
	public void testSingleParmConstructorOK() 
	{
		VariabilityReducer var = Mockito.mock(VariabilityReducer.class);
		
		ConfidenceReducer con = new ConfidenceReducer(logger, var);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testTwoParmConstructorInvalidTarget() 
	{
		VariabilityReducer var = Mockito.mock(VariabilityReducer.class);
		
		ConfidenceReducer con = new ConfidenceReducer(logger, 100, var);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testTwoParmConstructorNullVarReducer() 
	{
		
		ConfidenceReducer con = new ConfidenceReducer(logger, 85, null);
	}
	
	@Test
	public void testGetHandlerName()
	{
		VariabilityReducer var = new VariabilityReducer(logger, FieldType.numeric);
		ConfidenceReducer con = new ConfidenceReducer(logger, 80, var);

		assertEquals(CONF_HANDLER_NAME, con.getHandlerName());
	}
			
	@Test
	public void testNumericGetReduceResult()
	{
		List<HashMap<String, String>> records = reduceRecords();
		VariabilityReducer var = new VariabilityReducer(logger, FieldType.numeric);
		ConfidenceReducer con = new ConfidenceReducer(logger, 80, var);
		
		for (HashMap<String, String> record : records)
		{
			var.handleReduceRecord(record);
			con.handleReduceRecord(record);
		}
	
		// We should be 85% confident that 80% of the variability has been captured.
		assertEquals("85", con.getReduceResult());
	}

	@Test
	public void testNumericGetConfidenceRecordCount()
	{
		List<HashMap<String, String>> records = reduceRecords();
		VariabilityReducer var = new VariabilityReducer(logger, FieldType.numeric);
		ConfidenceReducer con = new ConfidenceReducer(logger, 80, var);
		
		for (HashMap<String, String> record : records)
		{
			var.handleReduceRecord(record);
			con.handleReduceRecord(record);
		}
	
		// Should have taken all 21 records to be 85% confident that 80% of the variability has been captured.
		assertEquals("21", con.getConfidenceRecordCount());
	}
	
	
	//
	//	Private/helper methods
	//
	
	private List<HashMap<String,String>> reduceRecords()
	{
		ArrayList<HashMap<String,String>> records = new ArrayList<HashMap<String, String>>();
		
		// Add 21 records
		records.add(0, new HashMap<String, String>());
		records.get(0).put(VAR_HANDLER_NAME, ROW1_VALUE);
				
		records.add(1, new HashMap<String, String>());
		records.get(1).put(VAR_HANDLER_NAME, ROW2_VALUE);
				
		records.add(2, new HashMap<String, String>());
		records.get(2).put(VAR_HANDLER_NAME, ROW3_VALUE);
				
		records.add(3, new HashMap<String, String>());
		records.get(3).put(VAR_HANDLER_NAME, ROW4_VALUE);
				
		records.add(4, new HashMap<String, String>());
		records.get(4).put(VAR_HANDLER_NAME, ROW5_VALUE);
				
		records.add(5, new HashMap<String, String>());
		records.get(5).put(VAR_HANDLER_NAME, ROW6_VALUE);
		
		records.add(6, new HashMap<String, String>());
		records.get(6).put(VAR_HANDLER_NAME, ROW7_VALUE);
				
		records.add(7, new HashMap<String, String>());
		records.get(7).put(VAR_HANDLER_NAME, ROW8_VALUE);
				
		records.add(8, new HashMap<String, String>());
		records.get(8).put(VAR_HANDLER_NAME, ROW9_VALUE);
		
		records.add(9, new HashMap<String, String>());
		records.get(9).put(VAR_HANDLER_NAME, ROW1_VALUE);
				
		records.add(10, new HashMap<String, String>());
		records.get(10).put(VAR_HANDLER_NAME, ROW2_VALUE);
				
		records.add(11, new HashMap<String, String>());
		records.get(11).put(VAR_HANDLER_NAME, ROW3_VALUE);
				
		records.add(12, new HashMap<String, String>());
		records.get(12).put(VAR_HANDLER_NAME, ROW4_VALUE);
				
		records.add(13, new HashMap<String, String>());
		records.get(13).put(VAR_HANDLER_NAME, ROW5_VALUE);
				
		records.add(14, new HashMap<String, String>());
		records.get(14).put(VAR_HANDLER_NAME, ROW6_VALUE);
		
		records.add(15, new HashMap<String, String>());
		records.get(15).put(VAR_HANDLER_NAME, ROW7_VALUE);
				
		records.add(16, new HashMap<String, String>());
		records.get(16).put(VAR_HANDLER_NAME, ROW8_VALUE);
				
		records.add(17, new HashMap<String, String>());
		records.get(17).put(VAR_HANDLER_NAME, ROW9_VALUE);
				
		records.add(18, new HashMap<String, String>());
		records.get(18).put(VAR_HANDLER_NAME, ROW1_VALUE);
				
		records.add(19, new HashMap<String, String>());
		records.get(19).put(VAR_HANDLER_NAME, ROW2_VALUE);
				
		records.add(20, new HashMap<String, String>());
		records.get(20).put(VAR_HANDLER_NAME, ROW3_VALUE);
				
		return records;
	}	
}