package org.trommel.trommel.controllers;

import org.apache.log4j.Logger;
import org.trommel.trommel.reporting.DataReporterReducer;
import org.trommel.trommel.utilities.StringUtilities;


/**
 * Implementation of the Controller pattern for Trommel scenarios for reporting data during the Reduce
 * phase of processing.
 */
public class ReduceReportController implements ReduceController 
{
	//
	//	Private members
	//
	private Logger logger = null;
	private DataReporterReducer reporter = null;
	
	
	//
	//	Getters/setters
	//
	
	/**
	 * @return Tab-delimited header for Reducer file output. Specifically, "Field\tContent\tCount"
	 */
	public String getHeader()
	{
		return "Field\tContent\tCount";
	}
	

	/**
	 * @return Tab-delimited current results from all {@link org.trommel.trommel.reporting.DataReporterReducer} instances
	 * added to Controller.
	 */
	public String getFormattedResults()
	{
		return reporter.getReduceResult();
	}
	
	
	//
	//	Constructors
	//
	
	public ReduceReportController(Logger logger, String fieldName)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if (logger == null)
		{
			throw new IllegalArgumentException("Logger cannot be null.");
		}
		
		if (StringUtilities.isNullOrEmpty(fieldName))
		{
			logger.error("ReduceReportController construct passed a null or empty fieldName.");
			
			throw new IllegalArgumentException("FieldName cannot be null or empty.");
		}
		
		this.logger = logger;
		reporter = new DataReporterReducer(logger, fieldName);
	}
	
	
	//
	//	Public methods
	//
	
	/**
	 * Process a single field value read from the post-Map phase data for the Reduce phase of processing.
	 * 
	 * @param record One record from post-Map phase processing, specifically the output of the 
	 * {@link org.trommel.trommel.reporting.DataReporterMapper} class.
	 * @throws IllegalArgumentException Where record is null or empty. All-whitespace strings are considered empty.
	 */
	public void handleReduceRecord(String record) 
		throws IllegalArgumentException
	{
		// Check for illegal input
		if(StringUtilities.isNullOrEmpty(record))
		{
			logger.error("ReduceReportController.handleReduceRecord was passed a null or empty record.");
			
			throw new IllegalArgumentException("Record cannot be null or empty.");
		}
		
		String[] outputPair = record.split("=");
		
		reporter.handleReduceRecord(outputPair[1]);
	}
}
