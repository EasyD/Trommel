/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel;

import java.util.HashMap;

/**
 * The interface for all Trommel constructs (i.e., Functions and Data Reporters) that process
 * records.
 */
public interface RecordHandler 
{	
	//
	//	Getters/setters
	//	

	/**
	 * Return the RecordHandler's name (e.g., "Max").
	 * 
	 * @return Name of record handler.
	 */
	public String getHandlerName();

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
	 * Process a single {@link MapRecord} read from the data set for the Map phase of processing.
	 * 
	 * @param record The MapRecord containing the data read from data set and any results of Map phase 
	 * Trommel processing.
	 */
	public void handleMapRecord(MapRecord record);
		
	/**
	 * Process a single record read from the post-Map phase data for the Reduce phase of processing.
	 * 
	 * @param record {@link java.util.HashMap} of parsed data. For Functions the {@link java.util.Hashtable} is in the form of 
	 * <"FunctionName", "OutputValue"> and for Data Reporters the HashMap is in the form of
	 * <"DataReporterName", "FieldValue">.
	 */
	public void handleReduceRecord(HashMap<String,String> record);
}
