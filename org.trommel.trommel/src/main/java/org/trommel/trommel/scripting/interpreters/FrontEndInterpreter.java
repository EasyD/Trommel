/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.scripting.interpreters;

import org.apache.log4j.Logger;
import org.trommel.trommel.scripting.analysis.DepthFirstAdapter;
import org.trommel.trommel.scripting.node.AHdfsFilePath;
import org.trommel.trommel.scripting.node.ALoadDataStatement;
import org.trommel.trommel.scripting.node.ALocalFilePath;
import org.trommel.trommel.scripting.node.ASampleDataStatement;
import org.trommel.trommel.utilities.StringUtilities;


/**
 *	The SableCC Visitor for interpreting the TrommelScript AST for the front end phase of processing (i.e., 
 *	the main method of the {@link org.trommel.trommel.mapreduce.TrommelDriver} class). The primary
 *	responsibility of the MapInterpreter is interpreting the TrommelScript in the context of setting up
 *	Hadoop MapReduce for Trommel processing.
 */
public class FrontEndInterpreter extends DepthFirstAdapter
{
	//
	//	Private members
	//

	private Logger logger = null;
	private boolean samplingData = false;
	private String hdfsInputFilePath = null;
	private String hdfsOutputFilePath = null;
	private String localFilePath = null;
	
	
	//
	//	Getters/setters
	//
	
	/**
	 * @return The HDFS input file path as specified in the LOAD DATA statement in the TrommelScript.
	 */
	public String getHdfsInputFilePath()
	{
		return hdfsInputFilePath;
	}
	
	/**
	 * @return The HDFS output file path specified in the TrommelScript, returns a default of 
	 * "/tmp/Trommel" otherwise.
	 */
	public String getHdfsOutputFilePath()
	{
		return hdfsOutputFilePath;
	}
	
	/**
	 * @return If specified in the TrommelScript, returns the local file system path (including file name) to use for writing output, 
	 * returns null otherwise. 
	 */
	public String getLocalFilePath()
	{
		return localFilePath;
	}
	
	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instances that will be used by the MapInterpreter
	 * to log to the Hadoop Task syslog file.
	 * @param defaultHdfsFilePath The HDFS file path to use by default for writing Trommel output (i.e., the user has 
	 * specified only export to the local file system in TrommelScript).
	 * @throws IllegalArgumentException Where logger is null. Also thrown in the case defaultHdfsFilePath is null or empty.
	 * All-whitespace strings are considered empty.
	 */
	public FrontEndInterpreter(Logger logger, String defaultHdfsFilePath)
		throws IllegalArgumentException
	{
		// Check illegal input
		if (logger == null)
		{
			throw new IllegalArgumentException("Logger cannot be null.");
		}
		
		if (StringUtilities.isNullOrEmpty(defaultHdfsFilePath))
		{
			throw new IllegalArgumentException("DefaultHdfsFilePath cannot be null or empty.");
		}
		
		this.logger = logger;
		hdfsOutputFilePath = defaultHdfsFilePath;
	}
	
	
	//
	//	Public methods
	//
	
	/**
	 * @return Whether the TrommelScript specifies randomly sampling data.
	 */
	public boolean samplingData()
	{
		return samplingData;
	}

	/**
	 * @return Whether the TrommelScript specifies writing to the local file system.
	 */
	public boolean writeToLocalFileSystem()
	{
		return localFilePath != null;
	}
	
	
    //
    // LOAD DATA statement methods
    //
	
	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "LoadDataStatement" Production. 
	 */
	@Override
    public void outALoadDataStatement(ALoadDataStatement node)
    {
		logger.debug("FrontEndInterpreter.outALoadDataStatement called.");

		hdfsInputFilePath = node.getLoadedFile().toString().replace("'", "").trim();
    }


    //
    // SAMPLE DATA statement methods
    //
	
	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "SampleDataStatement" Production. 
	 */
    public void outASampleDataStatement(ASampleDataStatement node)
    {
    	logger.debug("FrontEndInterpreter.outASampleDataStatement called.");
    	
        samplingData = true;
        
        hdfsInputFilePath = node.getSampledFile().toString().replace("'", "").trim();
    }


    //
	//	STORAGE Production methods
	//
	
	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "HdfsFilePath" Production. 
	 */
    public void outAHdfsFilePath(AHdfsFilePath node)
    {
		logger.debug("FrontEndInterpreter.outAHdfsFilePath called.");

		hdfsOutputFilePath = node.getQuotedString().toString().replace("'", "").trim();
    }

	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "LocalFilePath" Production. 
	 */
    public void outALocalFilePath(ALocalFilePath node)
    {
		logger.debug("FrontEndInterpreter.outALocalFilePath called.");

		localFilePath = node.getQuotedString().toString().replace("'", "").trim();
    }
}
