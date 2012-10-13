/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.scripting.interpreters.tests;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.trommel.trommel.scripting.interpreters.FrontEndInterpreter;
import org.trommel.trommel.scripting.interpreters.MapInterpreter;
import org.trommel.trommel.scripting.lexer.Lexer;
import org.trommel.trommel.scripting.lexer.LexerException;
import org.trommel.trommel.scripting.node.Start;
import org.trommel.trommel.scripting.parser.Parser;
import org.trommel.trommel.scripting.parser.ParserException;


//
//	Unit tests for the org.trommel.trommel.mapreduce.interpreters.FrontEndInterpreter class
//
@SuppressWarnings("unused")
public class FrontEndInterpreterTests 
{
	//
	//	Private members
	//
	private Logger logger = null;
	

	//
	//	Setup/Tear-down
	//
	
	@Before
	public void initialization()
	{
		logger = Mockito.mock(Logger.class);
		
		Mockito.when(logger.isDebugEnabled()).thenReturn(true);
	}
	
	
	//
	//	Tests
	//
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullLogger()
	{
		FrontEndInterpreter interpreter = new FrontEndInterpreter(null);
	}
	
	@Test
	public void testWriteToLocalFileSystemFalse()
	{
		FrontEndInterpreter interpreter = new FrontEndInterpreter(logger);
		
		assertFalse(interpreter.writeToLocalFileSystem());
	}
	
	@Test
	public void testWriteHdfsAndLocal() 
		throws ParserException, LexerException, IOException
	{
		Start ast = buildAST("src/test/resources/scripts/SampleDataExportAndStore.trommel");
		FrontEndInterpreter interpreter = new FrontEndInterpreter(logger);
		
		ast.apply(interpreter);	

		// Validate expected HDFS and local file info
		assertTrue(interpreter.writeToLocalFileSystem());
		assertEquals ("/usr/local/FooBar", interpreter.getHdfsInputFilePath());
		assertEquals ("Hello", interpreter.getHdfsOutputFilePath());
		assertEquals ("Foo", interpreter.getLocalFilePath());
		assertEquals ("Bar", interpreter.getLocalFileName());
	}
	
	@Test
	public void testWriteLocalOnly() 
		throws ParserException, LexerException, IOException
	{
		Start ast = buildAST("src/test/resources/scripts/SampleDataExport.trommel");
		FrontEndInterpreter interpreter = new FrontEndInterpreter(logger);
		
		ast.apply(interpreter);	

		// Validate expected HDFS and local file info
		assertTrue(interpreter.writeToLocalFileSystem());
		assertEquals ("/usr/local/FooBar", interpreter.getHdfsInputFilePath());
		assertEquals ("/tmp/Trommel", interpreter.getHdfsOutputFilePath());
		assertEquals ("Foo", interpreter.getLocalFilePath());
		assertEquals ("Bar", interpreter.getLocalFileName());
	}
		
	
	//
	//	Private/helper methods
	//
	
	private Start buildAST(String testScriptPath) 
		throws ParserException, LexerException, IOException
	{
		Lexer lexer = new Lexer(new PushbackReader(new FileReader(testScriptPath), 4096));
		Parser parser = new Parser(lexer);
		
		return parser.parse();
	}
}
