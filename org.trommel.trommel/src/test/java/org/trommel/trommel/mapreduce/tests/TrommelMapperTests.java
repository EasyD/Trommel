/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.mapreduce.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;
import org.trommel.trommel.mapreduce.TrommelMapper;
import org.trommel.trommel.scripting.interpreters.MapInterpreter;


//
//	Unit tests for the org.trommel.trommel.mapreduce.TrommelMapper class
//
public class TrommelMapperTests 
{
	// 
	//	Private members
	//	
	
	private MapDriver<LongWritable, Text, Text, Text> mapDriver = null;
	
	
	//
	//	Setup/Tear-down
	//
	
	@Before
	public void initialization()
	{
		TrommelMapper mapper = new TrommelMapper();
		
		mapDriver = new MapDriver<LongWritable, Text, Text, Text>(mapper);
	}
	
	
	//
	//	Tests
	//
	
	@Test
	public void test() 
		throws IOException 
	{
		List<Pair<Text, Text>> output = mapDriver.withInput(new LongWritable(1), new Text("12\tFoo\t14"))
													.run();
		
		// TODO - Need to fix when final interpreters are in place.
//		assertEquals("Column3", output.get(0).getFirst().toString());
//		assertEquals("Max=14" + MapInterpreter.DELIMITER + 
//				     "Min=14" + MapInterpreter.DELIMITER + 
//				     "Distinct=14", output.get(0).getSecond().toString());
	}

}
