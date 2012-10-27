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
import java.util.List;

import org.trommel.trommel.scripting.analysis.DepthFirstAdapter;
import org.trommel.trommel.scripting.node.AField;
import org.trommel.trommel.scripting.node.AProfiledField;
import org.trommel.trommel.scripting.node.AReportedField;

/**
 *	The SableCC Visitor for interpreting the TrommelScript AST in terms of validating semantics. The primary
 *	responsibility of the ValidationInterpreter is ensuring that the {@link org.trommel.trommel.Field} instances
 *	defined in a TrommelScript LOAD DATA statement are a complete superset of any Field instances referenced in
 *	a PROFILE DATA or REPORT DATA statement.
 */
public class ValidationInterpreter extends DepthFirstAdapter
{
	//
	//	Private members
	//
	
	// Sacrifice memory for O(1) lookup perf
	private HashMap<String, String> loadDataSymbolTable = new HashMap<String, String>();
	private HashMap<String, String> actionSymbolTable = new HashMap<String, String>();

	// List of semantic errors
	private LinkedList<String> semanticErrors = new LinkedList<String>();

	
	//
	//	Getters/setters
	//
	
	public List<String> getSemanticErrors()
	{
		return semanticErrors;
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
		String fieldName = node.getIdentifier().toString().toLowerCase().trim();
		
		// Build up symbol table of Fields specified in script
		if (loadDataSymbolTable.containsKey(fieldName))
		{
			// Field is listed more than once, report error
			semanticErrors.addLast(String.format("Field %1$s already specified in LOAD DATA statement.",
					                              node.getIdentifier().toString().trim()));
		}
		else
		{
			// Add field to symbol table
			loadDataSymbolTable.put(fieldName, "");
		}
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
    	validateField(node.getIdentifier().toString().trim(), "PROFILE DATA");
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
    	validateField(node.getIdentifier().toString().trim(), "REPORT DATA");
    }
    
    
    //
    //	Private/helper methods
    //	
    
    public void validateField(String fieldName, String statement)
    {
    	String tempFieldName = fieldName.toLowerCase();
    	
    	if (!loadDataSymbolTable.containsKey(tempFieldName))
    	{
    		// Field isn't recognized, report error
    		semanticErrors.addLast(String.format("Field %1$s was not specified in PROFILE DATA statement.",
    				                             fieldName));
    	}
    	else if (actionSymbolTable.containsKey(tempFieldName))
    	{
    		// Field isn't recognized, report error
    		semanticErrors.addLast(String.format("Field %1$s already specified in %2$s statement.",
    				                             fieldName, statement));
    	}
    	else
    	{
    		actionSymbolTable.put(tempFieldName, "");
    	}
    }
}