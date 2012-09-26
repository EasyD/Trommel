/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.interpreters;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.ListIterator;

import org.apache.hadoop.io.Text;
import org.trommel.trommel.FieldInstance;
import org.trommel.trommel.FieldParser;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.MapRecord;
import org.trommel.trommel.MapRecordHandler;
import org.trommel.trommel.SimpleFieldParser;
import org.trommel.trommel.functions.DistinctMapper;
import org.trommel.trommel.functions.MaxMapper;
import org.trommel.trommel.functions.MinMapper;


/**
 *	The SableCC Visitor for interpreting the TrommelScript AST for the Map phase of processing.
 */
public class MapInterpreter 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	public final static String DELIMITER = "*|*";
	
	
	//
	//	Private members
	//
	private FieldParser fieldParser;
	private LinkedList<MapRecordHandler> recordHandlers = new LinkedList<MapRecordHandler>();
	private FieldInstance[] fields = null;
	

	//
	//	Public methods
	//
	
	/**
	 * @param value A record of raw data read from the data set
	 * @return A {@link org.trommel.trommel.MapRecord} parsed from record read from the data set.
	 * @throws ParseException
	 */
	public MapRecord handleRecord(Text value)
		throws ParseException
	{
		// TODO - Remove this code once SableCC support is implemented.
		if (recordHandlers.size() == 0)
		{
			fieldParser = new SimpleFieldParser();
			
			fields = new FieldInstance[3];
			
			fields[0] = new FieldInstance("Column1", FieldType.numeric);
			fields[1] = new FieldInstance("Column2", FieldType.categorical);
			fields[2] = new FieldInstance("Column3", FieldType.numeric);
			
			MaxMapper max = new MaxMapper(fields);
			MinMapper min = new MinMapper(fields);
			DistinctMapper distinct = new DistinctMapper(fields);
			
			recordHandlers.addLast(max);
			recordHandlers.addLast(min);
			recordHandlers.addLast(distinct);
		}
		// TODO - End of code to be removed
		
		
		//	Parse raw data.
		String[] fieldValues = fieldParser.parse(value.toString());

		// Check for invalid output
		if (fieldValues.length != fields.length)
		{
			StringBuffer buffer = new StringBuffer();
			
			buffer.append("Parsed ");
			buffer.append(Integer.toString(fieldValues.length));
			buffer.append(" fields from the data, but ");
			buffer.append(Integer.toString(fields.length));
			buffer.append(" fields were specified in the TrommelScript.");
			
			throw new ParseException(buffer.toString(), 0);
		}
		
		// Populate FieldInstances with current record's field values
		for (int i = 0; i < fieldValues.length; ++i)
		{
			fields[i].setValue(fieldValues[i]);
		}

		// Run the record by all the handlers
		MapRecord record = new MapRecord(fields, DELIMITER);
		ListIterator<MapRecordHandler> iterator = recordHandlers.listIterator();
		
		while (iterator.hasNext())
		{
			iterator.next().handleMapRecord(record);
		}
		
		return record;
	}	
}
