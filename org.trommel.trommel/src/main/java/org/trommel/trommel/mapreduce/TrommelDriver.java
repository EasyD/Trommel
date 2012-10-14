/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.mapreduce;

import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.trommel.trommel.commandline.ArgumentInterpreter;
import org.trommel.trommel.scripting.interpreters.FrontEndInterpreter;
import org.trommel.trommel.scripting.lexer.Lexer;
import org.trommel.trommel.scripting.node.Start;
import org.trommel.trommel.scripting.parser.Parser;


/**
 * The Trommel class containing the main() method invoked by Hadoop via the Trommel command line.
 */
public class TrommelDriver 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String LOGGING_LEVEL_CONFIG_PROP = "TrommelLogLevel";
	private static final String SCRIPT_CONFIG_PROP = "TrommelScript";

	//
	//	Private members
	//
	
	private static final Logger logger = Logger.getLogger(TrommelMapper.class);

	
	//
	//	Public methods
	//
	
	public static void main(String[] args)
		throws Exception
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		int exitCode = 0;	// Normal termination
		
		switch(interpreter.interpretArguments(args))
		{
			case Help:
				executeHelp();
				break;
			case Version:
				executeVersion();
				break;
			case Validation:
				executeValidation(interpreter.getTrommelScriptFilePath());
				break;
			case ProcessScript:
				exitCode = processScript(interpreter.getLoggingLevel(), interpreter.getTrommelScriptFilePath());
				break;
			default:
				// Argument error, print error messages
				List<String> errorMessages = interpreter.getErrorMessages();
				
				for(String errorMessage : errorMessages)
				{
					System.out.println(errorMessage);
				}				
				break;
		}

		System.exit(exitCode);		
	}	
	
	
	//
	//	Private/helper methods
	//
	
	private static void executeHelp()
	{
		// TODO - Implement incrementing version from automated builds
		// Print help menu to std out
		System.out.println("\nTrommel version 1.0\n");		
		System.out.println("USAGE: Trommel [switch] [file] [switch].");
		System.out.println("   switches:");
		System.out.println("      -c, -check - Run syntax check on specified file. Cannot be used with other switches.");
		System.out.println("      -d, -debug - Log at the DEBUG level for the specified file, INFO is the default. Cannot be used with other switches.");
		System.out.println("      -h, -help - Display this help message. Cannot be used with other switches.");
		System.out.println("      -v, -version - Display Trommel version. Cannot be used with other switches.");
		System.out.println("      -w, -warn - Log at the WARN level for the specified file, INFO is the default. Cannot be used with other switches.");
	}
	
	private static void executeVersion()
	{
		// TODO - Implement incrementing version from automated builds
		// Print Trommel version to std out
		System.out.println("Trommel version is 1.0");
	}
	
	private static void executeValidation(String trommelScriptFilePath)
	{
		// Provide syntax/semantic checking for the trommel script
		// TODO - Implement Validation logic
		System.out.println("TrommelDriver.executeValidation() called.");
	}
	
	private static int processScript(Level logLevel, String trommelScriptFilePath) 
		throws Exception
	{
		int exitCode = 0;
		FileSystem fileSystem = null;
		Path cachedScript = null;
		
		logger.setLevel(logLevel);
		
		try
		{
			logger.info(String.format("Loading, parsing, and interpreting TommelScript file %1$s", trommelScriptFilePath));
			Lexer lexer = new Lexer(new PushbackReader(new FileReader(trommelScriptFilePath), 4096));
			Parser parser = new Parser(lexer);
			Start ast = parser.parse();
			FrontEndInterpreter interpreter = new FrontEndInterpreter(logger);
			
			ast.apply(interpreter);	
			
			logger.debug("Creating Job object");
			Job job = new Job();
			
			job.setJarByClass(TrommelDriver.class);

			// Copy TrommelScript file from local file system to HDFS and added to distributed cache
			fileSystem = FileSystem.get(job.getConfiguration());
			Path src = new Path(trommelScriptFilePath);
			cachedScript = new Path(String.format("/tmp/%1$s_%2$s", src.getName(), UUID.randomUUID().toString()));
			
			fileSystem.copyFromLocalFile(src, cachedScript);
			logger.debug(String.format("Moved TrommelScript file to HDFS as %1$s.", cachedScript.toString()));

			logger.debug("Adding TrommelScript file to DistibutedCachce.");
			DistributedCache.addCacheFile(new URI(cachedScript.toString()), job.getConfiguration());
			
			logger.debug(String.format("Setting LOGGING_LEVEL_CONFIG_PROP to %1$s", logLevel.toString()));
			job.getConfiguration().set(LOGGING_LEVEL_CONFIG_PROP, logLevel.toString());

			// Specify HDFS input/output locations
			logger.debug(String.format("Calling FileInputFormat.addInputPath() with %1$s.", interpreter.getHdfsInputFilePath()));
			FileInputFormat.addInputPath(job, new Path(interpreter.getHdfsInputFilePath()));
	
			logger.debug(String.format("Calling FileOutputFormat.setOutputPath() with %1$s.", interpreter.getHdfsOutputFilePath()));
			FileOutputFormat.setOutputPath(job, new Path(interpreter.getHdfsOutputFilePath()));

			// Hadoop setup
			job.setMapperClass(TrommelMapper.class);
			job.setReducerClass(TrommelReducer.class);
			
			job.setOutputKeyClass(Text.class);			
			job.setOutputValueClass(Text.class);
			
			logger.debug("Running job");
			if (!job.waitForCompletion(true))
			{
				exitCode = 1;
			}
			else if (interpreter.getLocalFilePath() != null)
			{		
				// User would like data exported to local file system
				logger.debug(String.format("Exporting Trommel output from %1$s to %2$s.", 
						                   interpreter.getHdfsOutputFilePath(), interpreter.getLocalFilePath()));
				Path mergeFilePath = new Path(String.format("/tmp/%1$s", UUID.randomUUID()));
				FSDataOutputStream mergeFileStream = fileSystem.create(mergeFilePath);
				Path localFilePath = new Path (interpreter.getLocalFilePath());
				FileStatus[] outputFileStatuses = fileSystem.listStatus(new Path(interpreter.getHdfsOutputFilePath()));
				FSDataInputStream outputFileStream = null;
				
				try
				{
					// Loop through the output, merging any reducer output file for export to local file system
					for (FileStatus outputFileStatus : outputFileStatuses)
					{
						if (!outputFileStatus.isDir() && outputFileStatus.getPath().getName().contains("part-r"))
						{
							logger.debug(String.format("Merging file %1$s into local file system output.", 
									                   outputFileStatus.getPath().toString()));
							
							outputFileStream = fileSystem.open(outputFileStatus.getPath());
							byte[] buffer = new byte[(int)outputFileStatus.getLen()];
							
							outputFileStream.read(buffer);
							
							mergeFileStream.write(buffer);
							
							outputFileStream.close();
						}
					}
				}
				finally
				{
					if (mergeFileStream != null)
					{
						mergeFileStream.close();
						fileSystem.copyToLocalFile(mergeFilePath, localFilePath);
						fileSystem.delete(mergeFilePath, true);
					}
				}
			}
		}
		finally
		{
			try
			{
				if (fileSystem != null)
				{
					// Clean up the cached file
					logger.debug(String.format("Deleting cached TrommelScript file %1$s", cachedScript.toString()));
					fileSystem.delete(cachedScript, true);
				}
			}
			catch(IOException ioe)
			{
				// Couldn't delete file for some reason, alert user
				logger.error(String.format("Exception encountered deleting cached TommelScript file %1$s. Error message: %2$s", cachedScript.toString(), ioe.getMessage()));
			}
		}
				
		return exitCode;
	}
}