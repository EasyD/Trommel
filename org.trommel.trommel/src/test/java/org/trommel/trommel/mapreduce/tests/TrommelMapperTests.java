/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.mapreduce.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;
import org.trommel.trommel.mapreduce.TrommelMapper;


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
		// Set up a Hadoop Configuration object to point at a test TrommelScript file
		Configuration config = new Configuration(false);
		
		config.set("TrommelScript", "src/test/resources/scripts/ProfileDataExplicitFunc.trommel");
		
		// Run a TrommelMapper with data that fits the LOAD DATA statement for the script above	
		List<Pair<Text, Text>> output = mapDriver.withInput(new LongWritable(1), new Text("12\tFoo\tBar"))
													.withConfiguration(config)
													.run();

		// Verify output
		assertEquals(3, output.size());
		
		ListIterator<Pair<Text, Text>> iterator = output.listIterator();
		
		while(iterator.hasNext())
		{
			Pair<Text, Text> outputPair = iterator.next();
			
			if (outputPair.getFirst().toString().equalsIgnoreCase("field1"))
			{
				assertEquals("Min=12*|*Max=12*|*Variability=12*|*Distinct=12*|*Linearity=12", outputPair.getSecond().toString().trim());
			}
			else if (outputPair.getFirst().toString().equalsIgnoreCase("field2"))
			{
				assertEquals("Variability=Foo*|*Distinct=Foo*|*Linearity=Foo", outputPair.getSecond().toString().trim());
			}
			else if (outputPair.getFirst().toString().equalsIgnoreCase("field3"))
			{
				assertEquals("Variability=Bar*|*Distinct=Bar*|*Linearity=Bar", outputPair.getSecond().toString().trim());
			}
			else
			{
				fail("Unknown Mapper output.");
			}
		}
	}

}
