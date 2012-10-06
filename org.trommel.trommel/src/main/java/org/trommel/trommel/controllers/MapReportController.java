/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.controllers;

import org.apache.log4j.Logger;
import org.trommel.trommel.Field;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.reporting.DataReporterMapper;

/**
 * Implementation of the Controller pattern for Trommel scenarios for reporting data during the Map
 * phase of processing.
 */
public class MapReportController implements MapController
{
	//
	//	Private members
	//
	private DataReporterMapper dataReporter = null;
	
	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the instantiated
	 * {@link org.trommel.trommel.reporting.DataReporterMapper} instances to log to the Hadoop Task syslog file.
	 * @param fields The {@link org.trommel.trommel.Field} instances to be processed during the Map phase.
	 * @throws IllegalArgumentException Where logger is null or fields array is null or empty. Also thrown if any of the fields
	 * are null or empty.
	 */
	public MapReportController(Logger logger, Field[] fields)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if (logger == null)
		{
			throw new IllegalArgumentException("Logger cannot be null.");
		}

		if (fields == null || fields.length == 0)
		{
			logger.error("Null or empty Fields array passed to MapProfileController constructor.");
			
			throw new IllegalArgumentException("Fields array cannot be null or empty.");
		}
		
		for (Field field : fields)
		{
			if (field == null)
			{
				logger.error("Null Fields array element passed to MapProfileController constructor.");
				
				throw new IllegalArgumentException("Fields array cannot contain null elements.");
			}
			
			logger.debug(String.format("MapProfileController constructed with Field: Name = %1$s, FieldType = %2$s.",
					     field.getName(), field.getType()));
		}
		
		dataReporter = new DataReporterMapper(logger, fields);
	}


	//
	//	Public methods
	//
		
	/**
	 * Process one record from the data set. The MapReportController will invoke one or more 
	 * {@link org.trommel.trommel.reporting.DataReporterMapper} instances to report on the data, with 
	 * processing results stored in the {@link org.trommel.trommel.MapRecord}.
	 * 
	 * @param record The MapRecord to process and to be used to store processing results.
	 */
	public void handleMapRecord(MapRecord record) 
	{
		dataReporter.handleMapRecord(record);
	}
}
