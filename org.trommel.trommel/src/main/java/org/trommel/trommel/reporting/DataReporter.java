/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.reporting;

import java.util.HashMap;

import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.RecordHandler;
import org.trommel.trommel.FrequencyCounts;
import org.trommel.trommel.utilities.StringUtilities;

/**
 *	Reports value frequencies (i.e., counts) for a specified field in a data set.
 */
public class DataReporter implements RecordHandler 
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
	 * Return the name of the DataReporter.
	 * 
	 * @return The string value of "DataReporter".
	 */
	public String getHandlerName()
	{
		return REPORTER_NAME;
	}
		
	/**
	 * Return the field value frequencies to a standard format suitable for writing as a file.
	 * Example format:<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FIELD&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CONTENT&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;COUNT<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Field1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Value1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;23<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Field1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Value2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;47<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Field1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Value3&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;12<br>
	 * 
	 * @return The formatted output {@link java.lang.String}.
	 */
	public String getReduceResult()
	{
		return frequencyCounts.serialize(fieldName);
	}

	
	//
	//	Constructors
	//
	
	/**
	 * @param fieldName The field in the Record which will be reported on.
	 * @throws IllegalArgumentException Where field is null or empty. All-whitespace strings are considered 
	 * empty.
	 */
	public DataReporter(String fieldName)
		throws IllegalArgumentException
	{		
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
	 * Process a single {@link MapRecord} read from the data set for the Map phase of building a data
	 * report (i.e., field value frequencies).
	 * 
	 * @param record The MapRecord containing the data read from data set and any results of Map phase 
	 * maximum value processing.
	 * @throws IllegalArgumentException Where bubbled up from passed-in MapRecord.
	 */
	public void handleMapRecord(MapRecord record) 
	{
		// Data reporting is pretty easy, just output the values by field for counting in Reduce phase
		record.addFunctionOutput(fieldName, new FunctionOutput(REPORTER_NAME, record.getFieldValue(fieldName))); 
	}

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
