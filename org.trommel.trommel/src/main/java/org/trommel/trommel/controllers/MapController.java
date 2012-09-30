/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel.controllers;

import org.trommel.trommel.MapRecord;


/**
 * Interface that defines the common functionality for all Map phase Trommel Controllers.
 *
 */
public interface MapController 
{

	/**
	 * Process one record from the data set. The MapController will invoke one or more {@link org.trommel.trommel.MapRecordHandler}
	 * instances (i.e., Trommel functions) to process the data, with processing results stored in the 
	 * {@link org.trommel.trommel.MapRecord}.
	 * 
	 * @param record The MapRecord to process and to be used to store processing results.
	 */
	public void handleMapRecord(MapRecord record);
}
