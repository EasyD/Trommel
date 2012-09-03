/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.functions.Variability;

//
//	Unit tests for the org.trommel.trommel.functions.Variability class
//
public class VariabilityTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String DELIMITER = "*|*";
	private static final String FUNCTION_NAME = "Variability";

	// First row fields and values
	private static final String FIELD1 = "Field1";
	private static final String FIELD2 = "Field2";
	private static final String FIELD3 = "Field3";
	private static final String FIELD1_MAP_NUMERIC_VALUE = "12.0";
	private static final String FIELD1_REDUCE_NUMERIC_VALUE = "12:144";
	private static final String FIELD2_MAP_NUMERIC_VALUE = "5.0";
	private static final String FIELD2_REDUCE_NUMERIC_VALUE = "5:25";
	private static final String FIELD3_MAP_NUMERIC_VALUE = "16.0";
	private static final String FIELD3_REDUCE_NUMERIC_VALUE = "16:256";

	//	Second row values
	private static final String FIELD4_MAP_NUMERIC_VALUE = "17.0";
	private static final String FIELD4_REDUCE_NUMERIC_VALUE = "17:289";
	private static final String FIELD5_MAP_NUMERIC_VALUE = "18.0";
	private static final String FIELD5_REDUCE_NUMERIC_VALUE = "18:324";
	private static final String FIELD6_MAP_NUMERIC_VALUE = "29.0";
	private static final String FIELD6_REDUCE_NUMERIC_VALUE = "29:841";

	//	Third row values
	private static final String FIELD7_MAP_NUMERIC_VALUE = "85.0";
	private static final String FIELD7_REDUCE_NUMERIC_VALUE = "85:7225";
	private static final String FIELD8_MAP_NUMERIC_VALUE = "33.0";
	private static final String FIELD8_REDUCE_NUMERIC_VALUE = "33:1089";
	private static final String FIELD9_MAP_NUMERIC_VALUE = "124.0";
	private static final String FIELD9_REDUCE_NUMERIC_VALUE = "124:15376";

	
	//
	//	Private members
	//
	private static Field[] numericFields = null;	
	

	//
	//	Setup/Tear-down
	//
	
	@BeforeClass
	public static void initialization()
	{
		numericFields = new Field[3];
		
		numericFields[0] = new Field(FIELD1, FieldType.numeric);
		numericFields[1] = new Field(FIELD2, FieldType.numeric);
		numericFields[2] = new Field(FIELD3, FieldType.numeric);
	}
		
	
	//
	//	Tests
	//
	
	@Test
	public void testGetNumericReduceResult() 
	{
		List<HashMap<String,String>> records = numericReduceRecords();
		Variability variability = new Variability(numericFields);
		
		for (HashMap<String, String> record : records)
		{
			variability.handleReduceRecord(record);
		}
		
		assertEquals("40.0", variability.getReduceResult());
	}

	//
	//	Private/helper methods
	//
	
	private List<HashMap<String,String>> numericReduceRecords()
	{
		ArrayList<HashMap<String,String>> records = new ArrayList<HashMap<String, String>>();
		
		// Add nine records
		records.add(0, new HashMap<String, String>());
		records.get(0).put(FUNCTION_NAME, FIELD1_REDUCE_NUMERIC_VALUE);
				
		records.add(1, new HashMap<String, String>());
		records.get(1).put(FUNCTION_NAME, FIELD2_REDUCE_NUMERIC_VALUE);
				
		records.add(2, new HashMap<String, String>());
		records.get(2).put(FUNCTION_NAME, FIELD3_REDUCE_NUMERIC_VALUE);
				
		records.add(3, new HashMap<String, String>());
		records.get(3).put(FUNCTION_NAME, FIELD4_REDUCE_NUMERIC_VALUE);
				
		records.add(4, new HashMap<String, String>());
		records.get(4).put(FUNCTION_NAME, FIELD5_REDUCE_NUMERIC_VALUE);
				
		records.add(5, new HashMap<String, String>());
		records.get(5).put(FUNCTION_NAME, FIELD6_REDUCE_NUMERIC_VALUE);
				
		records.add(6, new HashMap<String, String>());
		records.get(6).put(FUNCTION_NAME, FIELD7_REDUCE_NUMERIC_VALUE);
				
		records.add(7, new HashMap<String, String>());
		records.get(7).put(FUNCTION_NAME, FIELD8_REDUCE_NUMERIC_VALUE);
				
		records.add(8, new HashMap<String, String>());
		records.get(8).put(FUNCTION_NAME, FIELD9_REDUCE_NUMERIC_VALUE);
				
		return records;
	}	
}
