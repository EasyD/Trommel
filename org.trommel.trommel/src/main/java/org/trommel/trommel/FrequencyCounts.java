/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel;

import java.util.Enumeration;
import java.util.Hashtable;

import org.trommel.trommel.utilities.StringUtilities;

/**
 *	Helper class for maintaining field value frequency counts.
 */
public class FrequencyCounts 
{
	//
	//	Private members
	//
	Hashtable<String, Counter> frequencyCounts = new Hashtable<String, Counter>();
	
	
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
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FIELD&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CONTENT&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;COUNT<br>
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
		Enumeration<String> keys = frequencyCounts.keys();

		buffer.append("FIELD\t");
		buffer.append("CONTENT\t");
		buffer.append("COUNT\n");
		
		while(keys.hasMoreElements())
		{
			String value = keys.nextElement();
			
			buffer.append(fieldName);
			buffer.append("\t");
			buffer.append(value);
			buffer.append("\t");
			buffer.append(frequencyCounts.get(value).toString());
			buffer.append("\n");
		}
		
		return buffer.toString();
	}
	
	private class Counter
	{
		//
		//	Private members
		//
		private int count = 0;
		
		//
		//	Public methods
		//
		
		public void increment()
		{
			++count;
		}
		
		@Override
		public String toString()
		{
			return Integer.toString(count);
		}
	}
}
