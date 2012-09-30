/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.controllers;

import java.util.LinkedList;
import java.util.ListIterator;

import org.trommel.trommel.Field;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.MapRecordHandler;
import org.trommel.trommel.functions.DistinctMapper;
import org.trommel.trommel.functions.EmptyMapper;
import org.trommel.trommel.functions.LinearityMapper;
import org.trommel.trommel.functions.MaxMapper;
import org.trommel.trommel.functions.MinMapper;
import org.trommel.trommel.functions.VariabilityMapper;
import org.trommel.trommel.utilities.StringUtilities;

/**
 * Implementation of the Controller pattern for Trommel scenarios for profiling data during the Map
 * phase of processing.
 */
public class MapProfileController implements MapController
{
	//
	//	Private members
	//
	private Field[] fields = null;
	private LinkedList<MapRecordHandler> functions = new LinkedList<MapRecordHandler>();
	
	
	//
	//	Constructors
	//
	
	public MapProfileController(Field[] fields)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if (fields == null || fields.length == 0)
		{
			throw new IllegalArgumentException("Fields array cannot be null or empty.");
		}
		
		for (Field field : fields)
		{
			if (field == null)
			{
				throw new IllegalArgumentException("Fields array cannot contain null elements.");
			}
		}
		
		this.fields = fields;
	}
	
	
	//
	//	Public methods
	//
		

	
	public void addFunction(ProfileFunction function)
	{
		addFunction(function, null);
	}
	
	
	public void addFunction(ProfileFunction function, String parameter)
	{
		switch(function)
		{
			case Max:
				functions.addLast(new MaxMapper(fields));
				break;
			case Min:
				functions.addLast(new MinMapper(fields));
				break;
			case Distinct:
				functions.addLast(new DistinctMapper(fields));
				break;
			case Empty:
				functions.addLast(new EmptyMapper(fields));
				break;
			case Var:
				functions.addLast(new VariabilityMapper(fields));
				break;
			case Lin:
				if (StringUtilities.isNullOrEmpty(parameter))
				{
					// Instantiate the Linearity function with default
					functions.addLast(new LinearityMapper(fields));					
				}
				else
				{
					functions.addLast(new LinearityMapper(fields, Integer.parseInt(parameter)));
				}
				break;
		}
	}
	

	/**
	 * Process one record from the data set. The MapProfileController will invoke one or more 
	 * {@link org.trommel.trommel.MapRecordHandler} instances (i.e., Trommel functions) to profile the data, with 
	 * processing results stored in the {@link org.trommel.trommel.MapRecord}.
	 * 
	 * @param record The MapRecord to process and to be used to store processing results.
	 */
	public void handleMapRecord(MapRecord record) 
	{
		ListIterator<MapRecordHandler> iterator = functions.listIterator();
		
		while (iterator.hasNext())
		{
			MapRecordHandler handler = iterator.next();
			
			handler.handleMapRecord(record);
		}
	}
}
