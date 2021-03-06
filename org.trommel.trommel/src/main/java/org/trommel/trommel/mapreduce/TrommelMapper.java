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
package org.trommel.trommel.mapreduce;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.Random;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Level;
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
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String LOGGING_LEVEL_CONFIG_PROP = "TrommelLogLevel";
	private static final String LOGGING_LEVEL_DEBUG = "DEBUG";
	private static final String LOGGING_LEVEL_INFO = "INFO";
	private static final String LOGGING_LEVEL_WARN = "WARN";
	private static final String SCRIPT_CONFIG_PROP = "TrommelScript";

	//
	//	Private members
	//
	
	private static final Logger logger = Logger.getLogger(TrommelMapper.class);
	private MapInterpreter interpreter = null;
	private RecordParser recordParser = null;
	private MapController controller = null;
	private int sampleRate = -1;
	private Random random = null;

	
	//
	//	Public methods
	//

	/**
	 * Process a single record read from the data set as part of the Hadoop Map phase of processing.
	 */
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException
	{
		if (interpreter.samplingData())
		{
			// TrommelScript contains PROFILE DATA or REPORT DATA STATEMENT
			if (random.nextInt(10000) <= sampleRate)
			{
				// Record is part of sample
				context.write(new Text(""), value);
			}
		}
		else
		{
			// TrommelScript contains PROFILE DATA or REPORT DATA STATEMENT
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
				
				logger.error(buffer.toString());
				
				throw new IOException(buffer.toString());
			}
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
		throws IOException
	{
		try
		{
			// Configure logger based on context config property
			String logLevel = context.getConfiguration().get(LOGGING_LEVEL_CONFIG_PROP, LOGGING_LEVEL_INFO);
						
			if (logLevel.equalsIgnoreCase(LOGGING_LEVEL_DEBUG))
			{
				logger.setLevel(Level.DEBUG);
			}
			else if (logLevel.equalsIgnoreCase(LOGGING_LEVEL_WARN))
			{
				logger.setLevel(Level.WARN);
			}
			else
			{
				// Default to INFO
				logger.setLevel(Level.INFO);
			}
						
			// Load, parse, and interpret TrommelScript for Map phase of processing
			Path[] localFilePaths = DistributedCache.getLocalCacheFiles(context.getConfiguration());
			String trommelScript = null;
			
			if (localFilePaths == null || localFilePaths.length == 0)
			{
				// If there is no file path in the cache, we're testing grab file from config
				trommelScript = context.getConfiguration().get(SCRIPT_CONFIG_PROP);

				logger.info(String.format("TrommelMapper parsing and interpreting TrommelScript %1$s.", 
                                          context.getConfiguration().get(SCRIPT_CONFIG_PROP)));
			}
			else
			{
				// Got a file in the cache, use it
				trommelScript = localFilePaths[0].toString();				

				logger.info(String.format("TrommelMapper parsing and interpreting TrommelScript %1$s.", 
	                                      localFilePaths[0].getName()));
			}
			
			Lexer lexer = new Lexer(new PushbackReader(new BufferedReader(new FileReader(trommelScript)), 4096));
			Parser parser = new Parser(lexer);
			Start ast = parser.parse();
			
			interpreter = new MapInterpreter(logger);
		
			ast.apply(interpreter);	
			
			if(interpreter.samplingData())
			{
				// Use a larger range of random numbers (i.e., 100% = 10000) for enhanced fidelity
				sampleRate = interpreter.getSampleRate() * 100;
				
				random = new Random();

				logger.info(String.format("Sampling data randomly at approximately %1$s percent, beginning Map phase record proessing.", 
						                  interpreter.getSampleRate()));
			}
			else
			{
				// Grab RecordParser and MapController instances based on TrommelScript specification
				recordParser = interpreter.getRecordParser();
				controller = interpreter.getController();

				logger.info("TrommelMapper's MapController and RecordParser configured, beginning Map phase record processing.");
			}
		}
		catch (Exception e)
		{
			StringBuffer buffer = new StringBuffer();
			
			buffer.append(e.getClass().getSimpleName());
			buffer.append(" encountered parsing TrommelScript file. Exception message:\n");
			buffer.append(e.getMessage());
			
			logger.error(buffer.toString());
			
			throw new IOException(buffer.toString());
		}
	}
}
