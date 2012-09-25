/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel;

import java.util.HashMap;


/**
 * The interface for all Trommel constructs (i.e., Functions and Data Reporters) that process
 * records during the Reduce phase.
 */
public interface ReduceRecordHandler 
{
	//
	//	Getters/setters
	//

	/**
	 * Return the RecordHandler's final Reduce phase process result.
	 * 
	 * @return Reduce phase processing result as a String.
	 */
	public String getReduceResult();
	
	
	//
	//	Public methods
	//
	
	/**
	 * Process a single record read from the post-Map phase data for the Reduce phase of processing.
	 * 
	 * @param record {@link java.util.HashMap} of parsed data. For Functions the {@link java.util.Hashtable} is in the form of 
	 * <"FunctionName", "OutputValue"> and for Data Reporters the HashMap is in the form of
	 * <"DataReporterName", "FieldValue">.
	 */
	public void handleReduceRecord(HashMap<String,String> record);
}
