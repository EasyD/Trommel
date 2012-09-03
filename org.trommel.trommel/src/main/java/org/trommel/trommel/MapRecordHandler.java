/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel;


/**
 * The interface for all Trommel constructs (i.e., Functions and Data Reporters) that process
 * records during the Map phase.
 */
public interface MapRecordHandler 
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
}
