/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel.controllers;


/**
 * Interface that defines the common functionality for all Reduce phase Trommel Controllers.
 */
public interface ReduceController 
{
	
	/**
	 * @return Tab-delimited current results from the Controller.
	 */
	public String getFormattedResults();

	/**
	 * Process one suffled (i.e., Map phase output) record. The ReduceController will invoke one or more 
	 * {@link org.trommel.trommel.RedceRecordHandler} instances (i.e., Trommel functions) to process the 
	 * data.
	 * 
	 * @param record One record from post-Map phase processing, specifically the output of one of more
	 * {@link org.trommel.trommel.MapRecordHandler} instances delimited as specified by the 
	 * {@link org.trommel.trommel.scripting.interpreters.MapInterpreter} class' static public DELIMITER 
	 * member.
	 */
	public abstract void handleReduceRecord(String record);
}
