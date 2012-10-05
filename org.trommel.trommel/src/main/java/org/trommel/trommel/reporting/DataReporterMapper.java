/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.reporting;

import org.apache.log4j.Logger;
import org.trommel.trommel.FunctionOutput;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.MapRecordHandler;
import org.trommel.trommel.utilities.StringUtilities;


//	TODO - Need to refactor for multiple fields!
/**
 *	Execute the Map phase for reporting value frequencies (i.e., counts) for a specified {@link org.trommel.trommel.Field} 
 *	in a data set.
 */
public class DataReporterMapper extends MapRecordHandler 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String REPORTER_NAME = "DataReporter";

	
	//
	//	Private members
	//
	private String fieldName;
	

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

		
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the DataReporterMapper
	 * to log to the Hadoop Task syslog file.
	 * @param fieldName The {@link org.trommel.trommel.Field} in the Record which will be reported on.
	 * @throws IllegalArgumentException Where logger is null or fieldName is null or empty. All-whitespace strings are considered 
	 * empty.
	 */
	public DataReporterMapper(Logger logger, String fieldName)
		throws IllegalArgumentException
	{		
		super(logger);
		
		if (StringUtilities.isNullOrEmpty(fieldName))
		{
			logger.error("DataReporterMapper constructor passed a null or empty FieldName.");
			
			throw new IllegalArgumentException("FieldName cannot be null or empty.");
		}
		
		this.fieldName = fieldName;
	}
	
	
	//
	//	Public methods
	//
	
	/**
	 * Process a single {@link org.trommel.trommel.MapRecord} read from the data set for the Map phase of building a data
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

		// This method is called at scale, optimize logging
		if (logger.isDebugEnabled())
		{
			logger.debug(String.format("DataReporterMapper.handleMapRecord added output of fieldValue %1$s for Field %2$s.",
					                   record.getFieldValue(fieldName), fieldName));
		}				
	}
}
