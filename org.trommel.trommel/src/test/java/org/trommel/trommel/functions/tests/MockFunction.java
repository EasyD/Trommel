/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions.tests;

import java.util.HashMap;

import org.trommel.trommel.MapRecord;
import org.trommel.trommel.functions.Function;

//
//	Mock class to facilitate testing of the org.trommel.trommel.functions.Function 
//	abstract class.
//
public class MockFunction extends Function
{

	//
	//	Getters/setters
	//

	public String getHandlerName()
	{
		return "MockFunction";
	}
	
	public String getReduceResult()
	{
		return null;
	}
	
	
	//
	//	Constructors
	//
	
	public MockFunction(String[] fieldNames) 
		throws IllegalArgumentException 
	{
		super(fieldNames);
	}


	//
	//	Public methods
	//
	
	public void handleMapRecord(MapRecord record) 
	{ }

	public void handleReduceRecord(HashMap<String, String> record) 
	{ }
}
