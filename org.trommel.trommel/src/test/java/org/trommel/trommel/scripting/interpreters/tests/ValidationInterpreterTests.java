/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.scripting.interpreters.tests;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

import org.junit.Test;
import org.trommel.trommel.scripting.interpreters.ValidationInterpreter;
import org.trommel.trommel.scripting.lexer.Lexer;
import org.trommel.trommel.scripting.lexer.LexerException;
import org.trommel.trommel.scripting.node.Start;
import org.trommel.trommel.scripting.parser.Parser;
import org.trommel.trommel.scripting.parser.ParserException;

//
//	Unit tests for the org.trommel.trommel.mapreduce.interpreters.ValidationInterpreter class
//

public class ValidationInterpreterTests 
{
	//
	//	PROFILE DATA statement tests
	//
	
	@Test
	public void testProfileDataExplicitFunc()
		throws ParserException, LexerException, IOException 
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataExplicitFunc.trommel");
		ValidationInterpreter interpreter = new ValidationInterpreter();
		
		ast.apply(interpreter);	
		
		// Verify no semantic errors
		assertEquals(0, interpreter.getSemanticErrors().size());
	}
	
	@Test
	public void testProfileDataDuplicateLoadDataFields()
		throws ParserException, LexerException, IOException 
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataDuplicateLoadDataFields.trommel");
		ValidationInterpreter interpreter = new ValidationInterpreter();
		
		ast.apply(interpreter);	
		
		// Verify no semantic errors
		assertEquals(2, interpreter.getSemanticErrors().size());
		
		for (String errorMessage : interpreter.getSemanticErrors())
		{
			if (!errorMessage.equals("Field Field2 already specified in LOAD DATA statement.") && 
			    !errorMessage.equals("Field FIELD1 already specified in LOAD DATA statement."))
			{
				fail(String.format("Unexpected error message of %1$s", errorMessage));
			}
		}
	}

	@Test
	public void testProfileDataDuplicateProfileField()
		throws ParserException, LexerException, IOException 
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataDuplicateProfileField.trommel");
		ValidationInterpreter interpreter = new ValidationInterpreter();
		
		ast.apply(interpreter);	
		
		// Verify no semantic errors
		assertEquals(1, interpreter.getSemanticErrors().size());
		assertTrue(interpreter.getSemanticErrors().get(0).equals("Field FIELD2 already specified in PROFILE DATA statement."));
	}

	
	//
	//	REPORT DATA statement tests
	//
	
	@Test
	public void testReportDataExportAndStore() 
		throws ParserException, LexerException, IOException
	{
		Start ast = buildAST("src/test/resources/scripts/ReportDataExportAndStore.trommel");
		ValidationInterpreter interpreter = new ValidationInterpreter();
		
		ast.apply(interpreter);	
				
		// Verify no semantic errors
		assertEquals(0, interpreter.getSemanticErrors().size());
	}

	@Test
	public void testReportDataUnrecognizedReportField() 
		throws ParserException, LexerException, IOException
	{
		Start ast = buildAST("src/test/resources/scripts/ReportDataUnrecognizedReportField.trommel");
		ValidationInterpreter interpreter = new ValidationInterpreter();
		
		ast.apply(interpreter);	
				
		// Verify no semantic errors
		assertEquals(1, interpreter.getSemanticErrors().size());
		assertTrue(interpreter.getSemanticErrors().get(0).equals("Field FooBar was not specified in PROFILE DATA statement."));
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
