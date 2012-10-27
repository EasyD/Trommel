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
import org.trommel.trommel.utilities.StringUtilities;


//
//	Unit tests for the org.trommel.trommel.Field class
//
public class StringUtilitiesTest 
{
	//
	//	Tests
	//
	
	@Test
	public void testString() 
	{
		assertEquals(false, StringUtilities.isNullOrEmpty("Hello, world!"));
	}

	@Test
	public void testNullString()
	{
		assertEquals(true, StringUtilities.isNullOrEmpty(null));
	}

	@Test
	public void testEmptyString()
	{
		assertEquals(true, StringUtilities.isNullOrEmpty(""));
	}

	@Test
	public void testWhitespaceString()
	{
		assertEquals(true, StringUtilities.isNullOrEmpty(" \t "));
	}
}
