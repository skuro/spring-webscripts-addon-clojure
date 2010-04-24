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

import java.util.ArrayList;

import org.springframework.extensions.webscripts.AbstractWebScriptServerTest;
import org.springframework.extensions.webscripts.TestWebScriptServer.GetRequest;

/**
 * Unit tests for Groovy Script Processor
 * 
 * @author muzquiano
 */
public class GroovyScriptProcessorTest extends AbstractWebScriptServerTest
{
	public ArrayList<String> getConfigLocations()
	{
		ArrayList<String> list = super.getConfigLocations();
		
		list.add("classpath:org/springframework/extensions/webscripts/groovy-webscripts-context.xml");
		
		return list;
	}
	    
    /**
     * Groovy1 - Simple Response Test
     * 
     * @throws Exception
     */
    public void testGroovy1() throws Exception
    {
    	sendRequest(new GetRequest("/test/groovy1"), 200, "VALUE: SUCCESS");
    }

    /**
     * Groovy2 - Sudoko Solver Test
     * 
     * @throws Exception
     */
    public void testGroovy2() throws Exception
    {
    	sendRequest(new GetRequest("/test/groovy2"), 200, "284375169639218457571964382152496873348752916796831245967143528813527694425689731");
    }
}
