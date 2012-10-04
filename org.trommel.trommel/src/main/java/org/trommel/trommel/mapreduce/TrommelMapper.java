/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.mapreduce;

import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.RecordParser;
import org.trommel.trommel.controllers.MapController;
import org.trommel.trommel.scripting.interpreters.MapInterpreter;
import org.trommel.trommel.scripting.lexer.Lexer;
import org.trommel.trommel.scripting.node.Start;
import org.trommel.trommel.scripting.parser.Parser;


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
	private RecordParser recordParser = null;
	private MapController controller = null;

	
	//
	//	Public methods
	//

	/**
	 * Process a single record read from the data set as part of the Hadoop Map phase of processing.
	 */
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException
	{
		try
		{
			// Parse current record based on TrommelScript specification
			MapRecord record = recordParser.parse(value.toString());
			
			// Have specified functions/reporters process record 
			controller.handleMapRecord(record);
			
			// Serialize results to Hadoop Context
			record.serialize(context);
		}
		catch(Exception e)
		{
			StringBuffer buffer = new StringBuffer();
			
			buffer.append(e.getClass().getSimpleName());
			buffer.append(" encountered processing data set record. Exception message:\n");
			buffer.append(e.getMessage());
			
			throw new IOException(buffer.toString());
		}
	}

	
	//
	//	Protected methods	
	//
	
	/**
	 * Parse the user-authored TrommelScript and configure {@link org.trommel.trommel.RecordParser} and
	 * {@link org.trommel.trommel.controllers.MapController} instances based on the TrommelScript.
	 */
	@Override
	protected void setup(Context context)
		throws IOException, InterruptedException
	{
		try
		{
			// Get Task Appender Logger and configure based on command line input 
			Logger logger = Logger.getLogger(TrommelMapper.class);
			
			// TODO - Configure logger based on environment property
			
			
			
			
			// Load, parse, and interpret TrommelScript for Map phase of processing
			String trommelScript = context.getConfiguration().get("TrommelScript");
			Lexer lexer = new Lexer(new PushbackReader(new FileReader(trommelScript), 4096));
			Parser parser = new Parser(lexer);
			Start ast = parser.parse();
			
			interpreter = new MapInterpreter(logger);
		
			ast.apply(interpreter);	
			
			// Grab RecordParser and MapController instances based on TrommelScript specification
			recordParser = interpreter.getRecordParser();
			controller = interpreter.getController();
		}
		catch (Exception e)
		{
			StringBuffer buffer = new StringBuffer();
			
			buffer.append(e.getClass().getSimpleName());
			buffer.append(" encountered parsing TrommelScript file. Exception message:\n");
			buffer.append(e.getMessage());
			
			throw new IOException(buffer.toString());
		}
	}
}
