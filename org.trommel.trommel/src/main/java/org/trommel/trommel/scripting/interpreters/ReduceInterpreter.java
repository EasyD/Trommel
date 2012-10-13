/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.scripting.interpreters;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.controllers.ReduceController;
import org.trommel.trommel.controllers.ReduceProfileController;
import org.trommel.trommel.controllers.ReduceReportController;
import org.trommel.trommel.functions.ConfidenceReducer;
import org.trommel.trommel.functions.DistinctReducer;
import org.trommel.trommel.functions.EmptyReducer;
import org.trommel.trommel.functions.LinearityReducer;
import org.trommel.trommel.functions.MaxReducer;
import org.trommel.trommel.functions.MinReducer;
import org.trommel.trommel.functions.RequiredReducer;
import org.trommel.trommel.functions.VariabilityReducer;
import org.trommel.trommel.scripting.analysis.DepthFirstAdapter;
import org.trommel.trommel.scripting.node.AAllBuiltinProfilers;
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
import org.trommel.trommel.scripting.node.AProfileDataStatement;
import org.trommel.trommel.scripting.node.AReportDataStatement;
import org.trommel.trommel.scripting.node.AReqFunction;
import org.trommel.trommel.scripting.node.AVarFunction;
import org.trommel.trommel.utilities.StringUtilities;


/**
 *	The SableCC Visitor for interpreting the TrommelScript AST for the Reduce phase of processing. The primary
 *	responsibility of the ReduceInterpreter is the creation of {@link org.trommel.trommel.controllers.ReduceController}
 * 	instances based on the user-authored TrommelScript.
 *  <br><br>
 * 	NOTE - The ReduceInterpreter class assumes that the TrommelScript has already been validated for syntax and 
 * 	semantics prior to job submission (i.e., before it reaches the back-end).
 */
public class ReduceInterpreter extends DepthFirstAdapter
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	
	private final static String NUMERIC = "numeric";

	
	//
	//	Private members
	//
	
	private Logger logger = null;
	private HashMap<String, FieldType> fieldSymbolTable = new HashMap<String, FieldType>();
	
	// Maintain state of which kind of controller to construct
	private boolean constructProfileController = false;
	
	// Maintain state of reducers to be added to profile controller
	private boolean needConf = false;
	private boolean needDistinct = false;
	private boolean needEmpty = false;
	private boolean needLinearity = false;
	private boolean needMax = false;
	private boolean needMin = false;
	private boolean needRequired = false;
	private boolean needVariability = false;
	
	private int confTarget = -1;
	
	private ReduceProfileController profileController = null;

	
	//
	//	Getter/setters
	//
	
	/**
	 * @return A {@link org.trommel.trommel.controllers.ReduceController} instance constructed to process a
	 * specific Reduce key (i.e., a specific Field from the data set).
	 * @throws IllegalArgumentException  When fieldName is null or empty. All-whitespace strings are considered empty.
	 * Also thrown if fieldName does not correspond to a {@link org.trommel.trommel.Field} specified in the TrommelScript.
	 */
	public ReduceController getController(String fieldName)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if(StringUtilities.isNullOrEmpty(fieldName))
		{
			logger.error("ReduceInterpreer.getController was passed a null or empty fieldName.");
			
			throw new IllegalArgumentException("FieldName cannot be null or empty.");
		}
		
		if(!fieldSymbolTable.containsKey(fieldName.toLowerCase().trim()))
		{
			logger.error(String.format("ReduceInterpreer.getController was passed an unrecognized fieldName of %1$s.", 
					                   fieldName));
			
			throw new IllegalArgumentException("FieldName cannot be null or empty.");
			
		}

		// Build and return the appropriate controller for the specified Field
		if (constructProfileController)
		{
			profileController = new ReduceProfileController(logger);
			
			// Add "simple" reducers to controller
			if (needMax) { profileController.addRecordHandler(new MaxReducer(logger)); }
			if (needMin) { profileController.addRecordHandler(new MinReducer(logger)); }
			if (needDistinct) { profileController.addRecordHandler(new DistinctReducer(logger)); }
			if (needEmpty) { profileController.addRecordHandler(new EmptyReducer(logger)); }
			
			if (needLinearity) 
			{ 
				profileController.addRecordHandler(new LinearityReducer(logger, fieldSymbolTable.get(fieldName)));
			}
			
			// Add reducers that have object dependencies
			if (needVariability)
			{
				VariabilityReducer var = new VariabilityReducer(logger, fieldSymbolTable.get(fieldName));
				
				profileController.addRecordHandler(var);
				
				// ConfidenceReducers depend on VariabilityReducers
				if (needConf)
				{
					ConfidenceReducer conf = null;
					
					if (confTarget == -1)
					{
						// User wants default conf target
						conf = new ConfidenceReducer(logger, var);
					}
					else
					{
						// User specified a conf target
						conf = new ConfidenceReducer(logger, confTarget, var);
					}
					
					profileController.addRecordHandler(conf);
					
					// ConfidenceReducers depend on VariabilityReducers
					if (needRequired)
					{
						RequiredReducer req = new RequiredReducer(logger, conf);
						
						profileController.addRecordHandler(req);
					}
				}
			}
			
			return profileController;
		}
		else
		{
			return new ReduceReportController(logger, fieldName);
		}
	}
	
	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instances that will be used by the ReduceInterpreter
	 * to log to the Hadoop Task syslog file.
	 * @throws IllegalArgumentException Where logger is null.
	 */
	public ReduceInterpreter(Logger logger)
		throws IllegalArgumentException
	{
		// Check illegal input
		if (logger == null)
		{
			throw new IllegalArgumentException("Logger cannot be null.");
		}
		
		this.logger = logger;
	}

	
    //
    // LOAD DATA statement methods
    //
	
	/**
	 * Override of the SableCC-generated method for handling the TrommelScript grammar "field" Production. 
	 */
	@Override
    public void outAField(AField node)
    {
		logger.debug(String.format("ReduceInterpreter.outAField called with Identifier = %1$s, FieldType = %2$s.", 
				                   node.getIdentifier(), node.getFieldType()));
		
		// Build up symbol table of Fields specified in script
		String fieldName = node.getIdentifier().toString().toLowerCase().trim();
		String fieldType = node.getFieldType().toString().trim();
				
		if (NUMERIC.equalsIgnoreCase(fieldType))
		{
			fieldSymbolTable.put(fieldName, FieldType.numeric);
		}
		else
		{
			fieldSymbolTable.put(fieldName, FieldType.categorical);
		}
    }

	
	//
	//	PROFILE DATA statement methods
	//

	/**
	 * Override of the SableCC-generated method for pre-handling the TrommelScript grammar 
	 * "ProfileDataStatement" Production. 
	 */
    public void inAProfileDataStatement(AProfileDataStatement node)
    {
		logger.debug("ReduceInterpreter.inAProfileDataStatement called.");

		// User wants to profile data
		constructProfileController = true;
    }

	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "MaxFunction" Production. 
	 */
   @Override
    public void outAMaxFunction(AMaxFunction node)
    {
		logger.debug("ReduceInterpreter.outAMaxFunction called.");

		// User wants a MaxReducer
		needMax = true;
    }
	
	/**
	 * Override of the SableCC-generated method for handling the TrommelScript grammar 
	 * "MinFunction" Production. 
	 */
	@Override
    public void outAMinFunction(AMinFunction node)
    {
		logger.debug("ReduceInterpreter.outAMinFunction called.");

		// User wants a MinReducer
		needMin = true;
    }
	
	/**
	 * Override of the SableCC-generated method for handling the TrommelScript grammar 
	 * "DistinctFunction" Production. 
	 */
	@Override
    public void outADistinctFunction(ADistinctFunction node)
    {
		logger.debug("ReduceInterpreter.outADistinctFunction called.");

		// User wants a DistinctReducer
		needDistinct = true;
    }
	
	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "EmptyFunction" Production. 
	 */
    @Override
    public void outAEmptyFunction(AEmptyFunction node)
    {
		logger.debug("ReduceInterpreter.outAEmptyFunction called.");

		// User wants a EmptyReducer
		needEmpty = true;
    }

	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "VarFunction" Production. 
	 */
    @Override
    public void outAVarFunction(AVarFunction node)
    {
		logger.debug("ReduceInterpreter.outAVarFunction called.");

		// User wants a VariabilityReducer
		needVariability = true;
    }

	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "DefaultLinearity" Production. 
	 */
    @Override
    public void outADefaultLinearity(ADefaultLinearity node)
    {
		logger.debug("ReduceInterpreter.outADefaultLinearity called.");

		// User wants a LinearityReducer
		needLinearity = true;
    }

	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "DefaultParenLinearity" Production. 
	 */
    @Override
    public void outADefaultParenLinearity(ADefaultParenLinearity node)
    {
		logger.debug("ReduceInterpreter.outADefaultParenLinearity called.");

		// User wants a LinearityReducer
		needLinearity = true;
    }

	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "ParmLinearity" Production. 
	 */
    @Override
    public void outAParmLinearity(AParmLinearity node)
    {
		logger.debug("ReduceInterpreter.outAParmLinearity called.");

		// User wants a LinearityReducer
		needLinearity = true;
    }    
    
	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "DefaultConfidence" Production. 
	 */
    @Override
    public void outADefaultConfidence(ADefaultConfidence node)
    {
		logger.debug("ReduceInterpreter.outADefaultConfidence called.");

		// User wants a ConfidenceReducer with default target
		needConf = true;
    }
    
	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "DefaultParenConfidence" Production. 
	 */
    @Override
    public void outADefaultParenConfidence(ADefaultParenConfidence node)
    {
		logger.debug("ReduceInterpreter.outADefaultParenConfidence called.");

		// User wants a ConfidenceReducer with default target
		needConf = true;
    }

	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "ParmConfidence" Production. 
	 */
    @Override
    public void outAParmConfidence(AParmConfidence node)
    {
    	String parm = node.getInteger().toString().trim();
    	
		logger.debug(String.format("ReduceInterpreter.outAParmConfidence called with Integer = %1$s.", parm));
    	
		// User wants a ConfidenceReducer with specified target
		needConf = true;
		confTarget = Integer.parseInt(parm);
    }
    
	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "ReqFunction" Production. 
	 */
    @Override
    public void outAReqFunction(AReqFunction node)
    {
		logger.debug("ReduceInterpreter.outADefaultParenConfidence called.");

		// User wants a RequiredReducer
		needRequired = true;
    }

	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "AllBuiltinProfilers" Production. 
	 */
    @Override
    public void outAAllBuiltinProfilers(AAllBuiltinProfilers node)
    {
		logger.debug("ReduceInterpreter.outAAllBuiltinProfilers called.");
		
		// Building a profile controller
		constructProfileController = true;
		
		// User wants to use all the reducers
		needConf = true;
		needDistinct = true;
		needEmpty = true;
		needLinearity = true;
		needMax = true;
		needMin = true;
		needRequired = true;
		needVariability = true;
    }

	
	//
	//	REPORT DATA statement methods
	//
    
	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "ReportDataStatement" Production. 
	 */
    @Override
    public void inAReportDataStatement(AReportDataStatement node)
    {
    	// Only override this method for debugging purposes
		logger.debug("ReduceInterpreter.outAAllBuiltinProfilers called.");
    }
}
