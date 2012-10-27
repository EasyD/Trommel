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
package org.trommel.trommel.test;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.RecordHandler;
import org.trommel.trommel.functions.MaxMapper;


//
//	Unit tests for the org.trommel.trommel.OutputSet class
//
@SuppressWarnings("unused")
public class RecordHandlerTest 
{	
	//
	//	Private members
	//
	private Field[] fields = null;	
	

	//
	//	Setup/Tear-down
	//
	
	@Before
	public void initialization()
	{
		fields = new Field[3];
		
		fields[0] = new Field("Field1", FieldType.numeric);
		fields[1] = new Field("Field2", FieldType.numeric);
		fields[2] = new Field("Field3", FieldType.numeric);
	}
	
	
	//
	//	Tests
	//
	
	@Test
	public void testConstructorOK()
	{
		Logger logger = Mockito.mock(Logger.class);
		
		RecordHandler recordHandler = new MaxMapper(logger, fields);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullLogger()
	{
		RecordHandler recordHandler = new MaxMapper(null, fields);
	}
}
