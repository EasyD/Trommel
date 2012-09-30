/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel;

import java.text.ParseException;


/**
 * Interface that defines the functionality for classes that parse fields from raw record data read from the 
 * data set and return {@link MapRecord} instances. 
 */
public interface RecordParser 
{
	/**
	 * Parse field values from a record of raw data read from the data set. 
	 * 
	 * @param record One record's worth of raw data read from the data set as a {@link java.lang.String}.
	 * @return Parsed field values as a {@link MapRecord} instance.
	 * @throws IllegalArgumentException If record is null or empty. All-whitespace string are treated as empty.
	 * @throws ParseException  When errors are encountered parsing the record String.
	 */
	public MapRecord parse(String record)
		throws IllegalArgumentException, ParseException;
}
