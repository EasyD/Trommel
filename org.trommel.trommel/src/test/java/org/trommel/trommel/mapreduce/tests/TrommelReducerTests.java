/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.mapreduce.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Test;
import org.trommel.trommel.mapreduce.TrommelMapper;
import org.trommel.trommel.mapreduce.TrommelReducer;


//
//	Unit tests for the org.trommel.trommel.mapreduce.TrommelMapper class
//
public class TrommelReducerTests 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String LOGGING_LEVEL_CONFIG_PROP = "TrommelLogLevel";
	private static final String SCRIPT_CONFIG_PROP = "TrommelScript";
	
	// Reduce phase fields and values values
	private static final String FIELD1 = "Field1";
	private static final String FIELD1_VALUE1 = "15.0";
	private static final String FIELD2_VALUE1 = "20.0";
	private static final String FIELD3_VALUE1 = "25.0";

	
	//
	//	Tests
	//

	@Test(expected=IOException.class)
	public void testBadScript() 
		throws IOException 
	{
		TrommelReducer reducer = new TrommelReducer();
		ReduceDriver<Text, Text, Text, Text> reduceDriver = new ReduceDriver<Text, Text, Text, Text>(reducer);
		Configuration config = new Configuration(false);

		// Set up a Hadoop Configuration object for WARN logging and to point at a test TrommelScript file
		config.set(LOGGING_LEVEL_CONFIG_PROP, "WARN");
		config.set(SCRIPT_CONFIG_PROP, "src/test/resources/scripts/BadScript.trommel");
		
		// Run a TrommelMapper with a invalid TrommelScript loaded from the file path above	
		reduceDriver.withInput(new Text(FIELD1), new LinkedList<Text>())
						.withConfiguration(config)
						.run();
	}
	
	@Test
	public void testProfileDataExplicitFunc() 
		throws IOException 
	{
		TrommelMapper mapper = new TrommelMapper();
		TrommelReducer reducer = new TrommelReducer();
		MapReduceDriver<LongWritable, Text, Text, Text, Text, Text> driver = new MapReduceDriver<LongWritable, Text, Text, Text, Text, Text>(mapper, reducer);
		Configuration config = new Configuration(false);
		
		// Set up a Hadoop Configuration object for DEBUG logging and to point at a test TrommelScript file
		config.set(LOGGING_LEVEL_CONFIG_PROP, "DEBUG");
		config.set(SCRIPT_CONFIG_PROP, "src/test/resources/scripts/ProfileDataExplicitFunc.trommel");
		
		// Execute driver
		List<Pair<Text, Text>> output = driver.withInput(new LongWritable(1), new Text(FIELD1_VALUE1 + "\t" + FIELD2_VALUE1 + "\t" + FIELD3_VALUE1))
												.withConfiguration(config)
												.run();
		
		assertEquals(3, output.size());
	}
	
	@Test
	public void testReportDataExportAndStore() 
		throws IOException 
	{
		TrommelMapper mapper = new TrommelMapper();
		TrommelReducer reducer = new TrommelReducer();
		MapReduceDriver<LongWritable, Text, Text, Text, Text, Text> driver = new MapReduceDriver<LongWritable, Text, Text, Text, Text, Text>(mapper, reducer);
		Configuration config = new Configuration(false);
		
		// Set up a Hadoop Configuration object for WARN logging and to point at a test TrommelScript file
		config.set(LOGGING_LEVEL_CONFIG_PROP, "WARN");
		config.set(SCRIPT_CONFIG_PROP, "src/test/resources/scripts/ReportDataExportAndStore.trommel");
		
		// Execute driver
		List<Pair<Text, Text>> output = driver.withInput(new LongWritable(1), new Text(FIELD1_VALUE1 + "\t" + FIELD2_VALUE1 + "\t" + FIELD3_VALUE1))
												.withConfiguration(config)
												.run();
		
		assertEquals(2, output.size());
	}
}
