/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.functions;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.TreeMap;

import org.trommel.trommel.FieldType;
import org.trommel.trommel.ReduceRecordHandler;
import org.trommel.trommel.utilities.Counter;

/**
 *	For the Map phase find the interstitial linearity for a {@link org.trommel.trommel.Field} as as a value ranging from 0.0 
 *	(no linearity) to 1.0 (very high linearity) for numeric Fields and the Rate of Discovery (ROD) for categorical Fields. 
 *	For numeric fields, the interstitial linearity is calculated from an approximate random sample of individual records
 *	sampled during the Map phase.
 */
public class LinearityReducer implements ReduceRecordHandler 
{
	//
	//	Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FUNCTION_NAME = "Linearity";

	
	//
	//	Private members
	//
	private FieldType fieldType; 
	private int recordCount = 0;
	private TreeMap<Double, Counter> values = null;
	private HashMap<String, Integer> discoveredValues = null; 
	
	
	//
	//	Getters/setters
	//
	
	/**
	 * Return the current calculation of {@link org.trommel.trommel.Field} variability.
	 * 
	 * @return The current variability calculation as a {@link java.lang.String}. A value of "-1.0" indicates
	 * that random sampling failed to produce any records for processing.
	 */
	public String getReduceResult()
	{
		// It's possible that we won't get any records from the Map phase due to random sampling
		if (recordCount == 0)
		{
			return "-1.0";
		}
		
		if (fieldType == FieldType.numeric)
		{
			// Get interstitial differences
			LinkedList<Double> differences = interstitialDifferences();
			LinkedList<Double> summations = performSummations(differences);
			double slopeOfRegressionLine = regressionLineSlope(differences, summations.get(0));
			double observationStandardDeviation = Math.sqrt(((differences.size() * summations.get(3)) - (summations.get(2) * summations.get(2))) / 
                                                             (differences.size() * (differences.size() - 1)));
			double differencesStandardDeviation = Math.sqrt(((differences.size() * summations.get(1)) - (summations.get(0) * summations.get(0))) / 
                                                             (differences.size() * (differences.size() - 1)));
			
			// Return the absolute value of the correlation coefficient for the interstitial differences
			return Double.toString(Math.abs(((slopeOfRegressionLine * observationStandardDeviation) / differencesStandardDeviation)));
		}
		else
		{
			// Rate of Discovery is simply the unique discovered values divided by record count
			return Double.toString((double)discoveredValues.keySet().size() / recordCount);
		}
	}
	
	
	//
	//	Constructors
	//
	
	/**
	 * Construct a LinearityReducer. In the case of numeric instances a default random sample rate of 
	 * 10% will be used.
	 * 
	 * @param fieldType Specifies if LinearityReducer is processing numeric or categorical data.
	 */
	public LinearityReducer(FieldType fieldType)
	{
		this.fieldType = fieldType;
		
		if (this.fieldType == FieldType.numeric)
		{
			values = new TreeMap<Double, Counter>();
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
		if (record.containsKey(FUNCTION_NAME))
		{	
			++recordCount;

			if (fieldType == FieldType.numeric)
			{
				Double value = new Double(record.get(FUNCTION_NAME));

				if (values.containsKey(value))
				{
					// Duplicate value, increment count
					values.get(value).increment();
				}
				else
				{
					// New value, add it to TreeMap
					Counter counter = new Counter();
					
					counter.increment();
					
					values.put(value, counter);
				}
			}
			else
			{
				if(!discoveredValues.containsKey(record.get(FUNCTION_NAME)))
				{
					// New value discovered
					discoveredValues.put(record.get(FUNCTION_NAME), 1);
				}
			}
		}
	}
	
	
	//
	//	Private/helper methods
	//
	
	private LinkedList<Double> interstitialDifferences()
	{
		// Create queue of interstitial differences
		Iterator<Double> iterator = values.keySet().iterator();
		LinkedList<Double> differences = new LinkedList<Double>();
		double previousValue = iterator.next().doubleValue();
		
		while (iterator.hasNext())
		{
			Double currentValue = iterator.next();
			int count = values.get(currentValue).getCount();
			
			for (int i = 0; i < count; ++i)
			{
				double temp = currentValue.doubleValue();
				
				differences.addLast(temp - previousValue);
				
				previousValue = temp;
			}
		}	
		
		return differences;
	}
	
	private LinkedList<Double> performSummations(LinkedList<Double> differences)
	{	
		//	Perform various mathematical sums to make later calculations easier
		LinkedList<Double> summations = new LinkedList<Double>();
		ListIterator<Double> iterator = differences.listIterator();
		double sumOfDifferences = 0.0;
		double sumOfSquaredDifferences = 0.0;
		double differencesCount = differences.size();
		
		while (iterator.hasNext())
		{
			double difference = iterator.next().doubleValue();
			
			sumOfDifferences += difference;
			sumOfSquaredDifferences += (difference * difference);
		}

		// Add sum of the insterstitial differences to list
		summations.addLast(sumOfDifferences);

		// Add sum of the squared interstitial differences to list
		summations.addLast(sumOfSquaredDifferences);
		
		// Each difference in the list is an "observation" (i.e., an X value), add the 
		// summation of the X values
		double sumOfDifferencesCount = ((differencesCount * (differencesCount + 1)) / 2);
		
		summations.addLast(sumOfDifferencesCount);
		
		// Add the summation of the squared observation values
		summations.addLast(sumOfDifferencesCount * (((2 * differencesCount) + 1 ) / 3));
		
		return summations;
	}

	private double regressionLineSlope(LinkedList<Double> differences, double sumOfDifferences)
	{
		// Calculate the slope of the regression line for the interstitial differences
		ListIterator<Double> iterator = differences.listIterator();
		double meanOfDifferences = sumOfDifferences / differences.size();
		double meanOfObservations = ((double)differences.size() + 1) / 2;
		int observation = 1;
		double slopeNumerator = 0.0;
//		double slopeDenominator = (differences.size() * sumOfSquaredObervations) - (sumOfObservations * sumOfObservations);
		double slopeDenominator = 0.0;
		
		while(iterator.hasNext())
		{
			double difference = iterator.next().doubleValue();
			
			slopeNumerator += (observation - meanOfObservations) * (difference - meanOfDifferences);
			slopeDenominator += (observation - meanOfObservations) * (observation - meanOfObservations);
			
			++observation;
		}
		
		return slopeNumerator / slopeDenominator;
	}	
}
