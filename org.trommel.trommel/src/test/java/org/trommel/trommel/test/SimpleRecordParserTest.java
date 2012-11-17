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

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.SimpleRecordParser;


//
//	Unit tests for the org.trommel.trommel.SimpleRecordParser class
//
@SuppressWarnings("unused")
public class SimpleRecordParserTest 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	
	private static final String FIELD_DELIMITER = "\t";
	private static final String FIELD1 = "Field1";
	private static final String FIELD1_VALUE = "42";
	private static final String FIELD2 = "Field2";
	private static final String FIELD2_VALUE = "37";
	private static final String FIELD3 = "Field3";
	private static final String FIELD3_VALUE = "Foo";
	private static final String FIELD4 = "Field4";
	private static final String FIELD4_VALUE = "Bar";
	private static final String RECORD_DELIMITER = "*|*";

	
	//
	//	Tests
	//
	
	@Test
	public void testTwoParmConstructorOK() 
	{
		SimpleRecordParser parser = new SimpleRecordParser(buildFields(), RECORD_DELIMITER);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullFieldsArray()
	{
		SimpleRecordParser parser = new SimpleRecordParser(null, RECORD_DELIMITER);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorEmptyFieldsArray()
	{
		SimpleRecordParser parser = new SimpleRecordParser(new Field[0], RECORD_DELIMITER);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorFieldsArrayNullMember()
	{
		Field[] fields = buildFields();
		
		fields[2] = null;
		
		SimpleRecordParser parser = new SimpleRecordParser(fields, RECORD_DELIMITER);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullRecordDelimiter()
	{
		SimpleRecordParser parser = new SimpleRecordParser(buildFields(), null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorEmptyFieldDelimiter()
	{
		SimpleRecordParser parser = new SimpleRecordParser(buildFields(), "", RECORD_DELIMITER);
	}
	
	@Test
	public void testParseOK()
		throws ParseException
	{
		SimpleRecordParser parser = new SimpleRecordParser(buildFields(), FIELD_DELIMITER, RECORD_DELIMITER);
		MapRecord record = parser.parse(buildRecordString());
		
		assertEquals(FIELD1_VALUE, record.getFieldValue(FIELD1));
		assertEquals(FIELD2_VALUE, record.getFieldValue(FIELD2));
		assertEquals(FIELD3_VALUE, record.getFieldValue(FIELD3));
		assertEquals(FIELD4_VALUE, record.getFieldValue(FIELD4));		
	}
		
	@Test
	public void testParseWithEmptiesOK()
		throws ParseException
	{
		SimpleRecordParser parser = new SimpleRecordParser(buildFields(), FIELD_DELIMITER, RECORD_DELIMITER);
		MapRecord record = parser.parse(buildRecordStringWithEmpties());
		
		assertEquals("", record.getFieldValue(FIELD1));
		assertEquals("", record.getFieldValue(FIELD2));
		assertEquals("", record.getFieldValue(FIELD3));
		assertEquals("", record.getFieldValue(FIELD4));		
	}
	
	@Test(expected=ParseException.class)
	public void testParseException()
		throws ParseException
	{
		SimpleRecordParser parser = new SimpleRecordParser(buildFields(), FIELD_DELIMITER, RECORD_DELIMITER);
		MapRecord record = parser.parse("SomeValue");
	}

	
	//
	//	Private/helper methods
	//

	private Field[] buildFields()
	{
		Field[] fields = new Field[4];
		
		fields[0] = new Field(FIELD1, FieldType.numeric);
		fields[1] = new Field(FIELD2, FieldType.numeric);
		fields[2] = new Field(FIELD3, FieldType.categorical);
		fields[3] = new Field(FIELD4, FieldType.categorical);
		
		return fields;
	}
	
	private String buildRecordString()
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(FIELD1_VALUE);
		buffer.append(FIELD_DELIMITER);
		buffer.append(FIELD2_VALUE);
		buffer.append(FIELD_DELIMITER);
		buffer.append(FIELD3_VALUE);
		buffer.append(FIELD_DELIMITER);
		buffer.append(FIELD4_VALUE);
		
		return buffer.toString();
	}
	
	private String buildRecordStringWithEmpties()
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(FIELD_DELIMITER);
		buffer.append(FIELD_DELIMITER);
		buffer.append(FIELD_DELIMITER);
		
		return buffer.toString();
	}
}
