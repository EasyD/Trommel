/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.controllers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.trommel.trommel.ReduceRecordHandler;
import org.trommel.trommel.scripting.interpreters.MapInterpreter;


/**
 * Implementation of the Controller pattern for Trommel scenarios for profiling data during the Reduce
 * phase of processing.
 */
public class ReduceProfileController implements ReduceController 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String DELIMITER_REGEX = "\\Q" + MapInterpreter.DELIMITER + "\\E";
	
	//
	//	Private members
	//
	private Logger logger = null;
	private LinkedList<ReduceRecordHandler> recordHandlers = new LinkedList<ReduceRecordHandler>();
	private String header = null;
	
	
	//
	//	Getter/setters
	//
	
	/**
	 * @return Tab-delimited list of {@link org.trommel.trommel.ReduceRecordHandler} names used to process post-Map
	 * phase data. For example, "Field\tMax\tMin\tConfidence\tVariability"
	 */
	public String getHeader()
	{
		if (header == null)
		{
			StringBuffer buffer = new StringBuffer();
			boolean first = true;
			
			buffer.append("Field\t");
			
			for(ReduceRecordHandler handler : recordHandlers)
			{
				if (first)
				{
					first = false;
				}
				else
				{
					buffer.append("\t");
				}

				buffer.append(handler.getHandlerName());
			}
			
			header = buffer.toString();
		}
		
		return header;
	}
	
		
	/**
	 * @return Tab-delimited current results from all {@link org.trommel.trommel.ReduceRecordHandler} instances
	 * added to Controller.
	 */
	public String getFormattedResults()
	{
		StringBuffer output = new StringBuffer();

		for (int i = 0; i < recordHandlers.size() - 1; ++i)
		{
			output.append(recordHandlers.get(i).getReduceResult());
			output.append("\t");
		}
		
		output.append(recordHandlers.getLast().getReduceResult());
		
		return output.toString();
	}

	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used to log to the 
	 * Hadoop Task syslog file.
	 * @throws IllegalArgumentException Where logger is null.
	 */
	public ReduceProfileController(Logger logger)
		throws IllegalArgumentException
	{
		if (logger == null)
		{
			throw new IllegalArgumentException("Logger cannot be null.");
		}
		
		this.logger = logger;
	}
	
	
	//
	//	Public methods
	//
	
	/**
	 * Add a {@link org.trommel.trommel.ReduceRecordHandler} to controller for processing Reduce phase records.
	 * 
	 * @param recordHandler A ReduceRecordHandler instance.
	 * @throws IllegalArgumentException Where recordHandler is null.
	 */
	public void addRecordHandler(ReduceRecordHandler recordHandler)
		throws IllegalArgumentException
	{
		if (recordHandler == null)
		{
			logger.error("ReduceProfileController.addRecordHandler passed a null recordHandler.");
			
			throw new IllegalArgumentException("RecordHandler cannot be null.");
		}
		
		recordHandlers.addLast(recordHandler);
	}
	
	/**
	 * Process a single record read from the post-Map phase data for the Reduce phase of processing.
	 * 
	 * @param record One record from post-Map phase processing, specifically the output of one of more
	 * {@link org.trommel.trommel.MapRecordHandler} instances delimited as specified by the 
	 * {@link org.trommel.trommel.scripting.interpreters.MapInterpreter} class' static public DELIMITER 
	 * member.
	 */	
	public void handleReduceRecord(String record) 
	{
		HashMap<String, String> parsedRecords = parseRecords(record);
		ListIterator<ReduceRecordHandler> iterator = recordHandlers.listIterator();
		
		while (iterator.hasNext())
		{
			ReduceRecordHandler handler = iterator.next();
			
			handler.handleReduceRecord(parsedRecords);
		}
	}
	
	
	//
	//	Private/helper methods
	//
	
	private HashMap<String,String> parseRecords(String record)
	{
		// Split complete record into individual function outputs
		String[] functionOutputs = record.split(DELIMITER_REGEX);
		HashMap<String, String> parsedRecords = new HashMap<String, String>();
		
		for (String functionOutput : functionOutputs)
		{
			String[] outputPair = functionOutput.split("=");
			
			parsedRecords.put(outputPair[0], outputPair[1]);
		}
		
		return parsedRecords;
	}
}
