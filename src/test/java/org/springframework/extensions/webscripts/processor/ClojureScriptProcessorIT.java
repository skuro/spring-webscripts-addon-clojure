/**
 * Copyright (C) 2005-2009 Alfresco Software Limited.
 *
 * This file is part of the Spring Surf Extension project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.extensions.webscripts.processor;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
import org.springframework.extensions.webscripts.AbstractWebScriptServerTest;
import org.springframework.extensions.webscripts.TestWebScriptServer.GetRequest;

/**
 * Unit tests for Groovy Script Processor
 * 
 * @author muzquiano
 */
public class ClojureScriptProcessorIT extends AbstractWebScriptServerTest
{
    @Override
	public ArrayList<String> getConfigLocations()
	{
		ArrayList<String> list = super.getConfigLocations();
		
		list.add("classpath:org/springframework/extensions/webscripts/clojure-webscripts-context.xml");
		
		return list;
	}
	    
    /**
     * Clojure1 - Simple Response Test
     * 
     * @throws IOException if Bad Thingsª happen
     */
    public void testClojure1() throws IOException
    {
    	sendRequest(new GetRequest("/test/clojure1"), 200, "VALUE: SUCCESS");
    }

	/**
	 * WithArgs - Getting args from the query string
	 */
	public void testWithArgs() throws IOException
	{
		sendRequest(new GetRequest("/test/withargs/1?b=2"), 200, "VALUE: 3");
	}
}
