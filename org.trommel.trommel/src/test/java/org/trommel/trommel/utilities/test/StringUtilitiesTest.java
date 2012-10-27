/*
 * TODO - Insert license blurb here
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
