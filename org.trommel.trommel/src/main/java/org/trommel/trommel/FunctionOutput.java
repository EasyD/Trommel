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
package org.trommel.trommel;

import org.trommel.trommel.utilities.StringUtilities;


/**
 *	Simple Data Transfer Object (DTO) that stores a single result of a Trommel function's invocation.
 */
public final class FunctionOutput 
{
	//
	//	Private members
	//
	private String functionName;
	private String output;
		
	
	//
	//	Constructors
	//
	
	/**
	 * @param functionName Name of the function (e.g., "Max") that generated the output.
	 * @param output The output generated by the function.
	 * @throws IllegalArgumentException Where functionName or output are null or empty. All-whitespace strings are considered empty. 
	 */
	public FunctionOutput(String functionName, String output)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if (StringUtilities.isNullOrEmpty(functionName))
		{
			throw new IllegalArgumentException("Function name can't be null or an empty string.");
		}
		
		if (StringUtilities.isNullOrEmpty(output))
		{
			throw new IllegalArgumentException("Function output can't be null or an empty string.");
		}

		this.functionName = functionName;
		this.output = output;
	}
	
	
	//
	//	Public methods
	//
	
	/**
	 * Serializes the FunctionOutput to a {@link String}.
	 * 
	 * @return Serialized output in the format of "&lt;FunctionName&gt;=&lt;Output&gt;".
	 */
	public String serialize()
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(functionName);
		buffer.append("=");
		buffer.append(output);
	
		return buffer.toString();
	}
}
