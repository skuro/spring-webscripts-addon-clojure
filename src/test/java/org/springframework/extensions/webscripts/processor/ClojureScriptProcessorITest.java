/**
 * Copyright (c) 2011 Carlo Sciolla
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.springframework.extensions.webscripts.processor;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.extensions.webscripts.AbstractWebScriptServerTest;
import org.springframework.extensions.webscripts.TestWebScriptServer.GetRequest;
import org.springframework.extensions.webscripts.TestWebScriptServer.PostRequest;

/**
 * Unit tests for Clojure Script Processor
 *
 * @author Carlo Sciolla &lt;skuro@skuro.tk&gt;
 */
public class ClojureScriptProcessorITest extends AbstractWebScriptServerTest {
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
    public void testClojure1() throws IOException {
        sendRequest(new GetRequest("/test/clojure1"), 200, "VALUE: SUCCESS");
    }

    /**
     * WithArgs - Getting args from the query string
     */
    public void testWithArgs() throws IOException {
        sendRequest(new GetRequest("/test/withargs/1?b=2"), 200, "VALUE: 3");
    }

    /**
     * Body echo - receives a POST and echoes the body back
     */
    public void testPostEcho() throws IOException {
        sendRequest(new PostRequest("/test/bodypost", "boo", "text/text"), 200, "VALUE: boo");
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
