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
import org.junit.Test;
import org.trommel.trommel.mapreduce.TrommelMapper;


//
//	Unit tests for the org.trommel.trommel.mapreduce.TrommelMapper class
//
public class TrommelMapperTests 
{	
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String LOGGING_LEVEL_CONFIG_PROP = "TrommelLogLevel";
	private static final String SCRIPT_CONFIG_PROP = "TrommelScript";

	
	//
	//	Tests
	//
	
	@Test(expected=IOException.class)
	public void testBadScript() 
		throws IOException 
	{
		TrommelMapper mapper = new TrommelMapper();
		MapDriver<LongWritable, Text, Text, Text> mapDriver = new MapDriver<LongWritable, Text, Text, Text>(mapper);
		Configuration config = new Configuration(false);
		
		// Set up a Hadoop Configuration object for WARN logging and to point at a test TrommelScript file
		config.set(LOGGING_LEVEL_CONFIG_PROP, "WARN");
		config.set(SCRIPT_CONFIG_PROP, "src/test/resources/scripts/BadScript.trommel");
		
		// Run a TrommelMapper with a invalid TrommelScript loaded from the file path above	
		mapDriver.withInput(new LongWritable(1), new Text("12\tFoo\tBar"))
					.withConfiguration(config)
					.run();
	}
	
	@Test(expected=IOException.class)
	public void testBadRecord() 
		throws IOException 
	{
		TrommelMapper mapper = new TrommelMapper();
		MapDriver<LongWritable, Text, Text, Text> mapDriver = new MapDriver<LongWritable, Text, Text, Text>(mapper);
		Configuration config = new Configuration(false);
		
		// Set up a Hadoop Configuration object for WARN logging and to point at a test TrommelScript file
		config.set(LOGGING_LEVEL_CONFIG_PROP, "WARN");
		config.set(SCRIPT_CONFIG_PROP, "src/test/resources/scripts/ProfileDataExplicitFunc.trommel");
		
		// Run a TrommelMapper with data that doesn't not comply with the LOAD DATA statement loaded from the file
		// above
		mapDriver.withInput(new LongWritable(1), new Text("12\tFoo"))
					.withConfiguration(config)
					.run();
	}
	
	@Test
	public void testProfileDataExplicitFunc() 
		throws IOException 
	{
		TrommelMapper mapper = new TrommelMapper();
		MapDriver<LongWritable, Text, Text, Text> mapDriver = new MapDriver<LongWritable, Text, Text, Text>(mapper);
		Configuration config = new Configuration(false);
		
		// Set up a Hadoop Configuration object for DEBUG logging and to point at a test TrommelScript file
		config.set(LOGGING_LEVEL_CONFIG_PROP, "DEBUG");
		config.set(SCRIPT_CONFIG_PROP, "src/test/resources/scripts/ProfileDataExplicitFunc.trommel");
		
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

	@Test
	public void testReportDataExportAndStore() 
		throws IOException 
	{
		TrommelMapper mapper = new TrommelMapper();
		MapDriver<LongWritable, Text, Text, Text> mapDriver = new MapDriver<LongWritable, Text, Text, Text>(mapper);
		Configuration config = new Configuration(false);
		
		// Set up a Hadoop Configuration object for INFO logging and to point at a test TrommelScript file
		config.set(LOGGING_LEVEL_CONFIG_PROP, "INFO");
		config.set(SCRIPT_CONFIG_PROP, "src/test/resources/scripts/ReportDataExportAndStore.trommel");
		
		// Run a TrommelMapper with data that fits the LOAD DATA statement for the script above	
		List<Pair<Text, Text>> output = mapDriver.withInput(new LongWritable(1), new Text("12\tFoo\tBar"))
													.withConfiguration(config)
													.run();

		// Verify output - script does not report on Field3
		assertEquals(2, output.size());
		
		ListIterator<Pair<Text, Text>> iterator = output.listIterator();
		
		while(iterator.hasNext())
		{
			Pair<Text, Text> outputPair = iterator.next();
			
			if (outputPair.getFirst().toString().equalsIgnoreCase("field1"))
			{
				assertEquals("DataReporter=12", outputPair.getSecond().toString().trim());
			}
			else if (outputPair.getFirst().toString().equalsIgnoreCase("field2"))
			{
				assertEquals("DataReporter=Foo", outputPair.getSecond().toString().trim());
			}
			else
			{
				fail("Unknown Mapper output.");
			}
		}
	}
	
	@Test
	public void testSampleDataExportAndStore() 
		throws IOException 
	{
		TrommelMapper mapper = new TrommelMapper();
		MapDriver<LongWritable, Text, Text, Text> mapDriver = new MapDriver<LongWritable, Text, Text, Text>(mapper);
		Configuration config = new Configuration(false);
		int recordsWritten = 0;
		
		// Set up a Hadoop Configuration object for INFO logging and to point at a test TrommelScript file
		config.set(LOGGING_LEVEL_CONFIG_PROP, "INFO");
		config.set(SCRIPT_CONFIG_PROP, "src/test/resources/scripts/SampleDataExportAndStore.trommel");
		
		// Run a TrommelMapper 300 times and accumulate the number of records written
		for (int i = 0; i < 300; ++i)
		{
			List<Pair<Text, Text>> output = mapDriver.withInput(new LongWritable(1), new Text("12\tFoo\tBar"))
														.withConfiguration(config)
														.run();
			
			if (output.size() > 0)
			{
				++recordsWritten;
			}
		}

		// Verify output - script is 75% sample rate, should be within 210 and 240 records, inclusive
		if (recordsWritten < 210 || recordsWritten > 240 )
		{
			fail(String.format("%1$d records written, exceeds expected range of 210-240, inclusive.", recordsWritten));
		}
	}	
}
