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
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.controllers.ReduceProfileController;
import org.trommel.trommel.functions.MaxReducer;
import org.trommel.trommel.functions.MinReducer;
import org.trommel.trommel.functions.VariabilityReducer;
import org.trommel.trommel.scripting.interpreters.MapInterpreter;


//
//	Unit tests for the org.trommel.trommel.controllers.ReduceProfileController class
//
@SuppressWarnings("unused")
public class ReduceProfileControllerTest 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String MIN_NAME = "Min";
	private static final String MAX_NAME = "Max";
	
	
	// Reduce phase fields and values values
	private static final String FIELD1_VALUE1 = "15.0";
	private static final String FIELD1_VALUE2 = "20.0";
	private static final String FIELD1_VALUE3 = "25.0";

	
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
		ReduceProfileController controller = new ReduceProfileController(logger);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullLogger() 
	{
		ReduceProfileController controller = new ReduceProfileController(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddRecordHandlerNullHandler() 
	{
		ReduceProfileController controller = new ReduceProfileController(logger);
		
		controller.addRecordHandler(null);
	}
	
	@Test
	public void testGetHeader()
	{
		ReduceProfileController controller = new ReduceProfileController(logger);
		
		// Add handlers
		controller.addRecordHandler(new MaxReducer(logger));
		controller.addRecordHandler(new MinReducer(logger));
		controller.addRecordHandler(new VariabilityReducer(logger, FieldType.numeric));

		assertEquals("Field\tMax\tMin\tVariability", controller.getHeader());
	}

	@Test
	public void handleMapRecord()
	{
		ReduceProfileController controller = new ReduceProfileController(logger);
		String[] records = reduceRecords();
		
		// Add handlers
		controller.addRecordHandler(new MaxReducer(logger));
		controller.addRecordHandler(new MinReducer(logger));
		
		// Handle records
		for(String record : records)
		{
			controller.handleReduceRecord(record);
		}
		
		// Verify results
		assertEquals(FIELD1_VALUE3 + "\t" + FIELD1_VALUE1, controller.getFormattedResults());
	}

	
	//
	//	Private/helper methods
	//

	private String[] reduceRecords()
	{
		String[] records = new String[3];
		
		// First Record
		records[0] = MIN_NAME + "=" + FIELD1_VALUE1 + MapInterpreter.DELIMITER + MAX_NAME + "=" + FIELD1_VALUE1;
		
		// Second Record
		records[1] = MIN_NAME + "=" + FIELD1_VALUE2 + MapInterpreter.DELIMITER + MAX_NAME + "=" + FIELD1_VALUE2;

		// Third Record
		records[2] = MIN_NAME + "=" + FIELD1_VALUE3 + MapInterpreter.DELIMITER + MAX_NAME + "=" + FIELD1_VALUE3;

		return records;
	}	
}
