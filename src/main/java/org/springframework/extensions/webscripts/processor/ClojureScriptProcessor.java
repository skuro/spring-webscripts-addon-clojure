package org.springframework.extensions.webscripts.processor;

import java.io.*;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.core.scripts.ScriptException;
import org.springframework.extensions.webscripts.ScriptContent;

import spring.surf.webscript.WebScript;

/**
 * @author Carlo Sciolla &lt;skuro@skuro.tk&gt;
 */
public class ClojureScriptProcessor extends AbstractScriptProcessor
{
    private static final Log log = LogFactory.getLog(ClojureScriptProcessor.class);

    /* (non-Javadoc)
    * @see org.springframework.extensions.surf.core.processor.Processor#getExtension()
    */

    public String getExtension()
    {
        return "clj";
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.core.processor.Processor#getName()
     */

    public String getName()
    {
        return "clojure";
    }

    /**
     * Executes the Clojure script
     *
     * @param is    the input stream
     * @param out   the writer.  This can be null if no output is required.
     * @param model the context model for the script
     * @return Object   the return result of the executed script
     */
    @SuppressWarnings (value="unchecked")
    protected Object executeClojureScript(InputStream is, Writer out, Map<String, Object> model)
    {
        log.debug("Executing Clojure script");
        log.debug ("This line is to get rid of an IDEA warning: " + out);

		this.addProcessorModelExtensions(model);

		try
        {
            WebScript script = (WebScript)clojure.lang.Compiler.load(new InputStreamReader(is));
			Map<String, Object> cljModel = (Map)script.run(model);

            return cljModel;
        }
        catch (Exception exception)
        {
            throw new ScriptException("Error executing Clojure script", exception);
        }
    }

    /* (non-Javadoc)
    * @see org.springframework.extensions.webscripts.ScriptProcessor#findScript(java.lang.String)
    */

    public ScriptContent findScript(String path)
    {
		// TODO: maybe check path against (ns)?
        return getScriptLoader().getScript(path);
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.ScriptProcessor#executeScript(java.lang.String, java.util.Map)
     */

    public Object executeScript(String path, Map<String, Object> model)
    {
        ScriptContent scriptContent = findScript(path);
        if (scriptContent == null)
        {
            throw new ScriptException("Unable to locate: " + path);
        }

        return executeScript(scriptContent, model);
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.ScriptProcessor#executeScript(org.springframework.extensions.webscripts.ScriptContent, java.util.Map)
     */

    public Object executeScript(ScriptContent scriptContent, Map<String, Object> model)
    {
        return executeClojureScript(scriptContent.getInputStream(), null, model);
    }

    /* (non-Javadoc)
    * @see org.springframework.extensions.webscripts.ScriptProcessor#unwrapValue(java.lang.Object)
    */

    public Object unwrapValue(Object value)
    {
        return value;
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.ScriptProcessor#reset()
     */

    public void reset()
    {
        init();
    }
}
