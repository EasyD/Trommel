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
package org.trommel.trommel.scripting.interpreters.test;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.trommel.trommel.scripting.interpreters.FrontEndInterpreter;
import org.trommel.trommel.scripting.lexer.Lexer;
import org.trommel.trommel.scripting.lexer.LexerException;
import org.trommel.trommel.scripting.node.Start;
import org.trommel.trommel.scripting.parser.Parser;
import org.trommel.trommel.scripting.parser.ParserException;


//
//	Unit tests for the org.trommel.trommel.mapreduce.interpreters.FrontEndInterpreter class
//
@SuppressWarnings("unused")
public class FrontEndInterpreterTest 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	
	private static final String DEFAULT_HDFS_FILE_PATH = "/tmp/Trommel";

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
		FrontEndInterpreter interpreter = new FrontEndInterpreter(null, DEFAULT_HDFS_FILE_PATH);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorEmptyPath()
	{
		FrontEndInterpreter interpreter = new FrontEndInterpreter(logger, " \t ");
	}
	
	@Test
	public void testWriteToLocalFileSystemFalse()
	{
		FrontEndInterpreter interpreter = new FrontEndInterpreter(logger, DEFAULT_HDFS_FILE_PATH);
		
		assertFalse(interpreter.writeToLocalFileSystem());
	}
	
	@Test
	public void testWriteHdfsAndLocal() 
		throws ParserException, LexerException, IOException
	{
		Start ast = buildAST("SampleDataExportAndStore.trommel");
		FrontEndInterpreter interpreter = new FrontEndInterpreter(logger, DEFAULT_HDFS_FILE_PATH);
		
		ast.apply(interpreter);	

		// Validate expected HDFS and local file info
		assertTrue(interpreter.samplingData());
		assertTrue(interpreter.writeToLocalFileSystem());
		assertEquals ("/usr/local/FooBar", interpreter.getHdfsInputFilePath());
		assertEquals ("Hello", interpreter.getHdfsOutputFilePath());
		assertEquals ("Foo", interpreter.getLocalFilePath());
	}
	
	@Test
	public void testWriteLocalOnly() 
		throws ParserException, LexerException, IOException
	{
		Start ast = buildAST("SampleDataExport.trommel");
		FrontEndInterpreter interpreter = new FrontEndInterpreter(logger, DEFAULT_HDFS_FILE_PATH);
		
		ast.apply(interpreter);	

		// Validate expected HDFS and local file info
		assertTrue(interpreter.writeToLocalFileSystem());
		assertEquals ("/usr/local/FooBar", interpreter.getHdfsInputFilePath());
		assertEquals ("/tmp/Trommel", interpreter.getHdfsOutputFilePath());
		assertEquals ("Foo", interpreter.getLocalFilePath());
	}
		
	
	//
	//	Private/helper methods
	//
	
	private Start buildAST(String testScriptPath) 
		throws ParserException, LexerException, IOException
	{
		Lexer lexer = new Lexer(new PushbackReader(new FileReader(getClass().getClassLoader().getResource(testScriptPath).getFile()), 4096));
		Parser parser = new Parser(lexer);
		
		return parser.parse();
	}
}
