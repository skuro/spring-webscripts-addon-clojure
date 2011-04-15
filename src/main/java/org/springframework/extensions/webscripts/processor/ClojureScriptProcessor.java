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

import java.io.*;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.core.scripts.ScriptException;
import org.springframework.extensions.webscripts.ScriptContent;

import spring.surf.webscript.WebScript;

/**
 * @author Carlo Sciolla &lt;carlo.sciolla@gmail.com&gt;
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


		try
        {
            WebScript script = (WebScript)clojure.lang.Compiler.load(new InputStreamReader(is));
			Map<String, Object> cljModel = (Map)script.eval(model);
			setClojureViewModel(cljModel, model);

            return cljModel;
        }
        catch (Exception exception)
        {
            throw new ScriptException("Error executing Clojure script", exception);
        }
    }

	/**
	 * Adds the resulting model from Clojure to the view model
	 * @param cljModel View model resulting from Clojure execution
	 * @param model Full web script model
	 */
	private void setClojureViewModel(Map<String, Object> cljModel, Map<String, Object> model)
	{
		// TODO: verify that nothing more than updating the 'model' entry is needed here
		Map<String, Object> viewModel = (Map<String, Object>)model.get("model");
		viewModel.putAll(cljModel);
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
