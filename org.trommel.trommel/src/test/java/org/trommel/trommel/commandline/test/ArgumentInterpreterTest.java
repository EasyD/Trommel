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
package org.trommel.trommel.commandline.test;

import static org.junit.Assert.*;

import org.apache.log4j.Level;
import org.junit.Test;
import org.trommel.trommel.commandline.ArgumentInterpreter;
import org.trommel.trommel.commandline.ExecutionMode;


//
//	Unit tests for the org.trommel.trommel.commandline.ArgumentInterpreter class
//
public class ArgumentInterpreterTest 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FILE_PATH = "/Foo/Bar/FooBar.trommel";

	
	//
	//	Tests
	//
	
	@Test
	public void testHelpSwitchOK() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[1];
		
		args[0] = "-help";
		
		assertEquals(ExecutionMode.Help, interpreter.interpretArguments(args));
	}

	
	@Test
	public void testHSwitchOK() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[1];
		
		args[0] = "-h";
		
		assertEquals(ExecutionMode.Help, interpreter.interpretArguments(args));
	}
	
	@Test
	public void testVersionSwitchOK() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[1];
		
		args[0] = "-version";
		
		assertEquals(ExecutionMode.Version, interpreter.interpretArguments(args));
	}

	
	@Test
	public void testVSwitchOK() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[1];
		
		args[0] = "-v";
		
		assertEquals(ExecutionMode.Version, interpreter.interpretArguments(args));
	}
	
	@Test
	public void testCheckSwitchOK() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[2];
		
		args[0] = "-check";
		args[1] = FILE_PATH;
		
		assertEquals(ExecutionMode.Validation, interpreter.interpretArguments(args));
	}
	
	@Test
	public void testCSwitchOK() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[2];
		
		args[0] = "-c";
		args[1] = FILE_PATH;
		
		assertEquals(ExecutionMode.Validation, interpreter.interpretArguments(args));
	}	
	
	@Test
	public void testDebugSwitchOK() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[2];
		
		// Have to have a file path for logging
		args[0] = "-debug";
		args[1] = FILE_PATH;
		
		assertEquals(ExecutionMode.ProcessScript, interpreter.interpretArguments(args));
		assertEquals(Level.DEBUG, interpreter.getLoggingLevel());
	}

	@Test
	public void testDSwitchOK() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[2];
		
		// Have to have a file path for logging
		args[0] = "-d";
		args[1] = FILE_PATH;
		
		assertEquals(ExecutionMode.ProcessScript, interpreter.interpretArguments(args));
		assertEquals(Level.DEBUG, interpreter.getLoggingLevel());
	}
	
	@Test
	public void testWarnSwitchOK() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[2];
		
		// Have to have a file path for logging
		args[0] = "-warn";
		args[1] = FILE_PATH;
		
		assertEquals(ExecutionMode.ProcessScript, interpreter.interpretArguments(args));
		assertEquals(Level.WARN, interpreter.getLoggingLevel());
	}

	@Test
	public void testWSwitchOK() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[2];
		
		// Have to have a file path for logging, order shouldn't matter
		args[0] = FILE_PATH;
		args[1] = "-w";
		
		assertEquals(ExecutionMode.ProcessScript, interpreter.interpretArguments(args));
		assertEquals(Level.WARN, interpreter.getLoggingLevel());
	}
	
	@Test
	public void testReducersSwitchOK()
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[3];
		
		// Have to have a file path for logging, order shouldn't matter
		args[0] = FILE_PATH;
		args[1] = "-reducers";
		args[2] = "3";
		
		assertEquals(ExecutionMode.ProcessScript, interpreter.interpretArguments(args));
		assertEquals(3, interpreter.getNumOfReducers());
	}
	
	@Test
	public void testRSwitchOK()
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[3];
		
		// Have to have a file path for logging, order shouldn't matter
		args[0] = FILE_PATH;
		args[1] = "-r";
		args[2] = "5";
		
		assertEquals(ExecutionMode.ProcessScript, interpreter.interpretArguments(args));
		assertEquals(5, interpreter.getNumOfReducers());
	}
	
	@Test
	public void TestRSwitchNegativeReducers()
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[3];
		
		// Have to have a file path for logging, order shouldn't matter
		args[0] = FILE_PATH;
		args[1] = "-r";
		args[2] = "-5";
		
		assertEquals(ExecutionMode.ArgumentError, interpreter.interpretArguments(args));
		assertEquals("Argument to -r/-reducers switch must be 1 or more.", interpreter.getErrorMessages().get(0));
	}

	@Test
	public void TestRSwitchInvalidIntString()
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[3];
		
		// Have to have a file path for logging, order shouldn't matter
		args[0] = FILE_PATH;
		args[1] = "-r";
		args[2] = "foo";
		
		assertEquals(ExecutionMode.ArgumentError, interpreter.interpretArguments(args));
		assertEquals("An invalid argument of 'foo' was passed with the -r/reducers switch.", interpreter.getErrorMessages().get(0));
	}

	@Test
	public void TestRSwitchLastOnCommandLine()
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[2];
		
		// Have to have a file path for logging, order shouldn't matter
		args[0] = FILE_PATH;
		args[1] = "-r";
		
		assertEquals(ExecutionMode.ArgumentError, interpreter.interpretArguments(args));
		assertEquals("The -r/-reducers switch requires an argument.", interpreter.getErrorMessages().get(0));
	}

	@Test
	public void testProcessScriptNoSwitchesOK() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[1];
		
		args[0] = FILE_PATH;
		
		assertEquals(ExecutionMode.ProcessScript, interpreter.interpretArguments(args));
		assertEquals(Level.INFO, interpreter.getLoggingLevel());
		assertEquals(FILE_PATH, interpreter.getTrommelScriptFilePath());
	}	

	@Test
	public void testZeroSwitchesError() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[0];
		
		assertEquals(ExecutionMode.ArgumentError, interpreter.interpretArguments(args));
		assertEquals(2, interpreter.getErrorMessages().size());
		assertEquals("Incorrent number of switches/arguments.", interpreter.getErrorMessages().get(0));
		assertEquals("Use -help switch for details on usage.", interpreter.getErrorMessages().get(1));
	}	

	@Test
	public void testFiveSwitchesError() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[5];
		
		args[0] = "-d";
		args[1] = FILE_PATH;
		args[2] = "-h";
		args[3] = FILE_PATH;
		args[4] = "-h";
		
		assertEquals(ExecutionMode.ArgumentError, interpreter.interpretArguments(args));
		assertEquals(2, interpreter.getErrorMessages().size());
		assertEquals("Incorrent number of switches/arguments.", interpreter.getErrorMessages().get(0));
		assertEquals("Use -help switch for details on usage.", interpreter.getErrorMessages().get(1));
	}	

	@Test
	public void testHelpAndVersionSwitchesError() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[2];
		
		args[0] = "-help";
		args[1] = "-v";
		
		assertEquals(ExecutionMode.ArgumentError, interpreter.interpretArguments(args));
		assertEquals(2, interpreter.getErrorMessages().size());
		assertEquals("Version switch cannot be used with other command line switches.", interpreter.getErrorMessages().get(0));
		assertEquals("Use -help switch for details on usage.", interpreter.getErrorMessages().get(1));
	}	

	@Test
	public void testCheckAndDebugSwitchesError() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[2];
		
		args[0] = "-c";
		args[1] = "-debug";
		
		assertEquals(ExecutionMode.ArgumentError, interpreter.interpretArguments(args));
		assertEquals(2, interpreter.getErrorMessages().size());
		assertEquals("Logging switch cannot be used with -v, -h, or -c command line switches.", interpreter.getErrorMessages().get(0));
		assertEquals("Use -help switch for details on usage.", interpreter.getErrorMessages().get(1));
	}	

	@Test
	public void testCheckAndHelpSwitchesError() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[2];
		
		args[0] = "-c";
		args[1] = "-h";
		
		assertEquals(ExecutionMode.ArgumentError, interpreter.interpretArguments(args));
		assertEquals(2, interpreter.getErrorMessages().size());
		assertEquals("Help switch cannot be used with other command line switches.", interpreter.getErrorMessages().get(0));
		assertEquals("Use -help switch for details on usage.", interpreter.getErrorMessages().get(1));
	}	

	@Test
	public void testCheckAndCSwitchesError() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[2];
		
		args[0] = "-check";
		args[1] = "-c";
		
		assertEquals(ExecutionMode.ArgumentError, interpreter.interpretArguments(args));
		assertEquals(2, interpreter.getErrorMessages().size());
		assertEquals("Validation switch cannot be used with other command line switches.", interpreter.getErrorMessages().get(0));
		assertEquals("Use -help switch for details on usage.", interpreter.getErrorMessages().get(1));
	}	

	@Test
	public void testDebugSwitchOnlyError() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[1];
		
		args[0] = "-debug";
		
		assertEquals(ExecutionMode.ArgumentError, interpreter.interpretArguments(args));
		assertEquals(2, interpreter.getErrorMessages().size());
		assertEquals("A TrommelScript file path is required.", interpreter.getErrorMessages().get(0));
		assertEquals("Use -help switch for details on usage.", interpreter.getErrorMessages().get(1));
	}	

	@Test
	public void testUnrecognizedSwitchError() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[2];
		
		args[0] = FILE_PATH;
		args[1] = FILE_PATH;
		
		assertEquals(ExecutionMode.ArgumentError, interpreter.interpretArguments(args));
		assertEquals(2, interpreter.getErrorMessages().size());
		assertEquals(String.format("Switch/argument '$1$s' not recognized.", FILE_PATH), interpreter.getErrorMessages().get(0));
		assertEquals("Use -help switch for details on usage.", interpreter.getErrorMessages().get(1));
	}	
}
