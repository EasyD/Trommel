/*
 * Copyright 2012 Dave Langer
 *    
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package org.trommel.trommel.functions;

import java.util.HashMap;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.log4j.Logger;
import org.trommel.trommel.FieldType;
import org.trommel.trommel.ReduceRecordHandler;


/**
 *	For the Reduce phase find the variability for a {@link org.trommel.trommel.Field} as the sample 
 *	standard deviation for numeric Fields and the Rate of Discovery (ROD) for categorical Fields.
 *	Missing values are treated as zeroes for numeric fields and null values for categorical Fields
 * 	for the purposes of calculation.
 */
public class VariabilityReducer extends ReduceRecordHandler 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String HANDLER_NAME = "Variability";

	
	//
	//	Private members
	//
	private FieldType fieldType; 
	private int recordCount = 0;
	private SummaryStatistics stats = null;
	private HashMap<String, Integer> discoveredValues = null; 
	
			
	//
	//	Getters/setters
	//
	
	/**
	 * Return the name of the Variability function.
	 * 
	 * @return The string value of "Variability".
	 */
	public String getHandlerName()
	{
		return HANDLER_NAME;
	}

	/**
	 * Return the current calculation of {@link org.trommel.trommel.Field} variability.
	 * 
	 * @return The current variability calculation as a {@link java.lang.String}.
	 */
	public String getReduceResult()
	{
		if (fieldType == FieldType.numeric)
		{
			double sd = stats.getStandardDeviation();
			
			logger.debug(String.format("VariabilityReducer has current sample standard deviation of %1$f.", sd));

			// Return the sample standard deviation as a String
			return Double.toString(sd);
		}
		else
		{
			double rod = (double)discoveredValues.keySet().size() / recordCount;
			
			logger.debug(String.format("VariabilityReducer has current rate of discovery of %1$f", rod));
			
			// Rate of Discovery is simply the unique discovered values divided by record count
			return Double.toString(rod);
		}
	}
	
	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the RequiredReducer
	 * @param fieldType Specifies if VariabilityReducer is processing numeric or categorical data.
	 * 
	 * @throws IllegalArgumentException Where logger is null.
	 */
	public VariabilityReducer(Logger logger, FieldType fieldType)
		throws IllegalArgumentException
	{
		super(logger);
		
		this.fieldType = fieldType;
		
		if (this.fieldType == FieldType.numeric)
		{
			stats = new SummaryStatistics();
		}
		else
		{
			discoveredValues = new HashMap<String, Integer>();
		}
	}


	//
	//	Public methods
	//
	
	/**
	 * Process a single record read from the post-Map phase data for the Reduce phase of processing.
	 * 
	 * @param record {@link java.util.HashMap} of parsed data in the form of <"FunctionName", "OutputValue">.
	 * @exception NumberFormatException Where values are not numeric.
	 */
	public void handleReduceRecord(HashMap<String, String> record) 
		throws NumberFormatException
	{
		if (record.containsKey(HANDLER_NAME))
		{	
			++recordCount;

			if (fieldType == FieldType.numeric)
			{
				// Add numeric value to the SummaryStatistics instance
				stats.addValue(Double.parseDouble(record.get(HANDLER_NAME)));

				// This method is called at scale, optimize logging
				if (logger.isDebugEnabled())
				{
					logger.debug(String.format("VariabilityReducer.handleReduceRecord added value of %1$s.", record.get(HANDLER_NAME)));
				}
			}
			else
			{
				if(!discoveredValues.containsKey(record.get(HANDLER_NAME)))
				{
					// New value discovered
					discoveredValues.put(record.get(HANDLER_NAME), 1);

					// This method is called at scale, optimize logging
					if (logger.isDebugEnabled())
					{
						logger.debug(String.format("VariabilityReducer.handleReduceRecord added value of %1$s.", record.get(HANDLER_NAME)));
					}
				}
			}
		}
	}
}
