package org.trommel.trommel.scripting.interpreters.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

import org.apache.log4j.Logger;
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
import org.trommel.trommel.scripting.node.AVarFunction;
import org.trommel.trommel.scripting.node.Start;
import org.trommel.trommel.scripting.parser.Parser;
import org.trommel.trommel.scripting.parser.ParserException;

public class MapInterpreterTests 
{
	//
	//	Tests
	//
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullLogger()
	{
		@SuppressWarnings("unused")
		MapInterpreter interpreter = new MapInterpreter(null);
	}
	
	@Test
	public void testLoadDataStatement()
		throws ParserException, LexerException, IOException 
	{
		Lexer lexer = new Lexer(new PushbackReader(new StringReader(buildProfileDataScriptSpecifiedFuncs()), 1024));
		Parser parser = new Parser(lexer);
		Start ast = parser.parse();
		Logger logger = Mockito.mock(Logger.class);
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
		Lexer lexer = new Lexer(new PushbackReader(new StringReader(buildProfileDataScriptSpecifiedFuncs()), 1024));
		Parser parser = new Parser(lexer);
		Start ast = parser.parse();
		Logger logger = Mockito.mock(Logger.class);
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
		
		assertEquals("45", linArgument.getValue().getInteger().toString().trim());

		// Verify RecordParser and MapInterpreter were constructed
		assertNotNull(interpreter.getRecordParser());
		assertNotNull(interpreter.getController());
	}

	@Test
	public void testProfileDataStatementAllBuiltin() 
		throws ParserException, LexerException, IOException 
	{
		Lexer lexer = new Lexer(new PushbackReader(new StringReader(buildProfileDataScriptAllBuiltin()), 1024));
		Parser parser = new Parser(lexer);
		Start ast = parser.parse();
		Logger logger = Mockito.mock(Logger.class);
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
		Lexer lexer = new Lexer(new PushbackReader(new StringReader(buildProfileDataScriptLinNoParen()), 1024));
		Parser parser = new Parser(lexer);
		Start ast = parser.parse();
		Logger logger = Mockito.mock(Logger.class);
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
		Lexer lexer = new Lexer(new PushbackReader(new StringReader(buildProfileDataScriptLinDefaultParen()), 1024));
		Parser parser = new Parser(lexer);
		Start ast = parser.parse();
		Logger logger = Mockito.mock(Logger.class);
		MapInterpreter interpreter = Mockito.spy(new MapInterpreter(logger));
		
		ast.apply(interpreter);	
		
		// Verify all lin function with no parentheses
		Mockito.verify(interpreter).outADefaultParenLinearity(Mockito.any(ADefaultParenLinearity.class));

		// Verify RecordParser and MapInterpreter were constructed
		assertNotNull(interpreter.getRecordParser());
		assertNotNull(interpreter.getController());
	}
		
		
	//
	//	Private/helper methods
	//
	
	private String buildLoadDataScript()
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("load \n DATA '/usr/local/FooBar' as (Field1:numeric, Field2: CATEGORICAL,Field3 : categorical)\n");
		buffer.append("FIELDS terminated BY '\\t';");

		return buffer.toString();
	}
	
	private String buildProfileDataScriptSpecifiedFuncs()
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(buildLoadDataScript());
		buffer.append("PROFILE Field1, Field2, Field3 WITH min(), max, EMPTY(), VAR(), distinct, lin(45), CONF(1234)\n");
		buffer.append("store INTO 'Foo' AS 'Bar';");
		
		return buffer.toString();
	}

	private String buildProfileDataScriptAllBuiltin()
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(buildLoadDataScript());
		buffer.append("PROFILE Field1, Field2, Field3 WITH ALL builtin\n");
		buffer.append("store INTO 'Foo' AS 'Bar';");
		
		return buffer.toString();
	}

	private String buildProfileDataScriptLinNoParen()
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(buildLoadDataScript());
		buffer.append("PROFILE Field1, Field2, Field3 WITH min(), max, EMPTY(), VAR(), distinct, lin, CONF(1234)\n");
		buffer.append("store INTO 'Foo' AS 'Bar';");
		
		return buffer.toString();
	}


	private String buildProfileDataScriptLinDefaultParen()
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(buildLoadDataScript());
		buffer.append("PROFILE Field1, Field2, Field3 WITH min(), max, EMPTY(), VAR(), distinct, LIN(), CONF(1234)\n");
		buffer.append("store INTO 'Foo' AS 'Bar';");
		
		return buffer.toString();
	}
}
