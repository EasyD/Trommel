/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.reporting.tests;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.trommel.trommel.reporting.DataReporterReducer;


//
//	Unit tests for the org.trommel.trommel.reporting.DataReporterReducer class
//
@SuppressWarnings("unused")
public class DataReporterReducerTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FIELD1 = "Field1";
	private static final String FIELD1_VALUE1 = "Value1";

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

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullField() 
	{
		DataReporterReducer reporter = new DataReporterReducer(logger, null);
	}
		
	@Test(expected = IllegalArgumentException.class)
	public void testHandleReduceRecordEmptyFieldValue()
	{
		DataReporterReducer reporter = new DataReporterReducer(logger, FIELD1);

		reporter.handleReduceRecord("");
	}

	@Test
	public void testGetReduceResult() 
	{
		DataReporterReducer reporter = new DataReporterReducer(logger, FIELD1);
		String line1 = FIELD1 + "\t" + FIELD1_VALUE1 + "\t3\n";
		
		reporter.handleReduceRecord(FIELD1_VALUE1);
		reporter.handleReduceRecord(FIELD1_VALUE1);
		reporter.handleReduceRecord(FIELD1_VALUE1);
	
		String reduceValue = reporter.getReduceResult();
		
		assertTrue(reduceValue.contains(line1));
	}
}
