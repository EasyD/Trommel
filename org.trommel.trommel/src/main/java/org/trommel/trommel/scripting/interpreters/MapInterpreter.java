/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.scripting.interpreters;

import java.util.HashMap;
import java.util.LinkedList;

import org.trommel.trommel.Field;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.RecordParser;
import org.trommel.trommel.SimpleRecordParser;
import org.trommel.trommel.controllers.MapProfileController;
import org.trommel.trommel.controllers.ProfileFunction;
import org.trommel.trommel.scripting.analysis.DepthFirstAdapter;
import org.trommel.trommel.scripting.node.AAllBuiltinProfilers;
import org.trommel.trommel.scripting.node.AConfFunction;
import org.trommel.trommel.scripting.node.ACustomFieldsTerminatedBy;
import org.trommel.trommel.scripting.node.ADefaultLinearity;
import org.trommel.trommel.scripting.node.ADefaultParenLinearity;
import org.trommel.trommel.scripting.node.ADistinctFunction;
import org.trommel.trommel.scripting.node.AEmptyFunction;
import org.trommel.trommel.scripting.node.AField;
import org.trommel.trommel.scripting.node.ALoadDataStatement;
import org.trommel.trommel.scripting.node.AMaxFunction;
import org.trommel.trommel.scripting.node.AMinFunction;
import org.trommel.trommel.scripting.node.AParmConfidence;
import org.trommel.trommel.scripting.node.AParmLinearity;
import org.trommel.trommel.scripting.node.AProfiledField;
import org.trommel.trommel.scripting.node.ASingleFunctionList;
import org.trommel.trommel.scripting.node.AVarFunction;


/**
 *	The SableCC Visitor for interpreting the TrommelScript AST for the Map phase of processing. The primary
 *	responsibility of the MapInterpreter is the creation of {@link org.trommel.trommel.RecordParser} and
 * 	{@link org.trommel.trommel.controllers.MapController} instances based on the user-authored TrommelScript.
 *  <br>
 * 	NOTE - The MapInterpreter class assumes that the TrommelScript has already been validated for syntax and 
 * 	semantics prior to job submission (i.e., before it reaches the back-end).
 */
public class MapInterpreter extends DepthFirstAdapter
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	public final static String DELIMITER = "*|*";
	private final static String NUMERIC = "numeric";
	
	
	//
	//	Private members
	//
	private String fieldTerminator = "\t";
	private RecordParser recordParser = null;
	private HashMap<String, Field> fieldSymbolTable = new HashMap<String, Field>();
	private LinkedList<Field> profileFields = null;
	private MapProfileController profileController = null;
	
	
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
	

	//
	//	Public methods
	//
	
	@Override
    public void outAField(AField node)
    {
		// Build up symbol table of Fields specified in script
		String fieldName = node.getIdentifier().toString().toLowerCase().trim();
		String fieldType = node.getFieldType().toString().trim();
				
		if (NUMERIC.equalsIgnoreCase(fieldType))
		{
			fieldSymbolTable.put(fieldName, new Field(fieldName, FieldType.numeric));
		}
		else
		{
			fieldSymbolTable.put(fieldName, new Field(fieldName, FieldType.categorical));
		}
    }
	
    @Override
    public void outACustomFieldsTerminatedBy(ACustomFieldsTerminatedBy node)
    {
		// Custom terminator specified in script
		fieldTerminator = node.getFieldTerminator().toString().replace("\'", "");
    }
	
	@Override
    public void outALoadDataStatement(ALoadDataStatement node)
    {
		recordParser = new SimpleRecordParser(fieldSymbolTable.values().toArray(new Field[0]), fieldTerminator, DELIMITER);
    }

    @Override
    public void outAProfiledField(AProfiledField node)
    {		
    	if (profileFields == null)
    	{
    		profileFields = new LinkedList<Field>();
    	}
    	
    	profileFields.addLast(fieldSymbolTable.get(node.getIdentifier().toString().toLowerCase().trim()));
    }
	
    @Override
    public void inASingleFunctionList(ASingleFunctionList node)
    {
    	profileController = new MapProfileController(profileFields.toArray(new Field[0])); 
    }

    @Override
    public void outAMaxFunction(AMaxFunction node)
    {
		// Tell controller to add Max function
		profileController.addFunction(ProfileFunction.Max);
    }
	
	@Override
    public void outAMinFunction(AMinFunction node)
    {
		// Tell controller to add Min function
		profileController.addFunction(ProfileFunction.Min);
    }
	
	@Override
    public void outADistinctFunction(ADistinctFunction node)
    {
		// Tell controller to add Distinct function
		profileController.addFunction(ProfileFunction.Distinct);
    }
	
    @Override
    public void outAEmptyFunction(AEmptyFunction node)
    {
		// Tell controller to add Empty function
		profileController.addFunction(ProfileFunction.Empty);
    }

    @Override
    public void outAVarFunction(AVarFunction node)
    {
		// Tell controller to add Var function
		profileController.addFunction(ProfileFunction.Var);
    }

    @Override
    public void outADefaultLinearity(ADefaultLinearity node)
    {
		// Tell controller to add Lin function with default sampling rate
		profileController.addFunction(ProfileFunction.Lin);
    }

    @Override
    public void outADefaultParenLinearity(ADefaultParenLinearity node)
    {
		// Tell controller to add Lin function with default sampling rate
		profileController.addFunction(ProfileFunction.Lin);
    }

    @Override
    public void outAParmLinearity(AParmLinearity node)
    {
		// Tell controller to add Lin function with specified sampling rate
		profileController.addFunction(ProfileFunction.Lin, node.getInteger().toString().trim());
    }    

    @Override
    public void outAAllBuiltinProfilers(AAllBuiltinProfilers node)
    {
    	profileController = new MapProfileController(profileFields.toArray(new Field[0])); 
    	
    	// Add all mapper functions with default parameters
		profileController.addFunction(ProfileFunction.Max);
		profileController.addFunction(ProfileFunction.Min);
		profileController.addFunction(ProfileFunction.Distinct);
		profileController.addFunction(ProfileFunction.Empty);
		profileController.addFunction(ProfileFunction.Var);
		profileController.addFunction(ProfileFunction.Lin);
    }
}
