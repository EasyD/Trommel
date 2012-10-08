/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.utilities;

import java.util.HashMap;
import java.util.Iterator;


/**
 *	Helper class for maintaining field value frequency counts that are strings.
 */
public class FrequencyCounts 
{
	//
	//	Private members
	//
	HashMap<String, Counter> frequencyCounts = new HashMap<String, Counter>();
	
	
	//
	//	Public methods
	//
	
	/**
	 * Increment a field value frequency/count by one.
	 * 
	 * @param value The field value whose frequency/count should be incremented.
	 * @throws IllegalArgumentException Where value is null or empty. All-whitespace strings are considered empty.
	 */
	public void increment(String value)
		throws IllegalArgumentException
	{
		//	Check for illegal input
		if(StringUtilities.isNullOrEmpty(value))
		{
			throw new IllegalArgumentException("Value cannot be null or empty.");
		}
		
		if(!frequencyCounts.containsKey(value))
		{
			frequencyCounts.put(value, new Counter());
		}
		
		frequencyCounts.get(value).increment();
	}
	
	/**
	 * Serialize all the field value frequencies to a standard format suitable for writing as a file.
	 * Example format:<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Field1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Value1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;23<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Field1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Value2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;47<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Field1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Value3&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;12<br>
	 * 
	 * @param fieldName The field name to use in the serialization.
	 * @return Formatted output string.
	 */
	public String serialize(String fieldName)
	{
		StringBuffer buffer = new StringBuffer();
		Iterator<String> keys = frequencyCounts.keySet().iterator();

		while(keys.hasNext())
		{
			String value = keys.next();
			
			buffer.append(fieldName);
			buffer.append("\t");
			buffer.append(value);
			buffer.append("\t");
			buffer.append(frequencyCounts.get(value).toString());
			buffer.append("\n");
		}
		
		return buffer.toString();
	}	
}
