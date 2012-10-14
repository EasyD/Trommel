/*
 * TODO - Insert license blurb here
 */
package org.trommel.trommel.commandline;


/**
 *	Specifies the Trommel execution mode based on the processing of Trommel command line switches.
 */
public enum ExecutionMode 
{
	/**
	 * User has specified the -version or -v switch on the command line. 
	 */
	Version,
	/**
	 * User has specified the -help or -h switch on the command line. 
	 */
	Help,
	/**
	 * User has specified the -check or -c switch on the command line. 
	 */
	Validation,
	/**
	 * Default execution mode when the user does not specify another mode. 
	 */
	ProcessScript,
	/**
	 * User has specified an invalid set of switches/arguments. 
	 */
	ArgumentError
}
