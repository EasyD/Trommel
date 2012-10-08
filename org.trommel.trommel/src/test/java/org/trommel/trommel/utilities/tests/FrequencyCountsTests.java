/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.utilities.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.trommel.trommel.utilities.FrequencyCounts;


//
//	Unit tests for the org.trommel.trommel.reporting.FrequencyCount class
//
public class FrequencyCountsTests 
{
	//
	// Class constants (e.g., strings used in more than one place in the code)
	//
	private static final String FIELD1 = "Field1";
	private static final String FIELD1_VALUE1 = "Value1";
	private static final String FIELD1_VALUE2 = "Value2";
	private static final String FIELD1_VALUE3 = "Value3";
	private static final int FIELD1_VALUE1_COUNT = 13;
	private static final int FIELD1_VALUE2_COUNT = 27;
	private static final int FIELD1_VALUE3_COUNT = 35;

	
	//
	// Tests
	//

	@Test(expected = IllegalArgumentException.class)
	public void testIncrementEmptyValue() {
		FrequencyCounts frequencyCount = new FrequencyCounts();

		frequencyCount.increment(" \t");
	}

	@Test
	public void testSerialize() {
		FrequencyCounts frequencyCount = new FrequencyCounts();
		String line1 = FIELD1 + "\t" + FIELD1_VALUE1 + "\t" + FIELD1_VALUE1_COUNT + "\n";
		String line2 = FIELD1 + "\t" + FIELD1_VALUE2 + "\t" + FIELD1_VALUE2_COUNT + "\n";
		String line3 = FIELD1 + "\t" + FIELD1_VALUE3 + "\t" + FIELD1_VALUE3_COUNT + "\n";

		// Increment for value 1
		for (int i = 0; i < FIELD1_VALUE1_COUNT; ++i) {
			frequencyCount.increment(FIELD1_VALUE1);
		}

		// Increment for value 2
		for (int i = 0; i < FIELD1_VALUE2_COUNT; ++i) {
			frequencyCount.increment(FIELD1_VALUE2);
		}

		// Increment for value 3
		for (int i = 0; i < FIELD1_VALUE3_COUNT; ++i) {
			frequencyCount.increment(FIELD1_VALUE3);
		}

		String serializedString = frequencyCount.serialize(FIELD1);
		
		assertTrue(serializedString.contains(line1));
		assertTrue(serializedString.contains(line2));
		assertTrue(serializedString.contains(line3));
	}
}
