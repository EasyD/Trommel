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
import org.trommel.trommel.scripting.interpreters.MapInterpreter;
import org.trommel.trommel.scripting.lexer.Lexer;
import org.trommel.trommel.scripting.lexer.LexerException;
import org.trommel.trommel.scripting.node.AAllBuiltinProfilers;
import org.trommel.trommel.scripting.node.ACustomFieldsTerminatedBy;
import org.trommel.trommel.scripting.node.ADefaultLinearity;
import org.trommel.trommel.scripting.node.ADefaultParenLinearity;
import org.trommel.trommel.scripting.node.ADistinctFunction;
import org.trommel.trommel.scripting.node.AEmptyFunction;
import org.trommel.trommel.scripting.node.AField;
import org.trommel.trommel.scripting.node.AMaxFunction;
import org.trommel.trommel.scripting.node.AMinFunction;
import org.trommel.trommel.scripting.node.AParmLinearity;
import org.trommel.trommel.scripting.node.AProfiledField;
import org.trommel.trommel.scripting.node.AReportedField;
import org.trommel.trommel.scripting.node.AVarFunction;
import org.trommel.trommel.scripting.node.Start;
import org.trommel.trommel.scripting.parser.Parser;
import org.trommel.trommel.scripting.parser.ParserException;


//
//	Unit tests for the org.trommel.trommel.mapreduce.interpreters.MapInterpreter class
//
@SuppressWarnings("unused")
public class MapInterpreterTests 
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
		MapInterpreter interpreter = new MapInterpreter(null);
	}
	
	@Test
	public void testGetControllerReturnsNull()
	{
		MapInterpreter interpreter = new MapInterpreter(logger);
		
		assertNull(interpreter.getController());
	}
	

	//
	//	PROFILE DATA statement tests
	//
	
	@Test
	public void testLoadDataStatement()
		throws ParserException, LexerException, IOException 
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataExplicitFunc.trommel");
		MapInterpreter interpreter = Mockito.spy(new MapInterpreter(logger));
		
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

		// Verify field terminator
		ArgumentCaptor<ACustomFieldsTerminatedBy> terminatorArgument = ArgumentCaptor.forClass(ACustomFieldsTerminatedBy.class);
		
		Mockito.verify(interpreter).outACustomFieldsTerminatedBy(terminatorArgument.capture());
		
		assertEquals("'\\t'", terminatorArgument.getValue().getFieldTerminator().toString().trim());
	}
	
	@Test
	public void testProfileDataStatementSpecifiedFuncs() 
		throws ParserException, LexerException, IOException 
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataExplicitFunc.trommel");
		MapInterpreter interpreter = Mockito.spy(new MapInterpreter(logger));
		
		ast.apply(interpreter);	
		
		// Verify fields to be profiled
		ArgumentCaptor<AProfiledField> fieldArgument = ArgumentCaptor.forClass(AProfiledField.class);
		
		Mockito.verify(interpreter, Mockito.times(3)).outAProfiledField(fieldArgument.capture());
		
		assertEquals("Field1", fieldArgument.getAllValues().get(0).getIdentifier().toString().trim());
		assertEquals("Field2", fieldArgument.getAllValues().get(1).getIdentifier().toString().trim());
		assertEquals("Field3", fieldArgument.getAllValues().get(2).getIdentifier().toString().trim());
	
		// Verify funcs to use for profiling
		ArgumentCaptor<AParmLinearity> linArgument = ArgumentCaptor.forClass(AParmLinearity.class);
		
		Mockito.verify(interpreter).outAMaxFunction(Mockito.any(AMaxFunction.class));
		Mockito.verify(interpreter).outAMinFunction(Mockito.any(AMinFunction.class));
		Mockito.verify(interpreter).outADistinctFunction(Mockito.any(ADistinctFunction.class));
		Mockito.verify(interpreter).outAEmptyFunction(Mockito.any(AEmptyFunction.class));
		Mockito.verify(interpreter).outAVarFunction(Mockito.any(AVarFunction.class));
		Mockito.verify(interpreter).outAParmLinearity(linArgument.capture());
		
		assertEquals("100", linArgument.getValue().getInteger().toString().trim());

		// Verify RecordParser and MapInterpreter were constructed
		assertNotNull(interpreter.getRecordParser());
		assertNotNull(interpreter.getController());
	}

	@Test
	public void testProfileDataStatementAllBuiltin() 
		throws ParserException, LexerException, IOException 
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataAllBuiltIn.trommel");
		MapInterpreter interpreter = Mockito.spy(new MapInterpreter(logger));
		
		ast.apply(interpreter);	
		
		// Verify all builtin
		Mockito.verify(interpreter).outAAllBuiltinProfilers(Mockito.any(AAllBuiltinProfilers.class));

		// Verify RecordParser and MapInterpreter were constructed
		assertNotNull(interpreter.getRecordParser());
		assertNotNull(interpreter.getController());
	}

	@Test
	public void testProfileDataStatementLinNoParen() 
		throws ParserException, LexerException, IOException 
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataLinNoParen.trommel");
		MapInterpreter interpreter = Mockito.spy(new MapInterpreter(logger));
		
		ast.apply(interpreter);	
		
		// Verify all lin function with no parentheses
		Mockito.verify(interpreter).outADefaultLinearity(Mockito.any(ADefaultLinearity.class));

		// Verify RecordParser and MapInterpreter were constructed
		assertNotNull(interpreter.getRecordParser());
		assertNotNull(interpreter.getController());
	}

	@Test
	public void testProfileDataStatementLinDefaultParen() 
		throws ParserException, LexerException, IOException 
	{
		Start ast = buildAST("src/test/resources/scripts/ProfileDataLinDefaultParen.trommel");
		MapInterpreter interpreter = Mockito.spy(new MapInterpreter(logger));
		
		ast.apply(interpreter);	
		
		// Verify all lin function with no parentheses
		Mockito.verify(interpreter).outADefaultParenLinearity(Mockito.any(ADefaultParenLinearity.class));

		// Verify RecordParser and MapInterpreter were constructed
		assertNotNull(interpreter.getRecordParser());
		assertNotNull(interpreter.getController());
	}

	
	//
	//	REPORT DATA statement tests
	//
	
	@Test
	public void testReportDataExportAndStore() 
		throws ParserException, LexerException, IOException
	{
		Start ast = buildAST("src/test/resources/scripts/ReportDataExportAndStore.trommel");
		MapInterpreter interpreter = Mockito.spy(new MapInterpreter(logger));
		
		ast.apply(interpreter);	
		
		// Verify reported field production was visited
		Mockito.verify(interpreter, Mockito.atLeastOnce()).outAReportedField(Mockito.any(AReportedField.class));

		// Verify RecordParser and MapInterpreter were constructed
		assertNotNull(interpreter.getRecordParser());
		assertNotNull(interpreter.getController());
	}
	
	//
	//	SAMPLE DATA statement tests
	//
	
	@Test
	public void testSampleDataExportAndStore() 
		throws ParserException, LexerException, IOException
	{
		Start ast = buildAST("src/test/resources/scripts/SampleDataExportAndStore.trommel");
		MapInterpreter interpreter = new MapInterpreter(logger);
		
		ast.apply(interpreter);	
	
		assertTrue(interpreter.samplingData());
		assertEquals(75, interpreter.getSampleRate());
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
