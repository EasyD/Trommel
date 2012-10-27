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
package org.trommel.trommel.utilities.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.trommel.trommel.utilities.Counter;

//
//	Unit tests for the org.trommel.trommel.reporting.Counter class
//
public class CounterTest 
{
	//
	//	Tests
	//
	
	@Test
	public void testGetCountAndIncrement()
	{
		Counter counter = new Counter();
		
		counter.increment();
		counter.increment();
		counter.increment();
		counter.increment();
		
		assertEquals(4, counter.getCount());
	}

	@Test
	public void testToString()
	{
		Counter counter = new Counter();
		
		counter.increment();
		counter.increment();
		counter.increment();
		counter.increment();
		counter.increment();
		
		assertEquals("5", counter.toString());
	}
}
