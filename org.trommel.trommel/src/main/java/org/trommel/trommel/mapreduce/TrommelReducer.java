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

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.trommel.trommel.controllers.ReduceController;
import org.trommel.trommel.scripting.interpreters.ReduceInterpreter;
import org.trommel.trommel.scripting.lexer.Lexer;
import org.trommel.trommel.scripting.node.Start;
import org.trommel.trommel.scripting.parser.Parser;

/**
 * Trommel's {@link org.apache.hadoop.mapreduce.Reducer} class that is invoked from the Trommel command line.
 */
public class TrommelReducer 
	extends Reducer<Text, Text, NullWritable, Text>
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
	private ReduceInterpreter interpreter = null;
	private ReduceController controller = null;
	private boolean headerPrinted = false;
	private String currentKey = "";

	
	//
	//	Public methods
	//	
	
	/**
	 * Process a single post-Map phase record as part of the Hadoop Reduce phase of processing.
	 */
	public void reduce(Text key, Iterable<Text> values, Context context)
		throws IOException, InterruptedException
	{
		String tempKey = key.toString();
		
		if (!tempKey.equalsIgnoreCase(currentKey))
		{
			// Reducer has moved to different key, grab new controller
			currentKey = tempKey;
			
			logger.info(String.format("TrommelReducer processing fieldName: %1$s.", currentKey));
				
			controller = interpreter.getController(currentKey);
		}

		// Print a header line once per Reduce instance
		if (!headerPrinted)
		{
			context.write(NullWritable.get(), new Text(controller.getHeader()));
		}
		
		// Process all the records for the current key (i.e., field name)
		for (Text value : values)
		{
			controller.handleReduceRecord(value.toString());
		}
		
		context.write(NullWritable.get(), new Text(String.format("%1$s\t%2$s", tempKey, controller.getFormattedResults())));
	}
	
	//
	//	Protected methods
	//
	
	@Override
	protected void setup(Context context)
		throws IOException, InterruptedException
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
			
			interpreter = new ReduceInterpreter(logger);
		
			ast.apply(interpreter);	

			logger.info("TrommelReducer's ReduceController configured, beginning Reduce phase record processing.");
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
