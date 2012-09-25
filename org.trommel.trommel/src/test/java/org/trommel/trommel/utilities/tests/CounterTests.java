/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.utilities.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.trommel.trommel.utilities.Counter;

//
//	Unit tests for the org.trommel.trommel.reporting.Counter class
//
public class CounterTests 
{
	//
	//	Tests
	//
	
	@Test
	public void testGetCountAndIncrement()
	{
		Counter counter = new Counter();
		
		counter.increment();
		counter.increment();
		counter.increment();
		counter.increment();
		
		assertEquals(4, counter.getCount());
	}

	@Test
	public void testToString()
	{
		Counter counter = new Counter();
		
		counter.increment();
		counter.increment();
		counter.increment();
		counter.increment();
		counter.increment();
		
		assertEquals("5", counter.toString());
	}
}
