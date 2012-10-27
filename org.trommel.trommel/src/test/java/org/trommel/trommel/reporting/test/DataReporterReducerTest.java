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
package org.trommel.trommel.reporting.test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.trommel.trommel.reporting.DataReporterReducer;


//
//	Unit tests for the org.trommel.trommel.reporting.DataReporterReducer class
//

public class DataReporterReducerTest 
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
		@SuppressWarnings("unused")
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
		String line1 = FIELD1_VALUE1 + "\t3\n";
		
		reporter.handleReduceRecord(FIELD1_VALUE1);
		reporter.handleReduceRecord(FIELD1_VALUE1);
		reporter.handleReduceRecord(FIELD1_VALUE1);
	
		String reduceValue = reporter.getReduceResult();
		
		assertTrue(reduceValue.contains(line1));
	}
}
