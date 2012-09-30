/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.scripting.interpreters.MapInterpreter;


/**
 * Trommel's {@link org.apache.hadoop.mapreduce.Mapper} class that is invoked from the Trommel command line.
 */
public class TrommelMapper 
	extends Mapper<LongWritable, Text, Text, Text>
{
	//
	//	Private members
	//
	private MapInterpreter interpreter = null;

	
	//
	//	Public methods
	//
	
	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Mapper#map(KEYIN, VALUEIN, org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException
	{
		try
		{
//			MapRecord record = interpreter.handleRecord(value);
//			
//			record.serialize(context);
		}
		catch(Exception e)
		{
			// TODO - Add exception handling here
		}
	}

	//
	//	Protected methods	
	//
	
	@Override
	protected void setup(Context context)
		throws IOException, InterruptedException
	{
		// TODO - Wire up interpreter to SableCC AST
		interpreter = new MapInterpreter();
	}
}
