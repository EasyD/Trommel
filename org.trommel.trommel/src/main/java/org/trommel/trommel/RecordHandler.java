/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel;

import org.apache.log4j.Logger;


/**
 * Common base class for all Trommel RecordHandlers
 */
public abstract class RecordHandler 
{
	//
	//	Protected members
	//
	protected Logger logger = null;

	
	//
	//	Constructors
	//

	/**
	 * @param logger The {@link org.apache.log4j.Logger} instance that will be used by the RecordHandler
	 * to log to the Hadoop Task syslog file.
	 * @throws IllegalArgumentException Where logger is null.
	 */
	public RecordHandler(Logger logger)
		throws IllegalArgumentException
	{
		// Check for illegal input
		if (logger == null)
		{
			throw new IllegalArgumentException("Logger cannot be null.");
		}
		
		this.logger = logger;
	}
}
