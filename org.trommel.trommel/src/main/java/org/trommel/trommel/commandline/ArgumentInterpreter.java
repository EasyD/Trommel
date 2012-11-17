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
package org.trommel.trommel.commandline;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Level;


/**
 *	Trommel class responsible for interpreting command line switches/arguments to control Trommel execution.  
 */
public class ArgumentInterpreter 
{
	//
	//	Private Members
	//
	
	private ExecutionMode executionMode = ExecutionMode.ProcessScript;
	private LinkedList<String> errorMessages = new LinkedList<String>();
	private Level loggingLevel = Level.INFO;
	private String trommelScriptFilePath = null;
	private int numOfReducers = 4;
	
	
	//
	//	Getter/setters
	//

	/**
	 * @return The {@link org.apache.log4j.Level} to use for Trommel execution.
	 */
	public Level getLoggingLevel()
	{
		return loggingLevel;
	}
	
	/**
	 * @return The user-specified file path to the TrommelScript file.
	 */
	public String getTrommelScriptFilePath()
	{
		return trommelScriptFilePath;
	}
	
	/**
	 * @return The list of switch/argument error messages to report to the user.
	 */
	public List<String> getErrorMessages()
	{
		return errorMessages;
	}
	
	/**
	 * @return The number of Reducers to use for the Reduce phase.
	 */
	public int getNumOfReducers()
	{
		return numOfReducers;
	}
	
	
	//
	//	Public methods
	//
	
	/**
	 * Interpret/process the collection of command line switches/arguments.
	 * 
	 * @param args Array of {@link java.lang.String} instances passed to the {@link org.trommel.trommel.mapreduce.TrommelDriver}
	 * class' main() method.
	 * @return The {@link ExecutionMode} corresponding to the results of argument processing.
	 */
	public ExecutionMode interpretArguments(String[] args)
	{
		// Have to have at least 1 switch/argument and can have no more than four arguments
		if(args.length == 0 || args.length > 4)
		{
			// Houston, we have a problem
			errorMessages.addLast("Incorrent number of switches/arguments.");
			
			executionMode = ExecutionMode.ArgumentError;
		}
		else
		{
			// Correct number of switches/arguments
			for (int i = 0; i < args.length; ++i)
			{
				if (args[i].equals("-v") || args[i].equals("-version"))
				{
					handleVersion();
				}
				else if (args[i].equals("-h") || args[i].equals("-help"))
				{
					handleHelp();
				}
				else if (args[i].equals("-c") || args[i].equals("-check"))
				{
					handleValidation();
				}
				else if (args[i].equals("-d") || args[i].equals("-w") ||
						 args[i].equals("-debug") || args[i].equals("-warn"))
				{
					handleLogging(args[i]);
				}
				else if (args[i].equals("-r") || args[i].equals("-reducers"))
				{
					if (++i != args.length)
					{
						handleReducers(args[i]);
					}
					else
					{
						// Reducers switch is the last thing on the command line with no argument
						errorMessages.addLast("The -r/-reducers switch requires an argument.");
						
						executionMode = ExecutionMode.ArgumentError;
					}
				}
				else if (trommelScriptFilePath == null)
				{
					// The first arg that doesn't fit above is treated at the file path to the TrommelScript file
					trommelScriptFilePath = args[i];
				}
				else
				{
					// Houston, we have a problem
					errorMessages.addLast(String.format("Switch/argument %1$s not recognized.", args[i]));
					
					executionMode = ExecutionMode.ArgumentError;
				}
			}
		}
		
		//	If were executing or validating a TrommelScript, ensure we got a file path
		if ((executionMode == ExecutionMode.ProcessScript || executionMode == ExecutionMode.Validation) && 
			 trommelScriptFilePath == null)
		{
			// Houston, we've got a problem
			errorMessages.addLast("A TrommelScript file path is required.");
			
			executionMode = ExecutionMode.ArgumentError;
		}
		
		// Add common message in the case of an error
		if (executionMode == ExecutionMode.ArgumentError)
		{
			errorMessages.addLast("Use -help switch for details on usage.");
		}
		
		return executionMode;
	}
	
	
	//
	//	Private/helper methods
	//
	
	private void handleVersion()
	{
		if (executionMode == ExecutionMode.ProcessScript)
		{
			executionMode = ExecutionMode.Version;
		}
		else
		{
			errorMessages.addLast("Version switch cannot be used with other command line switches.");
			
			executionMode = ExecutionMode.ArgumentError;
		}
	}
	
	private void handleHelp()
	{
		if (executionMode == ExecutionMode.ProcessScript)
		{
			executionMode = ExecutionMode.Help;
		}
		else
		{
			errorMessages.addLast("Help switch cannot be used with other command line switches.");
			
			executionMode = ExecutionMode.ArgumentError;
		}
	}
	
	private void handleValidation()
	{
		if (executionMode == ExecutionMode.ProcessScript)
		{
			executionMode = ExecutionMode.Validation;
		}
		else
		{
			errorMessages.addLast("Validation switch cannot be used with other command line switches.");
			
			executionMode = ExecutionMode.ArgumentError;
		}
	}

	private void handleLogging(String arg)
	{
		if (executionMode == ExecutionMode.ProcessScript)
		{
			// Since INFO is the default, we only care about DEBUG and WARN
			if (arg.equals("-d") || arg.equals("-debug"))
			{
				loggingLevel = Level.DEBUG;
			}
			else
			{
				loggingLevel = Level.WARN;
			}
		}
		else if (executionMode != ExecutionMode.ArgumentError)
		{
			// Handle case where user has specified logging and a switch like -version on command line
			errorMessages.addLast("Logging switch cannot be used with -v, -h, or -c command line switches.");
		
			executionMode = ExecutionMode.ArgumentError;
		}
	}
	
	private void handleReducers(String numOfReducers)
	{
		try
		{
			this.numOfReducers = Integer.parseInt(numOfReducers);
			
			if (this.numOfReducers < 1)
			{
				errorMessages.addLast("Argument to -r/-reducers switch must be 1 or more.");

				executionMode = ExecutionMode.ArgumentError;
			}
		}
		catch (NumberFormatException nfe)
		{
			errorMessages.addLast(String.format("An invalid argument of '%1$s' was passed with the -r/reducers switch.", 
					                            numOfReducers));
			
			executionMode = ExecutionMode.ArgumentError;
		}
	}
}