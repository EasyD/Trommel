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
package org.trommel.trommel.utilities;


/**
 *	Utility class hosting a collection of handy methods for working/validating string objects.
 */
public final class StringUtilities 
{	
	//
	// Public methods
	//
	
	/**
	 * @param string The object to evaluate.
	 * @return True if string is null or empty. Strings consisting only of whitespace are treated as empty.
	 */
	public static boolean isNullOrEmpty(String string)
	{
		return string == null || string.trim().length() == 0;
	}
}
