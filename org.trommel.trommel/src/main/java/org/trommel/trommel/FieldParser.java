/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel;


/**
 * Interface that defines the functionality for classes that parse fields from raw data read from the 
 * data set. 
 */
public interface FieldParser 
{
	/**
	 * Parse field values from a record of raw data read from the data set. 
	 * 
	 * @param record One record's worth of raw data read from the data set.
	 * @return Parsed field values as a {@link java.lang.String} array.
	 * @throws IllegalArgumentException Where record is null or empty. All-whitespace strings are considered
	 * empty.
	 */
	public String[] parse(String record)
		throws IllegalArgumentException;
}
