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
