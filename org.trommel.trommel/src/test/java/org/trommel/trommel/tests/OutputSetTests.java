/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.mockito.Mockito;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.MapContext;
import org.junit.Test;
import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.OutputSet;


//
//	Unit tests for the org.trommel.trommel.OutputSet class
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
		throws IOException, InterruptedException
	{
		OutputSet outputSet = newOutputSet();
		
		outputSet.serialize(null);		
	}
	
	@Test
	public void testSerialize() 
		throws IllegalArgumentException, InterruptedException, IOException
	{
		@SuppressWarnings("unchecked")
		MapContext<LongWritable, Text, Text, Text> context = Mockito.mock(MapContext.class);
		OutputSet outputSet = newOutputSet();
		
		outputSet.addFunctionOutput(FIELD1, new FunctionOutput("Func1", "Output1.1"));
		outputSet.addFunctionOutput(FIELD2, new FunctionOutput("Func2", "Output2"));
		outputSet.addFunctionOutput(FIELD3, new FunctionOutput("Func3", "Output3"));
		outputSet.addFunctionOutput(FIELD1, new FunctionOutput("Func2", "Output1.2"));
		
		try
		{
			outputSet.serialize(context);
		}
		catch(IOException e)
		{
			fail("IOException thrown");
		}
		
		Mockito.verify(context).write(new Text(FIELD1), new Text("Func1=Output1.1*|*Func2=Output1.2"));
		Mockito.verify(context).write(new Text(FIELD2), new Text("Func2=Output2"));
		Mockito.verify(context).write(new Text(FIELD3), new Text("Func3=Output3"));
	}
	
	
	//
	//	Private/helper methods
	//
	private OutputSet newOutputSet()
	{
		return new OutputSet(FIELD_NAMES, DELIMITER);
	}	
}
