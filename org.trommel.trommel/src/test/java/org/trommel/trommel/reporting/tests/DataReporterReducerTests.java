/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.reporting.tests;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;
import org.trommel.trommel.reporting.DataReporterReducer;


//
//	Unit tests for the org.trommel.trommel.reporting.DataReporterReducer class
//
public class DataReporterReducerTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FIELD1 = "Field1";
	private static final String FIELD1_VALUE1 = "Value1";

	
	//
	//	Tests
	//

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullField() 
	{
		@SuppressWarnings("unused")
		DataReporterReducer reporter = new DataReporterReducer(null);
	}
	
	
	@Test
	public void testGetReduceResult() 
	{
		DataReporterReducer reporter = new DataReporterReducer(FIELD1);
		String header = "FIELD\tCONTENT\tCOUNT\n";
		String line1 = FIELD1 + "\t" + FIELD1_VALUE1 + "\t3\n";
		
		reporter.handleReduceRecord(reduceRecord());
		reporter.handleReduceRecord(reduceRecord());
		reporter.handleReduceRecord(reduceRecord());

		String reduceValue = reporter.getReduceResult();
		
		assertTrue(reduceValue.contains(header));
		assertTrue(reduceValue.contains(line1));
	}


	//
	//	Private/helper methods
	//	
	
	private HashMap<String, String> reduceRecord()
	{
		HashMap<String, String> record = new HashMap<String, String>();

		record.put("Foo", "FooValue");
		record.put("Bar", "BarValue");
		record.put("DataReporter", FIELD1_VALUE1);
		record.put("FooBar", "FooBarValue");
		
		return record;
	}
}
