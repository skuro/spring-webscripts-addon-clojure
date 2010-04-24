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

import groovy.lang.GroovyObject;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import groovy.text.TemplateEngine;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.ScriptContent;
import org.springframework.extensions.webscripts.WebScriptException;

/**
 * @author muzquiano
 */
public class GroovyTemplateProcessor extends AbstractTemplateProcessor
{
	private static Log logger = LogFactory.getLog(GroovyTemplateProcessor.class);

	private Map<String, GroovyObject> tags;

	private ClojureScriptProcessor clojureScriptProcessor;
	
    /** Default template input encoding */
    private String defaultEncoding;

    /**
	 * Sets the groovy script processor
	 * 
	 * @param clojureScriptProcessor
	 */
	public void setGroovyScriptProcessor(ClojureScriptProcessor clojureScriptProcessor)
	{
		this.clojureScriptProcessor = clojureScriptProcessor;
	}

    /**
     * @param defaultEncoding
     */
    public void setDefaultEncoding(String defaultEncoding)
    {
        this.defaultEncoding = defaultEncoding;
    }
        
    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.TemplateProcessor#getDefaultEncoding()
     */
    public String getDefaultEncoding()
    {
        return this.defaultEncoding;
    }
    
	/**
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getModel(Object model) 
	{
		Map<String, Object> result = null;
		
		if (model != null && model instanceof Map) 
		{
			result = (Map<String, Object>) model;
		} 
		else 
		{
			result = new HashMap<String, Object>();
		}
		
		addProcessorModelExtensions(result);
		
		return result;
	}    

    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.processor.AbstractTemplateProcessor#init()
     */
    public void init()
    {
        super.init();
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.core.processor.Processor#getExtension()
     */
    public String getExtension()
    {
        return "gsp";
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.core.processor.Processor#getName()
     */
    public String getName()
    {
        return "gsp";
    }
    
    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.TemplateProcessor#process(java.lang.String, java.lang.Object, java.io.Writer)
     */
    public void process(String templatePath, Object model, Writer out)
    {
    	TemplateEngine engine = makeTemplateEngine(out);
    	
    	ScriptContent scriptContent = clojureScriptProcessor.findScript(templatePath);
    	if (scriptContent != null)
    	{
    		Template t = getTemplate(scriptContent.getInputStream(), engine);
    		
    		try
    		{
    			if (!(out instanceof PrintWriter))
    			{
    				out = new PrintWriter(out);
    			}
    			
    			t.make(getModel(model)).writeTo(out);
    			out.flush();
    		}
    		catch(IOException e)
    		{
    			throw new WebScriptException("Unable to commit output", e);
    		}
    	}
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.TemplateProcessor#processString(java.lang.String, java.lang.Object, java.io.Writer)
     */
    public void processString(String templateString, Object model, Writer out)
    {
    	TemplateEngine engine = makeTemplateEngine(out);
    	
		Template t = getTemplate(templateString, engine);
		
		try
		{
			if (!(out instanceof PrintWriter))
			{
				out = new PrintWriter(out);
			}
			
			t.make(getModel(model)).writeTo(out);
			out.flush();
		}
		catch(IOException e)
		{
			throw new WebScriptException("Unable to commit output", e);
		}
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.TemplateProcessor#reset()
     */
    public void reset()
    {
    	this.init();
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.TemplateProcessor#hasTemplate(java.lang.String)
     */
    public boolean hasTemplate(String templatePath)
    {
    	return (this.clojureScriptProcessor.findScript(templatePath) != null);
    }

	private TemplateEngine makeTemplateEngine(Writer out)
	{
		TemplateEngine engine = null;
		
		Writer engineWriter = out;
		if (!(out instanceof PrintWriter))
		{
			engineWriter = new PrintWriter(out);
		}
		
		engine = new SimpleTemplateEngine();

		return engine;
	}

	private Template getTemplate(InputStream is, TemplateEngine engine) 
	{
		Template t = null;
		try {
			try {
				t = engine.createTemplate(new InputStreamReader(is));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return t;
	}

	private Template getTemplate(String template, TemplateEngine engine) 
	{
		Template t = null;
		try {
			t = engine.createTemplate(template);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return t;
	}
}
