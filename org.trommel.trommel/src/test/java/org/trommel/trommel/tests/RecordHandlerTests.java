/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.tests;

import static org.junit.Assert.*;

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
public class RecordHandlerTests 
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
		@SuppressWarnings("unused")
		RecordHandler recordHandler = new MaxMapper(logger, fields);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullLogger()
	{
		Logger logger = Mockito.mock(Logger.class);
		@SuppressWarnings("unused")
		RecordHandler recordHandler = new MaxMapper(null, fields);
	}
}
