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
package org.trommel.trommel;


/**
 * Specifies an instance of the {@link Field} class as a categorical or numeric variable.
 */
public enum FieldType 
{
	/**
	 * The Field instance is a categorical (i.e., string) value. 
	 */
	categorical,
	/**
	 * The Field instance is a numeric value.
	 */
	numeric
}
