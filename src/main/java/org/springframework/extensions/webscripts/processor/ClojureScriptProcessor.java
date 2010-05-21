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

import clojure.lang.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.core.scripts.ScriptException;
import org.springframework.extensions.webscripts.ScriptContent;

/**
 * @author Carlo Sciolla &lt;carlo.sciolla@gmail.com&gt;
 */
public class ClojureScriptProcessor extends AbstractScriptProcessor
{
    public static final Namespace WEBSCRIPT_NS = Namespace.findOrCreate(Symbol.intern("spring.surf.webscript"));
    public static final Var CURRENT_WEBSCRIPT_NS = Var.intern(WEBSCRIPT_NS, Symbol.create("*wsns*"), WEBSCRIPT_NS);

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
    private Object executeClojureScript(InputStream is, Writer out, Map<String, Object> model)
    {
        log.debug("Executing Clojure script");
        log.debug ("This line is to get rid of an IDEA warning: " + out);

        try
        {
            // make available in/out paramters to the Clojure world
            Associative mappings = PersistentHashMap.EMPTY;
            mappings = mappings.assoc(ClojureScriptProcessor.CURRENT_WEBSCRIPT_NS, ClojureScriptProcessor.WEBSCRIPT_NS);

            for (Map.Entry<String, Object> e : model.entrySet())
            {
                if ("format".equals (e.getKey()) || "atom".equals(e.getKey()))
                {
                    continue;
                }
                
                // TODO: provide a Clojure integration layer
                Symbol sym = Symbol.intern(e.getKey());
                Var var = Var.intern(ClojureScriptProcessor.WEBSCRIPT_NS, sym);
                Object bridgedValue = bridge(e.getValue());
                mappings = mappings.assoc(var, bridgedValue);

                log.debug(String.format("Included in mapping: %s -> %s", var, bridgedValue));
            }

            Var.pushThreadBindings(mappings);

            // here we go
            Object result = clojure.lang.Compiler.load(new InputStreamReader(is));
            if (!(result instanceof IPersistentMap))
            {
                throw new UnsupportedOperationException("Clojure webscript controllers must yield a map");
            }

            // TODO: clojure is not currently directly feeding elements in the view model
            Map<String, Object> viewModel = (Map<String, Object>) model.get("model");
            merge((IPersistentMap) result, viewModel);

            //Var.popThreadBindings();

            return result;
        }
        catch (Exception exception)
        {
            throw new ScriptException("Error executing Clojure script", exception);
        }
    }

    protected void merge(IPersistentMap cljMap, Map<String, Object> viewModel)
    {
        for (Object o : cljMap)
        {
            MapEntry next = (MapEntry) o;
            Keyword key = (Keyword) next.getKey();
            viewModel.put(key.getName(), next.getValue());

            log.debug(String.format("Importing object in viewModel from Clojure: %s -> %s", key.getName(), next.getValue()));
        }
    }

    protected Object bridge(Object obj)
    {
        // TODO: this doesn't work, think of how to map things correctly
        if (obj instanceof Map)
        {
            Map otherMap = (Map) obj;
            ITransientMap ret = PersistentHashMap.EMPTY.asTransient();
            for (Object o : otherMap.entrySet())
            {
                Map.Entry e = (Map.Entry) o;
                ret = ret.assoc(e.getKey(), e.getValue());
            }

            return ret;
        }

        // no special case, let it pass
        return obj;
    }

    /* (non-Javadoc)
    * @see org.springframework.extensions.webscripts.processor.AbstractScriptProcessor#init()
    */

    public void init()
    {
        super.init();

        try
        {
            RT.init();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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
