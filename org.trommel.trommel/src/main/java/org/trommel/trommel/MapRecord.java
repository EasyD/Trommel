/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.MapContext;
import org.trommel.trommel.utilities.StringUtilities;


/**
 * Simplistic variant of the Unit of Work pattern. Represents all input data, and resulting 
 * output data, for Trommel's Map phase processing of a single record from the data set. 
 * Provides interface for serializing Map phase output data into a tabular form via a 
 * MapReduce {@link org.apache.hadoop.mapreduce.MapContext} and a contained {@link OutputSet} class.
 */
public class MapRecord
{
	//
	// Private members
	//
	protected HashMap<String, FieldInstance> fieldInstances = new HashMap<String, FieldInstance>();
	private OutputSet outputSet = null;
	
	
	//
	//	Getters/setters
	//
		
	/**
	 * Returns the current value for a {@link Field}.
	 * 
	 * @param name Name of the Field.
	 * @return The current value for the Field as a {@link java.lang.String}.
	 * @throws IllegalArgumentException Where name is null or empty. Also thrown when field name is not
	 * recognized. All-whitespace strings are considered empty.
	 */
	public String getFieldValue(String name)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if (StringUtilities.isNullOrEmpty(name))
		{
			throw new IllegalArgumentException("Field name can't be null or empty.");			
		}
		
		if(!fieldInstances.containsKey(name))
		{
			throw new IllegalArgumentException("Field name " + name + " not recognized.");
		}
		
		return fieldInstances.get(name).getValue();
	}
	
	
	//
	//	Constructors
	//

	/**
	 * @param fieldInstances An array of {@link Field} instances specifying the structure of the data set.
	 * @param outputDelimiter A {@link java.lang.String} denoting the characters used to delimit one {@link FunctionOutput} from another.
	 * @throws IllegalArgumentException Where the fields array is null or empty. Also thrown if outputDelimiter 
	 * is null or an empty string. All-whitespace strings are considered empty.
	 */
	public MapRecord(FieldInstance[] fieldInstances, String outputDelimiter)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if (fieldInstances == null)
		{
			throw new IllegalArgumentException("Fields array cannot be null.");
		}
		
		if (fieldInstances.length == 0)
		{
			throw new IllegalArgumentException("Fields array cannot be empty.");
		}
		
		for(FieldInstance fieldInstance : fieldInstances)
		{
			// Store fields in a HashMap for quick lookup, memory usage should be more than 
			// offset by efficiency gains given Hadoop-volume of lookups
			this.fieldInstances.put(fieldInstance.getName(), fieldInstance);
		}
		
		if (StringUtilities.isNullOrEmpty(outputDelimiter))
		{
			throw new IllegalArgumentException("Output delimiter cannot be null or empty.");
		}
		
		outputSet = new OutputSet(this.fieldInstances.keySet().toArray(new String[0]), outputDelimiter);
	}
	
	
	//
	//	Public methods
	//
	
	/**
	 * Add the results of a {@link MapRecordHandler} instance's processing for a {@link Field} in the MapRecord's {@link OutputSet}.
	 * 
	 * @param fieldName Name of the Field that was processed by the {@link MapRecordHandler}.
	 * @param functionOutput The results of the MapRecordHandler's processing for the Field.
	 * @throws IllegalArgumentException Where fieldName is null or empty or does not match any of the OutputSet's Fields.
	 * Also thrown when functionOutput is null. All-whitespace strings are considered empty.
	 */
	public void addFunctionOutput(String fieldName, FunctionOutput functionOutput)
		throws IllegalArgumentException
	{
		// Delegate to contained OutputSet
		outputSet.addFunctionOutput(fieldName, functionOutput);
	}
		
	/**
	 * Serialize the MapRecord's data in tabular form via a {org.apache.hadoop.mapreduce.MapContext} and a contained
	 * {@link OutputSet}.
	 * 
	 * @param context Instance of the MapReduce MapContext to use for serialization.
	 * @throws IllegalArgumentException Where context is null.
	 * @throws IOException Where bubbled up from context.
	 * @throws InterruptedException Where bubbled up from context.
	 */
	public void serialize(MapContext<LongWritable, Text, Text, Text> context)
		throws IllegalArgumentException, IOException, InterruptedException
	{
		outputSet.serialize(context);
	}
}
