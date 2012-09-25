/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel;

import java.util.ArrayList;

import org.trommel.trommel.utilities.StringUtilities;


/**
 * A {@link FieldParser} implementation that parses fields based on simple string delimiters. By default,
 * class parses using a tab delimiter.
 */
public class SimpleFieldParser implements FieldParser 
{
	//
	//	Private members
	//
	private String delimiter;
	
	//
	//	Constructors
	//
	
	/**
	 * Parse fields based on a tab delimiter.
	 */
	public SimpleFieldParser()
	{
		delimiter = "\t";
	}
	
	
	/**
	 * @param delimiter The {@link String} delimiter to use in parsing.
	 * @throws IllegalArgumentException Where delimiter is null or empty. All-whitespace strings are considered
	 * empty.
	 */
	public SimpleFieldParser(String delimiter)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if (StringUtilities.isNullOrEmpty(delimiter))
		{
			throw new IllegalArgumentException("Delimiter string cannot be null or empty.");
		}
		
		this.delimiter = delimiter;
	}
	
	
	//
	//	Public methods
	//
		
	/**
	 * Parse field values from a record of raw data read from the data set. 
	 * 
	 * @param record One record's worth of raw data read from the data set.
	 * @return Parsed field values as {@link java.lang.String} array.
	 * @throws IllegalArgumentException Where record is null or empty. All-whitespace strings are considered
	 * empty.
	 */
	public String[] parse(String record)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if (StringUtilities.isNullOrEmpty(record))
		{
			throw new IllegalArgumentException("Record string cannot be null or empty.");
		}
		
		int startIndex = 0;
		int endIndex = record.indexOf(delimiter);
		ArrayList<String> fields = new ArrayList<String>();
		
		while (endIndex != -1)
		{
			fields.add(record.substring(startIndex, endIndex));
			
			startIndex = endIndex + delimiter.length();
			endIndex = record.indexOf(delimiter, startIndex);
		}
		
		fields.add(record.substring(startIndex));
				
		return fields.toArray(new String[0]);
	}
}
