/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.trommel.trommel.SimpleFieldParser;


//
//	Unit tests for the org.trommel.trommel.SimpleFieldParser class
//
public class SimpleFieldParserTests 
{
	//
	//	Tests
	//
	
	@Test
	public void testConstructor() 
	{
		@SuppressWarnings("unused")
		SimpleFieldParser parser = new SimpleFieldParser();
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorEmptyDelimiter() 
	{
		@SuppressWarnings("unused")
		SimpleFieldParser parser = new SimpleFieldParser(" \t ");
	}

	@Test
	public void testConstructorDelimiter() 
	{
		@SuppressWarnings("unused")
		SimpleFieldParser parser = new SimpleFieldParser("|");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testParseNullRecord()
	{
		SimpleFieldParser parser = new SimpleFieldParser();
		
		parser.parse(null);
	}
	
	@Test
	public void testParseDelimiterNotInRecord()	
	{
		SimpleFieldParser parser = new SimpleFieldParser();
		String record = "Field1|Field2|Field3|Field4";
		
		assertEquals(1, parser.parse(record).length);
	}
	
	@Test
	public void testParse()	
	{
		SimpleFieldParser parser = new SimpleFieldParser("|");
		String record = "Field1|Field2||Field3|Field4|";
		String[] fields = parser.parse(record);
		
		assertEquals(6, fields.length);
	}
}
