/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.junit.Test;
import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.OutputSet;

//
//Unit tests for the org.trommel.trommel.OutputSet class
//
public class OutputSetTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FIELD1 = "Field1";
	private static final String FIELD2 = "Field2";
	private static final String FIELD3 = "Field3";
	private static final String[] FIELD_NAMES = {FIELD1, FIELD2, FIELD3, "Field4", "Field5"};
	private static final String DELIMITER = "*|*";
	
	
	//
	//	Tests
	//
	
	@Test
	public void testConstructorTwoParamsOK() 
	{
		@SuppressWarnings("unused")
		OutputSet outputSet = new OutputSet(FIELD_NAMES, DELIMITER);
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullArray() 
	{
		@SuppressWarnings("unused")
		OutputSet outputSet = new OutputSet(null, DELIMITER);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorEmptyArray() 
	{
		String[] array = { };
		
		@SuppressWarnings("unused")
		OutputSet outputSet = new OutputSet(array, DELIMITER);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorEmptyFieldName() 
	{
		String[] array = { " " };
		
		@SuppressWarnings("unused")
		OutputSet outputSet = new OutputSet(array, DELIMITER);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullDelimiter() 
	{
		@SuppressWarnings("unused")
		OutputSet outputSet = new OutputSet(FIELD_NAMES, null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddFunctionOutputNullFieldName()
	{
		OutputSet outputSet = newOutputSet();
		FunctionOutput functionOutput = new FunctionOutput("Name", "Output");
		
		outputSet.addFunctionOutput(null, functionOutput);		
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddFunctionOutputNullFunctionOutput()
	{
		OutputSet outputSet = newOutputSet();
		
		outputSet.addFunctionOutput(FIELD1, null);		
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddFunctionOutputUnrecognizedFieldName()
	{
		OutputSet outputSet = newOutputSet();
		FunctionOutput functionOutput = new FunctionOutput("Name", "Output");
		
		outputSet.addFunctionOutput("Foo", functionOutput);		
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSerializeNulLCollector()
		throws IOException
	{
		OutputSet outputSet = newOutputSet();
		
		outputSet.serialize(null);		
	}
	
	@Test
	public void testSerialize()
	{
		OutputSet outputSet = newOutputSet();
		MockOutputCollector<Text, Text> collector = new MockOutputCollector<Text, Text>();

		outputSet.addFunctionOutput(FIELD1, new FunctionOutput("Func1", "Output1.1"));
		outputSet.addFunctionOutput(FIELD2, new FunctionOutput("Func2", "Output2"));
		outputSet.addFunctionOutput(FIELD3, new FunctionOutput("Func3", "Output3"));
		outputSet.addFunctionOutput(FIELD1, new FunctionOutput("Func2", "Output1.2"));
		
		try
		{
			outputSet.serialize(collector);
		}
		catch(IOException e)
		{
			fail("IOException thrown");
		}
		
		assertEquals(3, collector.getKeys().size());
		assertEquals(3, collector.getValues().size());
		
		assertEquals(true, collector.getKeys().contains(new Text(FIELD2)));
		assertEquals(true, collector.getValues().contains(new Text("Func1=Output1.1" + DELIMITER + "Func2=Output1.2")));
	}
	
	
	//
	//	Private/helper methods
	//
	private OutputSet newOutputSet()
	{
		return new OutputSet(FIELD_NAMES, DELIMITER);
	}	
}
