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
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.trommel.trommel.controllers.ReduceProfileController;
import org.trommel.trommel.controllers.ReduceReportController;
import org.trommel.trommel.scripting.interpreters.MapInterpreter;
import org.trommel.trommel.scripting.interpreters.ReduceInterpreter;
import org.trommel.trommel.scripting.lexer.Lexer;
import org.trommel.trommel.scripting.lexer.LexerException;
import org.trommel.trommel.scripting.node.AAllBuiltinProfilers;
import org.trommel.trommel.scripting.node.ACustomFieldsTerminatedBy;
import org.trommel.trommel.scripting.node.ADefaultConfidence;
import org.trommel.trommel.scripting.node.ADefaultLinearity;
import org.trommel.trommel.scripting.node.ADefaultParenConfidence;
import org.trommel.trommel.scripting.node.ADefaultParenLinearity;
import org.trommel.trommel.scripting.node.ADistinctFunction;
import org.trommel.trommel.scripting.node.AEmptyFunction;
import org.trommel.trommel.scripting.node.AField;
import org.trommel.trommel.scripting.node.AMaxFunction;
import org.trommel.trommel.scripting.node.AMinFunction;
import org.trommel.trommel.scripting.node.AParmConfidence;
import org.trommel.trommel.scripting.node.AParmLinearity;
import org.trommel.trommel.scripting.node.AProfiledField;
import org.trommel.trommel.scripting.node.AReportDataStatement;
import org.trommel.trommel.scripting.node.AReportedField;
import org.trommel.trommel.scripting.node.AReqFunction;
import org.trommel.trommel.scripting.node.AVarFunction;
import org.trommel.trommel.scripting.node.Start;
import org.trommel.trommel.scripting.parser.Parser;
import org.trommel.trommel.scripting.parser.ParserException;


//
//	Unit tests for the org.trommel.trommel.mapreduce.interpreters.ReduceInterpreter class
//
@SuppressWarnings("unused")
public class ReduceInterpreterTests 
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
		ReduceInterpreter interpreter = new ReduceInterpreter(null);
	}
	
	@Test
	public void testGetReduceProfileControllerOK() 
		throws ParserException, LexerException, IOException
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataAllBuiltIn.trommel");
		ReduceInterpreter interpreter = Mockito.spy(new ReduceInterpreter(logger));
		
		ast.apply(interpreter);	
		
		assertTrue("Returned controller is not an instance of ReduceProfileController.", 
				   interpreter.getController("Field1") instanceof ReduceProfileController);
	}
	
	@Test
	public void testGetReduceProfileControllerConfParamOK() 
		throws ParserException, LexerException, IOException
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataExplicitFunc.trommel");
		ReduceInterpreter interpreter = Mockito.spy(new ReduceInterpreter(logger));
		
		ast.apply(interpreter);	
		
		assertTrue("Returned controller is not an instance of ReduceProfileController.", 
				   interpreter.getController("Field1") instanceof ReduceProfileController);
	}
	
	@Test
	public void testGetReduceReportControllerOK() 
		throws ParserException, LexerException, IOException
	{
		Start ast = buildAST("src/test/resources/scripts/ReportDataExportAndStore.trommel");
		ReduceInterpreter interpreter = Mockito.spy(new ReduceInterpreter(logger));
		
		ast.apply(interpreter);	
		
		assertTrue("Returned controller is not an instance of ReduceReportController.", 
				   interpreter.getController("Field1") instanceof ReduceReportController);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetReduceProfileControllerEmptyFieldName() 
		throws ParserException, LexerException, IOException
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataAllBuiltIn.trommel");
		ReduceInterpreter interpreter = Mockito.spy(new ReduceInterpreter(logger));
		
		ast.apply(interpreter);	
		
		interpreter.getController(" \t ");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetReduceProfileControllerUnrecognizedFieldName() 
		throws ParserException, LexerException, IOException
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataAllBuiltIn.trommel");
		ReduceInterpreter interpreter = Mockito.spy(new ReduceInterpreter(logger));
		
		ast.apply(interpreter);	
		
		interpreter.getController("FooBar");
	}

	//
	//	PROFILE DATA statement tests
	//
	
	@Test
	public void testLoadDataStatement()
		throws ParserException, LexerException, IOException 
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataExplicitFunc.trommel");
		ReduceInterpreter interpreter = Mockito.spy(new ReduceInterpreter(logger));
		
		ast.apply(interpreter);	
		
		// Verify fields to be loaded
		ArgumentCaptor<AField> fieldArgument = ArgumentCaptor.forClass(AField.class);
		
		Mockito.verify(interpreter, Mockito.times(3)).outAField(fieldArgument.capture());

		assertEquals("Field1", fieldArgument.getAllValues().get(0).getIdentifier().toString().trim());
		assertEquals("numeric", fieldArgument.getAllValues().get(0).getFieldType().getText().trim());
		assertEquals("Field2", fieldArgument.getAllValues().get(1).getIdentifier().toString().trim());
		assertEquals("CATEGORICAL", fieldArgument.getAllValues().get(1).getFieldType().getText().trim());
		assertEquals("Field3", fieldArgument.getAllValues().get(2).getIdentifier().toString().trim());
		assertEquals("categorical", fieldArgument.getAllValues().get(2).getFieldType().getText().trim());
	}
	
	
	@Test
	public void testProfileDataStatementSpecifiedFuncs() 
		throws ParserException, LexerException, IOException 
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataExplicitFunc.trommel");
		ReduceInterpreter interpreter = Mockito.spy(new ReduceInterpreter(logger));
		
		ast.apply(interpreter);	
		
		// Verify fields to be profiled
		ArgumentCaptor<AProfiledField> fieldArgument = ArgumentCaptor.forClass(AProfiledField.class);
		
		Mockito.verify(interpreter, Mockito.times(3)).outAProfiledField(fieldArgument.capture());
		
		assertEquals("Field1", fieldArgument.getAllValues().get(0).getIdentifier().toString().trim());
		assertEquals("Field2", fieldArgument.getAllValues().get(1).getIdentifier().toString().trim());
		assertEquals("Field3", fieldArgument.getAllValues().get(2).getIdentifier().toString().trim());
	
		// Verify funcs to use for profiling
		ArgumentCaptor<AParmLinearity> linArgument = ArgumentCaptor.forClass(AParmLinearity.class);
		ArgumentCaptor<AParmConfidence> confArgument = ArgumentCaptor.forClass(AParmConfidence.class);
		
		Mockito.verify(interpreter).outAMaxFunction(Mockito.any(AMaxFunction.class));
		Mockito.verify(interpreter).outAMinFunction(Mockito.any(AMinFunction.class));
		Mockito.verify(interpreter).outADistinctFunction(Mockito.any(ADistinctFunction.class));
		Mockito.verify(interpreter).outAEmptyFunction(Mockito.any(AEmptyFunction.class));
		Mockito.verify(interpreter).outAVarFunction(Mockito.any(AVarFunction.class));
		Mockito.verify(interpreter).outAParmLinearity(linArgument.capture());
		Mockito.verify(interpreter).outAParmConfidence(confArgument.capture());
		
		assertEquals("100", linArgument.getValue().getInteger().toString().trim());
		assertEquals("80", confArgument.getValue().getInteger().toString().trim());
	}
	
	@Test
	public void testProfileDataStatementAllBuiltin() 
		throws ParserException, LexerException, IOException 
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataAllBuiltIn.trommel");
		ReduceInterpreter interpreter = Mockito.spy(new ReduceInterpreter(logger));
		
		ast.apply(interpreter);	
		
		// Verify all builtin
		Mockito.verify(interpreter).outAAllBuiltinProfilers(Mockito.any(AAllBuiltinProfilers.class));
	}
	
	@Test
	public void testProfileDataStatementLinNoParen() 
		throws ParserException, LexerException, IOException 
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataLinNoParen.trommel");
		ReduceInterpreter interpreter = Mockito.spy(new ReduceInterpreter(logger));
		
		ast.apply(interpreter);	
		
		// Verify all lin function with no parentheses
		Mockito.verify(interpreter).outADefaultLinearity(Mockito.any(ADefaultLinearity.class));
	}

	@Test
	public void testProfileDataStatementLinDefaultParen() 
		throws ParserException, LexerException, IOException 
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataLinDefaultParen.trommel");
		ReduceInterpreter interpreter = Mockito.spy(new ReduceInterpreter(logger));
		
		ast.apply(interpreter);	
		
		// Verify lin function with parentheses, no parameter
		Mockito.verify(interpreter).outADefaultParenLinearity(Mockito.any(ADefaultParenLinearity.class));
	}

	@Test
	public void testProfileDataStatementConfNoParen() 
		throws ParserException, LexerException, IOException 
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataConfNoParen.trommel");
		ReduceInterpreter interpreter = Mockito.spy(new ReduceInterpreter(logger));
		
		ast.apply(interpreter);	

		// Verify req function
		Mockito.verify(interpreter).outAReqFunction(Mockito.any(AReqFunction.class));
				
		// Verify conf function with no parentheses
		Mockito.verify(interpreter).outADefaultConfidence(Mockito.any(ADefaultConfidence.class));
	}

	@Test
	public void testProfileDataStatementConfDefaultParen() 
		throws ParserException, LexerException, IOException 
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataConfDefaultParen.trommel");
		ReduceInterpreter interpreter = Mockito.spy(new ReduceInterpreter(logger));
		
		ast.apply(interpreter);	

		// Verify req function
		Mockito.verify(interpreter).outAReqFunction(Mockito.any(AReqFunction.class));
				
		// Verify conf function with parentheses, no parameter
		Mockito.verify(interpreter).outADefaultParenConfidence(Mockito.any(ADefaultParenConfidence.class));
	}

	
	//
	//	REPORT DATA statement tests
	//
	
	@Test
	public void testReportDataExportAndStore() 
		throws ParserException, LexerException, IOException
	{
		Start ast = buildAST("src/test/resources/scripts/ReportDataExportAndStore.trommel");
		ReduceInterpreter interpreter = Mockito.spy(new ReduceInterpreter(logger));
		
		ast.apply(interpreter);	
		
		// Verify report data statement production was visited
		Mockito.verify(interpreter, Mockito.atLeastOnce()).inAReportDataStatement(Mockito.any(AReportDataStatement.class));
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
