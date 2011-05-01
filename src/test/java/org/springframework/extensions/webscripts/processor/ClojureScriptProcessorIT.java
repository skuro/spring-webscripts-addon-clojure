package org.springframework.extensions.webscripts.processor;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
import org.springframework.extensions.webscripts.AbstractWebScriptServerTest;
import org.springframework.extensions.webscripts.TestWebScriptServer.GetRequest;

/**
 * Unit tests for Clojure Script Processor
 * 
 * @author Carlo Sciolla &lt;skuro@skuro.tk&gt;
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
