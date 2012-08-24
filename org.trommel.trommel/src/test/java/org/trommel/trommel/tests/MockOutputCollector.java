/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.mapred.OutputCollector;

//
//	Custom mock class for the MapReduce OutputCollector interface
//
public class MockOutputCollector<K, V> implements OutputCollector<K, V> 
{
	//
	//	Private members
	//
	private List<K> keys = new ArrayList<K>();
	private List<V> values = new ArrayList<V>();

	
	//
	//	Getters/setters 
	//
	public List<K> getKeys() 
	{
		return keys;
	}

	public List<V> getValues() 
	{
		return values;
	}
	
	
	//
	//	Public methods
	//
	public void collect(K key, V value) throws IOException 
	{
		keys.add(key);
		values.add(value);
	}
}
