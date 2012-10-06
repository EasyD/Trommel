/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel;

import java.text.ParseException;
import java.util.LinkedList;

import org.trommel.trommel.utilities.StringUtilities;


/**
 * A {@link RecordParser} implementation that parses fields based on simple string delimiters. By default,
 * class parses using a tab delimiter.
 */
public class SimpleRecordParser implements RecordParser
{
	//
	//	Private members
	//
	private LinkedList<FieldInstance> fields = null;
	private String fieldDelimiter = null; 
	private String recordDelimiter = null;
	
	
	//
	//	Constructors
	//
	
	
	/**
	 * Instantiate a SimpleRecordParser using the default of tab-delimited fields.
	 * 
	 * @param fields Array of {@link Field} instances that specify the data contained in the data set.
	 * @param recordDelimiter {@link java.lang.String} value that will be used to delimit values in the 
	 * parsed/returned {@link MapRecord}.
	 * @throws IllegalArgumentException When fields array is null or empty. Also thrown if any element in the
	 * fields array is null or if recordDelimiter is null or empty. All-whitespace strings are treated as empty.
	 */
	public SimpleRecordParser(Field[] fields, String recordDelimiter)
		throws IllegalArgumentException
	{
		this(fields, "\t", recordDelimiter);
	}
	
	
	/**
	 * @param fields Array of {@link Field} instances that specify the data contained in the data set.
	 * @param fieldDelimiter {@link java.lang.String} value that delimits one field value from another in the 
	 * data set.
	 * @param recordDelimiter {@link java.lang.String} value that will be used to delimit values in the 
	 * parsed/returned {@link MapRecord}.
	 * @throws IllegalArgumentException When fields array is null or empty. Also thrown if any element in the
	 * fields array is null, if fieldDelimiter is null/empty, or if recordDelimiter is null/empty.
	 */
	public SimpleRecordParser(Field[] fields, String fieldDelimiter, String recordDelimiter)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if (fields == null || fields.length == 0)
		{
			throw new IllegalArgumentException("Fields array cannot be null or empty.");
		}
		
		if (fieldDelimiter == null || fieldDelimiter.length() == 0)
		{
			throw new IllegalArgumentException("FieldDelimiter cannot be null or empty.");
		}
		
		if (recordDelimiter == null || recordDelimiter.length() == 0)
		{
			throw new IllegalArgumentException("RecordDelimiter cannot be null or empty.");
		}

		this.fields = new LinkedList<FieldInstance>();
		
		for (Field field : fields)
		{
			if (field == null)
			{
				throw new IllegalArgumentException ("Fields array cannot contain null values.");
			}
			
			this.fields.addLast(field.getInstance());
		}

		this.fieldDelimiter = fieldDelimiter;
		this.recordDelimiter = recordDelimiter;
	}
	
	
	//
	//	Public methods
	//	
	
	/**
	 * Parse field values from a record of raw data read from the data set. 
	 * 
	 * @param record One record's worth of raw data read from the data set.
	 * @return Parsed field values as a {@link MapRecord} instance.
	 * @throws IllegalArgumentException If record is null or empty. All-whitespace string are treated as empty.
	 * @throws ParseException  When errors are encountered parsing the record String.
	 */
	public MapRecord parse(String record) 
		throws IllegalArgumentException, ParseException 
	{
		// Check for illegal input
		if (StringUtilities.isNullOrEmpty(record))
		{
			throw new IllegalArgumentException("Record string cannot be null or empty.");
		}

		try
		{
			int startIndex = 0;
			int endIndex = record.indexOf(fieldDelimiter);
			
			for (int i = 0; i < fields.size() - 1; ++i)
			{
				fields.get(i).setValue(record.substring(startIndex, endIndex));
	
				startIndex = endIndex + fieldDelimiter.length();
				endIndex = record.indexOf(fieldDelimiter, startIndex);
			}
	
			fields.getLast().setValue(record.substring(startIndex));
		}
		catch (Exception e)
		{
			// Encountered error, don't log will be handled by TrommelMapper/Reducer
			StringBuffer buffer = new StringBuffer();
			
			buffer.append("Error encountered parsing record. Exception message:\n");
			buffer.append(e.getMessage());
			
			throw new ParseException(buffer.toString(), 0);
		}
		
		return new MapRecord(fields.toArray(new FieldInstance[0]), recordDelimiter);
	}

}
