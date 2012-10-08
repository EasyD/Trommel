/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel;

import java.util.HashMap;

import org.apache.log4j.Logger;


/**
 * The interface for all Trommel constructs (i.e., Functions and Data Reporters) that process
 * records during the Reduce phase.
 */
public abstract class ReduceRecordHandler  extends RecordHandler
{
	//
	//	Getters/setters
	//

	/**
	 * Return the RecordHandler's final Reduce phase process result.
	 * 
	 * @return Reduce phase processing result as a String.
	 */
	public abstract String getReduceResult();
	
	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instances that will be used by the ReduceRecordHandler
	 * to log to the Hadoop Task syslog file.
	 * @throws IllegalArgumentException Where logger is null or empty.
	 */
	public ReduceRecordHandler(Logger logger)
		throws IllegalArgumentException
	{
		super(logger);
	}
	
	
	//
	//	Public methods
	//
	
	/**
	 * Process a single record read from the post-Map phase data for the Reduce phase of processing.
	 * 
	 * @param record {@link java.util.HashMap} of parsed data. For Functions the {@link java.util.HashMap} is in the form of 
	 * <"FunctionName", "OutputValue"> and for Data Reporters the HashMap is in the form of
	 * <"DataReporterName", "FieldValue">.
	 */
	public abstract void handleReduceRecord(HashMap<String,String> record);
}
