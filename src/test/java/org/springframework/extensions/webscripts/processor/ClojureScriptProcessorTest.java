package org.springframework.extensions.webscripts.processor;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Carlo Sciolla &lt;c.sciolla@sourcesense.com&gt;
 */
public class ClojureScriptProcessorTest {
	@Test
	public void testExecution () {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("model", new HashMap<String, String> ());

		ClojureScriptProcessor proc = new ClojureScriptProcessor();
		Object res = proc.executeScript("/webscipt/test/clojure1.get.clj", new HashMap());
		assertNotNull ("script returned a null result", res);
		assertTrue ("script returned something else than a map", res instanceof Map);

		Map<String, Object> resModel = (Map<String, Object>)res;
		Object viewModelObj = resModel.get("model");
		assertNotNull ("view model is null", viewModelObj);
		assertTrue ("view model is something else than a map", viewModelObj instanceof Map);

		Map<String, String> viewModel = (Map<String, String>) viewModelObj;
		String success = viewModel.get("clojureVal");

		assertEquals("SUCCESS", success);
	}
}
