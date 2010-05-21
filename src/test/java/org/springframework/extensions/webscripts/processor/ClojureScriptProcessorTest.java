package org.springframework.extensions.webscripts.processor;

import clojure.lang.ITransientMap;
import org.springframework.extensions.webscripts.AbstractWebScriptServerTest;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Carlo Sciolla &lt;c.sciolla@sourcesense.com&gt;
 */
public class ClojureScriptProcessorTest extends AbstractWebScriptServerTest
{
    protected ClojureScriptProcessor proc = null;

    @Override
    public void setUp ()
    {
        try
        {
            super.setUp();
        }
        catch (ServletException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        proc = new ClojureScriptProcessor();
    }

    @Override
	public ArrayList<String> getConfigLocations()
	{
		ArrayList<String> list = super.getConfigLocations();

		list.add("classpath:org/springframework/extensions/webscripts/clojure-webscripts-context.xml");

		return list;
	}

    public void testBridgeValue ()
    {
        assertNotNull (proc);

        Map<String, String> aMap = new HashMap<String, String>();
        aMap.put ("key", "value");

        Object result = proc.bridge(aMap);

        assertTrue (result instanceof ITransientMap);

        ITransientMap cljMap = (ITransientMap) result;
        System.out.println (cljMap.count());
        assertEquals ("value", cljMap.valAt("key"));
    }
}
