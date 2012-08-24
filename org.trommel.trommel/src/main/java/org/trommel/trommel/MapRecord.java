/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel;

import java.io.IOException;
import java.util.Hashtable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.trommel.trommel.utilities.StringUtilities;


/**
 * Simplistic variant of the Unit of Work pattern. Represents all input data, and resulting 
 * output data, for Trommel's Map phase processing of a single record from the data set. 
 * Provides interface for serializing Map phase output data into a tabular form via a 
 * MapReduce {@link OutputCollector} and a contained {@link OutputSet} class.
 */
public final class MapRecord
{
	//
	// Private members
	//
	protected Hashtable<String, Field> fields = new Hashtable<String, Field>();
	private OutputSet outputSet = null;
	
	
	//
	//	Getters/setters
	//
	
	/**
	 * Returns the current value for a {@link Field}.
	 * 
	 * @param name Name of the Field.
	 * @return The current value for the Field as a {@link String}.
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
		
		if(!fields.containsKey(name))
		{
			throw new IllegalArgumentException("Field name " + name + " not recognized.");
		}
		
		return fields.get(name).getValue();
	}

	
	//
	//	Constructors
	//

	
	/**
	 * @param fields An array of {@link Field} instances specifying the structure of the data set.
	 * @param outputDelimiter A {@link String} denoting the characters used to delimit one {@link FunctionOutput} from another.
	 * @throws IllegalArgumentException Where the fields array is null or empty. Also thrown if outputDelimiter 
	 * is null or an empty string. All-whitespace strings are considered empty.
	 */
	public MapRecord(Field[] fields, String outputDelimiter)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if (fields == null)
		{
			throw new IllegalArgumentException("Fields array cannot be null.");
		}
		
		if (fields.length == 0)
		{
			throw new IllegalArgumentException("Fields array cannot be empty.");
		}
		
		for(Field field : fields)
		{
			// Store fields in a Hashtable for quick lookup, memory usage should be more than 
			// offset by efficiency gains given Hadoop-volume of lookups
			this.fields.put(field.getName(), field);
		}
		
		if (StringUtilities.isNullOrEmpty(outputDelimiter))
		{
			throw new IllegalArgumentException("Output delimiter cannot be null or empty.");
		}
		
		outputSet = new OutputSet(this.fields.keySet().toArray(new String[0]), outputDelimiter);
	}
	
	
	//
	//	Public methods
	//
	
	/**
	 * Add the results of a Function's processing for a {@link Field} in the MapRecord's {@link OutputSet}.
	 * 
	 * @param fieldName Name of the Field that was processed by the {@link org.trommel.trommel.functions.Function}.
	 * @param functionOutput The results of the Function's processing for the Field.
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
	 * Serialize the MapRecord's data in tabular form via a MapReduce {@link OutputCollector} and a contained
	 * {@link OutputSet}.
	 * 
	 * @param outputCollector Instance of the MapReduce OutputCollector interface to use for serialization.
	 * @throws IllegalArgumentException Where outputCollector is null.
	 * @throws IOException Where bubbled up from OutputCollector.
	 */
	public void serialize(OutputCollector<Text, Text> outputCollector)
		throws IllegalArgumentException,IOException
	{
		// Delegate to contained OutputSet
		outputSet.serialize(outputCollector);
	}	
}
