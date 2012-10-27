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
package org.trommel.trommel.scripting.interpreters;

import java.util.HashMap;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.trommel.trommel.Field;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.RecordParser;
import org.trommel.trommel.SimpleRecordParser;
import org.trommel.trommel.controllers.MapController;
import org.trommel.trommel.controllers.MapProfileController;
import org.trommel.trommel.controllers.MapReportController;
import org.trommel.trommel.controllers.ProfileFunction;
import org.trommel.trommel.scripting.analysis.DepthFirstAdapter;
import org.trommel.trommel.scripting.node.AAllBuiltinProfilers;
import org.trommel.trommel.scripting.node.ACustomFieldsTerminatedBy;
import org.trommel.trommel.scripting.node.ADefaultLinearity;
import org.trommel.trommel.scripting.node.ADefaultParenLinearity;
import org.trommel.trommel.scripting.node.ADistinctFunction;
import org.trommel.trommel.scripting.node.AEmptyFunction;
import org.trommel.trommel.scripting.node.AField;
import org.trommel.trommel.scripting.node.ALoadDataStatement;
import org.trommel.trommel.scripting.node.AMaxFunction;
import org.trommel.trommel.scripting.node.AMinFunction;
import org.trommel.trommel.scripting.node.AParmLinearity;
import org.trommel.trommel.scripting.node.AProfiledField;
import org.trommel.trommel.scripting.node.AReportDataStatement;
import org.trommel.trommel.scripting.node.AReportedField;
import org.trommel.trommel.scripting.node.ASampleDataStatement;
import org.trommel.trommel.scripting.node.ASampleRate;
import org.trommel.trommel.scripting.node.ASingleFunctionList;
import org.trommel.trommel.scripting.node.AVarFunction;


/**
 *	The SableCC Visitor for interpreting the TrommelScript AST for the Map phase of processing. The primary
 *	responsibility of the MapInterpreter is the creation of {@link org.trommel.trommel.RecordParser} and
 * 	{@link org.trommel.trommel.controllers.MapController} instances based on the user-authored TrommelScript.
 *  <br><br>
 * 	NOTE - The MapInterpreter class assumes that the TrommelScript has already been validated for syntax and 
 * 	semantics prior to job submission (i.e., before it reaches the back-end).
 */
public class MapInterpreter extends DepthFirstAdapter
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	
	/**
	 * Delimiter for Trommel Function (e.g., Max function) output.
	 */
	public final static String DELIMITER = "*|*";
	private final static String NUMERIC = "numeric";
	
	
	//
	//	Private members
	//
	
	private Logger logger = null;
	private String fieldTerminator = "\t";
	private RecordParser recordParser = null;
	
	// Sacrifice memory for O(1) lookup perf
	private HashMap<String, Field> fieldSymbolTable = new HashMap<String, Field>();
	
	// Need to maintain Fields in order of specification
	private LinkedList<Field> dataSetFields = new LinkedList<Field>();
	
	// Subset of all fields that will be handled
	private LinkedList<Field> handledFields = null;
	
	private MapProfileController profileController = null;
	private MapReportController reportController = null;
	private boolean samplingData = false;
	private int sampleRate = -1;
	
	
	//
	//	Getters/setters
	//
	
	/**
	 * @return {@link org.trommel.trommel.RecordParser} instance configured to parse the data set
	 * based on the user-authored TrommelScript after AST is visited, returns null otherwise.
	 */
	public RecordParser getRecordParser()
	{		
		return recordParser;
	}
	
	/**
	 * @return {@link org.trommel.trommel.controllers.MapController} instance configured to process 
	 * the data set based on the user-authored TrommelScript after AST is visited, returns null otherwise.
	 */
	public MapController getController()
	{
		if (profileController != null)
		{
			return profileController;
		}		
		else if (reportController != null)
		{
			return reportController;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * @return For TrommelScripts containing the SAMPLE DATA statement, returns the user-specified random sample rate,
	 * -1 otherwise.
	 */
	public int getSampleRate()
	{
		return sampleRate;
	}
	
	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instances that will be used by the MapInterpreter
	 * to log to the Hadoop Task syslog file.
	 */
	public MapInterpreter(Logger logger)
	{
		// Check illegal input
		if (logger == null)
		{
			throw new IllegalArgumentException("Logger cannot be null.");
		}
		
		this.logger = logger;
	}
	
	
	//
	//	Public methods
	//
	
	
	/**
	 * @return Whether the TrommelScript contains the SAMPLE DATA statement.
	 */
	public boolean samplingData()
	{
		return samplingData;
	}
	
	
    //
    // LOAD DATA statement methods
    //
	
	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar "field" Production. 
	 */
	@Override
    public void outAField(AField node)
    {
		logger.debug(String.format("MapInterpreter.outAField called with Identifier = %1$s, FieldType = %2$s.", 
				                   node.getIdentifier(), node.getFieldType()));
		
		// Build up symbol table of Fields specified in script
		String fieldName = node.getIdentifier().toString().toLowerCase().trim();
		String fieldType = node.getFieldType().toString().trim();
				
		if (NUMERIC.equalsIgnoreCase(fieldType))
		{
			Field field = new Field(fieldName, FieldType.numeric);
			
			fieldSymbolTable.put(fieldName, field);
			dataSetFields.addLast(field);
		}
		else
		{
			Field field = new Field(fieldName, FieldType.categorical);
			
			fieldSymbolTable.put(fieldName, field);
			dataSetFields.addLast(field);
		}
    }
	
	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "CustomFieldsTerminatedBy" Production. 
	 */
    @Override
    public void outACustomFieldsTerminatedBy(ACustomFieldsTerminatedBy node)
    {
    	fieldTerminator = node.getFieldTerminator().toString().trim();
    	
		logger.debug(String.format("MapInterpreter.outACustomFieldsTerminatedBy called with FieldTerminator = %1$s.", 
                                   fieldTerminator));

		// Custom terminator specified in script
		fieldTerminator = fieldTerminator.replace("\'", "").replace("\\t", "\t");
    }
	
	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "LoadDataStatement" Production. 
	 */
	@Override
    public void outALoadDataStatement(ALoadDataStatement node)
    {
		logger.debug("MapInterpreter.outALoadDataStatement called.");

		recordParser = new SimpleRecordParser(dataSetFields.toArray(new Field[0]), fieldTerminator, DELIMITER);
    }

	
    //
    // PROFILE DATA statement methods
    //
	
	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "ProfiledField" Production. 
	 */
    @Override
    public void outAProfiledField(AProfiledField node)
    {		
		logger.debug(String.format("MapInterpreter.outAProfiledField called with Identifier = %1$s.", 
                                   node.getIdentifier()));

		if (handledFields == null)
    	{
    		handledFields = new LinkedList<Field>();
    	}
    	    	
    	handledFields.addLast(fieldSymbolTable.get(node.getIdentifier().toString().toLowerCase().trim()));
    }
	
	/**
	 * Override of the SableCC-generated method for pre-handling the TrommelScript grammar 
	 * "SingleFunctionList" Production. 
	 */
    @Override
    public void inASingleFunctionList(ASingleFunctionList node)
    {
		logger.debug("MapInterpreter.inASingleFunctionList called.");

		profileController = new MapProfileController(logger, handledFields.toArray(new Field[0])); 
    }

	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "MaxFunction" Production. 
	 */
   @Override
    public void outAMaxFunction(AMaxFunction node)
    {
		logger.debug("MapInterpreter.outAMaxFunction called.");

		// Tell controller to add Max function
		profileController.addFunction(ProfileFunction.Max);
    }
	
	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "MinFunction" Production. 
	 */
	@Override
    public void outAMinFunction(AMinFunction node)
    {
		logger.debug("MapInterpreter.outAMinFunction called.");

		// Tell controller to add Min function
		profileController.addFunction(ProfileFunction.Min);
    }
	
	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "DistinctFunction" Production. 
	 */
	@Override
    public void outADistinctFunction(ADistinctFunction node)
    {
		logger.debug("MapInterpreter.outADistinctFunction called.");

		// Tell controller to add Distinct function
		profileController.addFunction(ProfileFunction.Distinct);
    }
	
	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "EmptyFunction" Production. 
	 */
    @Override
    public void outAEmptyFunction(AEmptyFunction node)
    {
		logger.debug("MapInterpreter.outAEmptyFunction called.");

		// Tell controller to add Empty function
		profileController.addFunction(ProfileFunction.Empty);
    }

	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "VarFunction" Production. 
	 */
    @Override
    public void outAVarFunction(AVarFunction node)
    {
		logger.debug("MapInterpreter.outAVarFunction called.");

		// Tell controller to add Var function
		profileController.addFunction(ProfileFunction.Var);
    }

	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "DefaultLinearity" Production. 
	 */
    @Override
    public void outADefaultLinearity(ADefaultLinearity node)
    {
		logger.debug("MapInterpreter.outADefaultLinearity called.");

		// Tell controller to add Lin function with default sampling rate
		profileController.addFunction(ProfileFunction.Lin);
    }

	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "DefaultParenLinearity" Production. 
	 */
    @Override
    public void outADefaultParenLinearity(ADefaultParenLinearity node)
    {
		logger.debug("MapInterpreter.outADefaultParenLinearity called.");

		// Tell controller to add Lin function with default sampling rate
		profileController.addFunction(ProfileFunction.Lin);
    }

	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "ParmLinearity" Production. 
	 */
    @Override
    public void outAParmLinearity(AParmLinearity node)
    {
    	String parm = node.getInteger().toString().trim();
    	
		logger.debug(String.format("MapInterpreter.outAParmLinearity called with Integer = %1$s.", parm));

		// Tell controller to add Lin function with specified sampling rate
		profileController.addFunction(ProfileFunction.Lin, parm);
    }    

	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "AllBuiltinProfilers" Production. 
	 */
    @Override
    public void outAAllBuiltinProfilers(AAllBuiltinProfilers node)
    {
		logger.debug("MapInterpreter.outAAllBuiltinProfilers called.");

    	profileController = new MapProfileController(logger, handledFields.toArray(new Field[0])); 
    	
    	// Add all mapper functions with default parameters
		profileController.addFunction(ProfileFunction.Max);
		profileController.addFunction(ProfileFunction.Min);
		profileController.addFunction(ProfileFunction.Distinct);
		profileController.addFunction(ProfileFunction.Empty);
		profileController.addFunction(ProfileFunction.Var);
		profileController.addFunction(ProfileFunction.Lin);
    }


    //
    // REPORT DATA statement methods
    //
    
	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "ReportedField" Production. 
	 */
    @Override
    public void outAReportedField(AReportedField node)
    {
		logger.debug(String.format("MapInterpreter.outAReportedField called with Identifier = %1$s.", 
                                   node.getIdentifier()));

		if (handledFields == null)
		{
			handledFields = new LinkedList<Field>();
		}
		
		handledFields.addLast(fieldSymbolTable.get(node.getIdentifier().toString().toLowerCase().trim()));
    }
    
	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "ReportDataStatement" Production. 
	 */
    public void outAReportDataStatement(AReportDataStatement node)
    {
		logger.debug("MapInterpreter.outAReportDataStatement called.");

		reportController = new MapReportController(logger, handledFields.toArray(new Field[0])); 
    }


    //
    // SAMPLE DATA statement methods
    //

	/**
	 * Override of the SableCC-generated method for pre-handling the TrommelScript grammar 
	 * "SampleDataStatement" Production. 
	 */
    public void inASampleDataStatement(ASampleDataStatement node)
    {
		logger.debug("MapInterpreter.inASampleDataStatement called.");

    	// TrommelScript contains a SAMPLE DATA statement
    	samplingData = true;
    }

	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "SampleRate" Production. 
	 */
    public void outASampleRate(ASampleRate node)
    {
		logger.debug("MapInterpreter.outASampleRate called.");

		// Store the user-specified random sampling rate
		sampleRate = Integer.parseInt(node.getInteger().toString().trim());
    }
}
