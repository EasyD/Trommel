/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.MapContext;
import org.trommel.trommel.utilities.StringUtilities;


/**
 *	Implementation of the Record Set pattern. Represents the tabular output of Trommel's processing of a single record in a data set and
 *	serializing each row via a Hadoop {@link org.apache.hadoop.mapreduce.MapContext}. Example serialized output.<br><br>
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     Field1 Max=42||Min=43||etc...<br>
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     Field2 Max=7||Min=134||etc...<br>
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     Field3 Max=6785||Min=567||etc...<br>
 */
public final class OutputSet 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FIELD_NAME_ERROR_STRING = "Field names cannot be null or an empty string.";
	
	
	//
	//	Private members
	//
	private Hashtable<String, List<FunctionOutput>> outputSet = new Hashtable<String, List<FunctionOutput>>();
	private String outputDelimiter;
	
	
	//
	//	Constructors
	//
	
	/**
	 * @param fieldNames The names of {@link Field} instances for which {@link FunctionOutput} instances can be collected.
	 * @param outputDelimiter {@link java.lang.String} value used to delimit one serialized FunctionOutput instance from another.
	 * @exception IllegalArgumentException Where fieldNames' length is 0 or contains a null/empty string. 
	 * All-whitespace strings are considered empty.
	 */
	public OutputSet(String[] fieldNames, String outputDelimiter)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if (fieldNames == null)
		{
			throw new IllegalArgumentException("Field names array cannot be null.");
		}
		
		if (fieldNames.length == 0)
		{
			throw new IllegalArgumentException("OutputSet must contain at least one field name.");
		}
		
		if (StringUtilities.isNullOrEmpty(outputDelimiter))
		{
			throw new IllegalArgumentException("OutputDelimiter cannot be null or empty.");
		}

		// Initialize Hashtable and output delimiter
		for (String fieldName : fieldNames)
		{
			// Check for illegal input
			if (StringUtilities.isNullOrEmpty(fieldName))
			{
				throw new IllegalArgumentException(FIELD_NAME_ERROR_STRING);
			}
			
			outputSet.put(fieldName, new ArrayList<FunctionOutput>());
		}
		
		this.outputDelimiter = outputDelimiter;
	}

	
	//
	//	Public methods
	//
	
	/**
	 * Add the results of a {@link MapRecordHandler} instance's processing for a {@link Field} in the {@link OutputSet}.
	 * 
	 * @param fieldName Name of the Field that was processed by the Function.
	 * @param functionOutput The results of the MapRecordHandler's processing for the Field.
	 * @throws IllegalArgumentException Where fieldName is null or empty or does not match any of the OutputSet's Fields.
	 * Also thrown when functionOutput is null. All-whitespace strings are considered empty.
	 */
	public void addFunctionOutput(String fieldName, FunctionOutput functionOutput)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if (StringUtilities.isNullOrEmpty(fieldName))
		{
			throw new IllegalArgumentException(FIELD_NAME_ERROR_STRING);
		}
		
		if (functionOutput == null)
		{
			throw new IllegalArgumentException("FunctionOutput cannot be null.");
		}
		
		if (outputSet.containsKey(fieldName))
		{
			// Add functionOutput to the set
			outputSet.get(fieldName).add(functionOutput);
		}
		else
		{
			throw new IllegalArgumentException("Field name not recognized.");
		}
	}
		
	/**
	 * Serialize the {@link OutputSet} data in tabular form via a MapReduce {@link org.apache.hadoop.mapreduce.MapContext}.
	 * 
	 * @param context Instance of the MapReduce MapContext interface to use for serialization.
	 * @throws IllegalArgumentException Where context is null.
	 * @throws IOException Where bubbled up from context.
	 * @throws InterruptedException Where bubbled up from context.
	 */
	public void serialize(MapContext<LongWritable, Text, Text, Text> context)
		throws IllegalArgumentException, IOException, InterruptedException
	{
		// Check for illegal input
		if (context == null)
		{
			throw new IllegalArgumentException("MapContext cannot be null.");
		}
		
		StringBuffer value = null;
		Enumeration<String> keys = outputSet.keys();

		while (keys.hasMoreElements())
		{
			String fieldName = keys.nextElement();
			List<FunctionOutput> functionOutputs = outputSet.get(fieldName);
			
			if (functionOutputs.size() > 0)
			{			
				value = new StringBuffer();
				
				for (int i = 0; i < functionOutputs.size() - 1; i++)
				{
					value.append(functionOutputs.get(i).serialize());
					value.append(outputDelimiter);
				}
				
				value.append(functionOutputs.get(functionOutputs.size() - 1).serialize());			
	
				// Write record to HDFS
				context.write(new Text(fieldName), new Text(value.toString()));
			}
		}
	}
}
