package org.springframework.extensions.webscripts.processor;

import org.junit.Test;
import org.springframework.extensions.webscripts.AbstractWebScriptServerTest;
import org.springframework.extensions.webscripts.TestWebScriptServer.GetRequest;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Unit tests for Clojure Script Processor
 *
 * @author Carlo Sciolla &lt;skuro@skuro.tk&gt;
 */
public class ClojureScriptProcessorIT extends AbstractWebScriptServerTest {
    @Override
    public ArrayList<String> getConfigLocations() {
        ArrayList<String> list = super.getConfigLocations();

        list.add("classpath:org/springframework/extensions/clj/webscripts/clojure-webscripts-context.xml");

        return list;
    }

    /**
     * Clojure1 - Simple Response Test
     *
     * @throws IOException if Bad Things� happen
     */
    @Test
    public void testClojure1() throws IOException {
        sendRequest(new GetRequest("/test/clojure1"), 200, "VALUE: SUCCESS");
    }

    /**
     * WithArgs - Getting args from the query string
     */
    @Test
    public void testWithArgs() throws IOException {
        sendRequest(new GetRequest("/test/withargs/1?b=2"), 200, "VALUE: 3");
    }

//	@Ignore("The current spring web script test classes don't pass an output stream -> NPE")
//	public void testWithOutput() throws IOException {
//		GetRequest req = new GetRequest("/test/without");
//		TestWebScriptServer.Response res = sendRequest(req, 200, "VALUE: SUCCESS");
//		byte[] content = res.getContentAsByteArray();
//		assertNotNull("failed to retrieve content", content);
//		assertEquals("streamed content", new String(content, "UTF-8"));
//	}
}
