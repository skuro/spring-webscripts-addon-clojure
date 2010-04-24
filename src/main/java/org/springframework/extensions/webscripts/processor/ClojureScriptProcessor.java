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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.core.scripts.ScriptException;
import org.springframework.extensions.webscripts.ScriptContent;

/**
 * @author Carlo Sciolla &lt;carlo.sciolla@gmail.com&gt;
 */
public class ClojureScriptProcessor extends AbstractScriptProcessor
{
	private static final Log logger = LogFactory.getLog(ClojureScriptProcessor.class);
	    
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
     * @param is        the input stream
     * @param out       the writer.  This can be null if no output is required.
     * @param model     the context model for the script
     * @return Object   the return result of the executed script
     */
    private Object executeClojureScript(InputStream is, Writer out, Map<String, Object> model)
    {
        try
        {
//			GroovyShell shell = new GroovyShell();
//			Script script = shell.parse(is);
//
//			this.addProcessorModelExtensions(model);
//
//			Binding binding = new Binding(model);
//			for(String name : processorExtensions.keySet())
//			{
//				binding.setProperty(name, processorExtensions.get(name));
//			}
//			binding.setProperty("out", out);
//			script.setBinding(binding);
            return clojure.lang.Compiler.load(new InputStreamReader(is));
        }
        catch (Exception exception)
        {
            throw new ScriptException("Error executing groovy script", exception);
        }
    }
    

//    public Object executeClojureString(String script, Map<String, Object> model)
//    {
//        return executeClojureScript(new ByteArrayInputStream(script.getBytes()), null, model);
//    }
    
    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.processor.AbstractScriptProcessor#init()
     */
    public void init()
    {
        super.init();
    }
    
    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.ScriptProcessor#findScript(java.lang.String)
     */
    public ScriptContent findScript(String path)
    {
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
