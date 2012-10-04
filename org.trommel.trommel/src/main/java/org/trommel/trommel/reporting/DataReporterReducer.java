/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.reporting;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.trommel.trommel.ReduceRecordHandler;
import org.trommel.trommel.utilities.FrequencyCounts;
import org.trommel.trommel.utilities.StringUtilities;


/**
 *	For the Reduce phase report value frequencies (i.e., counts) for a specified {@link org.trommel.trommel.Field} in a data set.
 */
public class DataReporterReducer extends ReduceRecordHandler 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String REPORTER_NAME = "DataReporter";

	//
	//	Private members
	//
	private String fieldName;
	private FrequencyCounts frequencyCounts = null;

	//
	//	Getters/setters
	//
		
	/**
	 * Return the {@link org.trommel.trommel.Field} value frequencies to a standard format suitable for writing as a file.
	 * Example format:<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FIELD&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CONTENT&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;COUNT<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Field1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Value1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;23<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Field1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Value2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;47<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Field1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Value3&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;12<br>
	 * 
	 * @return The formatted output as a {@link java.lang.String}.
	 */
	public String getReduceResult()
	{
		return frequencyCounts.serialize(fieldName);
	}
	
	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the DataReporterReducer
	 * to log to the Hadoop Task syslog file.
	 * @param fieldName The {@link org.trommel.trommel.Field} which will be reported on.
	 * @throws IllegalArgumentException Where logger is null or field is null or empty. All-whitespace strings are considered 
	 * empty.
	 */
	public DataReporterReducer(Logger logger, String fieldName)
		throws IllegalArgumentException
	{		
		super(logger);
		
		if (StringUtilities.isNullOrEmpty(fieldName))
		{
			throw new IllegalArgumentException("FieldName cannot be null or empty.");
		}
		
		this.fieldName = fieldName;
	}

	
	//
	//	Public methods
	//

	/**
	 * Process a single record read from the post-Map phase data for the Reduce phase of processing.
	 * 
	 * @param record {@link java.util.HashMap} of parsed data. For DataReporters the {@link java.util.Hashtable} is in the form of 
	 * <"DataReporterName", "FieldValue">.
	 */
	public void handleReduceRecord(HashMap<String, String> record) 
	{	
		if (frequencyCounts == null)
		{
			frequencyCounts = new FrequencyCounts();
		}
		
		// Data reporter values might not be in every reducer record
		if (record.containsKey(REPORTER_NAME))
		{
			frequencyCounts.increment(record.get(REPORTER_NAME));
		}
	}
}
