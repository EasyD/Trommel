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
package org.trommel.trommel.controllers;


/**
 * Interface that defines the common functionality for all Reduce phase Trommel Controllers.
 */
public interface ReduceController 
{
	/**
	 * @return Tab-delimited header for Reduce file output. For example, "Max\tMin\tConfidence\tVariability"
	 */
	public String getHeader();
	
	/**
	 * @return Tab-delimited current results from the Controller.
	 */
	public String getFormattedResults();

	/**
	 * Process one suffled (i.e., Map phase output) record. The ReduceController will invoke one or more 
	 * {@link org.trommel.trommel.ReduceRecordHandler} instances (i.e., Trommel functions) to process the 
	 * data.
	 * 
	 * @param record One record from post-Map phase processing, specifically the output of one of more
	 * {@link org.trommel.trommel.MapRecordHandler} instances delimited as specified by the 
	 * {@link org.trommel.trommel.scripting.interpreters.MapInterpreter} class' static public DELIMITER 
	 * member.
	 */
	public abstract void handleReduceRecord(String record);
}
