/*
 *	TODO - Insert license blurb here
 */
package org.trommel.trommel.scripting.interpreters;

import org.apache.log4j.Logger;
import org.trommel.trommel.scripting.analysis.DepthFirstAdapter;
import org.trommel.trommel.scripting.node.AHdfsFilePath;
import org.trommel.trommel.scripting.node.ALoadDataStatement;
import org.trommel.trommel.scripting.node.ALocalFile;
import org.trommel.trommel.scripting.node.ALocalFilePath;


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
	private String hdfsInputFilePath = null;
	private String hdfsOutputFilePath = "/tmp/Trommel";
	private String localFilePath = null;
	private String localFileName = null;
	
	
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
	 * @return If specified in the TrommelScript, returns the local file system path to use for writing output, 
	 * returns null otherwise. 
	 */
	public String getLocalFilePath()
	{
		return localFilePath;
	}
	
	/**
	 * @return If specified in the TrommelScript, returns the name of the local file to use for writing output, 
	 * returns null otherwise. 
	 */
	public String getLocalFileName()
	{
		return localFileName;
	}
	
	
	//
	//	Constructors
	//
	
	/**
	 * @param logger The {@link org.apache.log4j.Logger} instances that will be used by the MapInterpreter
	 * to log to the Hadoop Task syslog file.
	 * @throws IllegalArgumentException Where logger is null.
	 */
	public FrontEndInterpreter(Logger logger)
		throws IllegalArgumentException
	{
		// Check illegal input
		if (logger == null)
		{
			throw new IllegalArgumentException("Logger cannot be null.");
		}
		
		this.logger = logger;
	}
	
	
	//
	//	Public methods
	//
	
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

	/**
	 * Override of the SableCC-generated method for post-handling the TrommelScript grammar 
	 * "LocalFile" Production. 
	 */
    public void outALocalFile(ALocalFile node)
    {
		logger.debug("FrontEndInterpreter.outALocalFile called.");

		localFileName = node.getQuotedString().toString().replace("'", "").trim();
    }
}
