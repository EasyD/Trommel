/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.utilities;

/**
 *	Helper class for maintaining counts in conjunction with Java generic collections (e.g., HashMap and TreeMap).
 */
public class Counter 
{
	//
	//	Private members
	//
	private int count = 0;
	
	
	//
	//	Getters/setters
	//
	
	/**
	 * @return The current count.
	 */
	public int getCount()
	{
		return count;
	}

	
	//
	//	Public methods
	//
		
	/**
	 * Increment the current count by one.
	 */
	public void increment()
	{
		++count;
	}
	
	/**
	 * Return the current integer count as a String. 
	 */
	@Override
	public String toString()
	{
		return Integer.toString(count);
	}
}
