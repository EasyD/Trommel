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
package org.trommel.trommel.functions.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.trommel.trommel.functions.EmptyReducer;


//
//	Unit tests for the org.trommel.trommel.functions.EmptyReducer class
//
public class EmptyReducerTest 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String HANDLER_NAME = "Empty";
	private static final String OTHER_HANDLER_NAME = "Foo";
	
	// Field values
	private static final String FIELD_VALUE = "1";
	
	
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
	public void testGetHandlerName()
	{
		EmptyReducer empty = new EmptyReducer(logger);

		assertEquals(HANDLER_NAME, empty.getHandlerName());
	}
	
	@Test
	public void testGetReduceResult() 
	{
		List<HashMap<String,String>> records = reduceRecords();
		EmptyReducer empty = new EmptyReducer(logger);
		
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
		
		records.get(0).put(HANDLER_NAME, FIELD_VALUE);
				
		// Second Record
		records.add(0, new HashMap<String, String>());
		
		records.get(0).put(OTHER_HANDLER_NAME, FIELD_VALUE);
				
		// Third Record
		records.add(2, new HashMap<String, String>());
		
		records.get(2).put(HANDLER_NAME, FIELD_VALUE);
				
		// Fourth Record
		records.add(3, new HashMap<String, String>());
		
		records.get(3).put(HANDLER_NAME, FIELD_VALUE);
		
		return records;
	}	
}
