/*
 * Copyright 2012 Dave Langer
 *    
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package org.trommel.trommel.controllers.test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mockito;
import org.trommel.trommel.controllers.ReduceReportController;


//
//	Unit tests for the org.trommel.trommel.controllers.ReduceReportController class
//
@SuppressWarnings("unused")
public class ReduceReportControllerTest 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String DATA_REPORTER_NAME = "DataReporter";
	
	// Reduce phase fields and values values
	private static final String FIELD1 = "Field1";
	private static final String FIELD1_VALUE1 = "15.0";
	private static final String FIELD1_VALUE2 = "20.0";

	
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
	public void testConstructorOK()
	{
		ReduceReportController controller = new ReduceReportController(logger, FIELD1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullLogger() 
	{
		ReduceReportController controller = new ReduceReportController(null, FIELD1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullFieldName() 
	{
		ReduceReportController controller = new ReduceReportController(logger, null);
	}
	
	@Test
	public void testGetHeader()
	{
		ReduceReportController controller = new ReduceReportController(logger, FIELD1);
		
		assertEquals("Field\tContent\tCount", controller.getHeader());
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void testHandleReduceRecordEmptyRecord() 
	{
		ReduceReportController controller = new ReduceReportController(logger, FIELD1);
		
		controller.handleReduceRecord(" \t ");
	}
	
	@Test
	public void testGetFormattedResults()
	{
		ReduceReportController controller = new ReduceReportController(logger, FIELD1);
		String resultsLine1 = FIELD1 + "\t" + FIELD1_VALUE1 + "\t3\n";
		String resultsLine2 = FIELD1_VALUE2 + "\t2\n";
		
		controller.handleReduceRecord(DATA_REPORTER_NAME + "=" + FIELD1_VALUE1);
		controller.handleReduceRecord(DATA_REPORTER_NAME + "=" + FIELD1_VALUE2);
		controller.handleReduceRecord(DATA_REPORTER_NAME + "=" + FIELD1_VALUE1);
		controller.handleReduceRecord(DATA_REPORTER_NAME + "=" + FIELD1_VALUE1);
		controller.handleReduceRecord(DATA_REPORTER_NAME + "=" + FIELD1_VALUE2);

		String formattedResults = controller.getFormattedResults();
		
		assertTrue(formattedResults.contains(resultsLine1));
		assertTrue(formattedResults.contains(resultsLine2));
	}
}
