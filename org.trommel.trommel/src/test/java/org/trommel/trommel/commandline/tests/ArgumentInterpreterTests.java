/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.commandline.tests;

import static org.junit.Assert.*;

import org.apache.log4j.Level;
import org.junit.Test;
import org.trommel.trommel.commandline.ArgumentInterpreter;
import org.trommel.trommel.commandline.ExecutionMode;

//
//	Unit tests for the org.trommel.trommel.commandline.ArgumentInterpreter class
//
public class ArgumentInterpreterTests 
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
	public void testThreeSwitchesError() 
	{
		ArgumentInterpreter interpreter = new ArgumentInterpreter();
		String[] args = new String[3];
		
		args[0] = "-d";
		args[1] = FILE_PATH;
		args[2] = "-h";
		
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
