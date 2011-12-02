package org.springframework.extensions.webscripts.processor;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.core.scripts.ScriptException;
import org.springframework.extensions.webscripts.ScriptContent;

import spring.surf.webscript.WebScript;

/**
 * @author Carlo Sciolla &lt;skuro@skuro.tk&gt;
 */
public class ClojureScriptProcessor extends AbstractScriptProcessor {
    private static final Log log = LogFactory.getLog(ClojureScriptProcessor.class);

    private Map<String, WebScript> compiledWebScripts =
            Collections.synchronizedMap(new HashMap<String,WebScript>());

    /* (non-Javadoc)
    * @see org.springframework.extensions.surf.core.processor.Processor#getExtension()
    */

    public String getExtension() {
        return "clj";
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.core.processor.Processor#getName()
     */

    public String getName() {
        return "clojure";
    }

    /**
     * Executes the Clojure script
     *
     * @param is    the input stream
     * @param out   the writer.  This can be null if no output is required.
     * @param model the context model for the script
     * @return WebScript  a new instance of the requested Clojure backed web script
     */
    @SuppressWarnings(value = "unchecked")
    protected WebScript compileClojureScript(InputStream is, Writer out, Map<String, Object> model) {
        log.debug("Executing Clojure script");
        log.debug("This line is to get rid of an IDEA warning: " + out);

        this.addProcessorModelExtensions(model);

        try {
            return (WebScript) clojure.lang.Compiler.load(new InputStreamReader(is));
        } catch (Exception exception) {
            throw new ScriptException("Error executing Clojure script", exception);
        }
    }

    /* (non-Javadoc)
    * @see org.springframework.extensions.webscripts.ScriptProcessor#findScript(java.lang.String)
    */

    public ScriptContent findScript(String path) {
        // TODO: maybe check path against (ns)?
        return getScriptLoader().getScript(path);
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.ScriptProcessor#executeScript(java.lang.String, java.util.Map)
     */
    public Object executeScript(String path, Map<String, Object> model) {
        ScriptContent scriptContent = findScript(path);
        if (scriptContent == null) {
            throw new ScriptException("Unable to locate: " + path);
        }

        return executeScript(scriptContent, model);
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.ScriptProcessor#executeScript(org.springframework.extensions.webscripts.ScriptContent, java.util.Map)
     */

    public Object executeScript(ScriptContent scriptContent, Map<String, Object> model) {
        String path  = scriptContent.getPath();
        WebScript webscript = this.compiledWebScripts.get(path);
        if (webscript == null) {
            if (log.isDebugEnabled()) {
                log.debug("Compiling new Clojure webscript at path " + path);
            }
            webscript = compileClojureScript(scriptContent.getInputStream(), null, model);
        }
        if (webscript == null) {
            throw new ScriptException("Cannot compile Clojure web script at path " + path);
        }

        synchronized (this) {
            if (log.isDebugEnabled()) {
                log.debug("Caching Clojure webscript at path " + path);
            }
            this.compiledWebScripts.put(path, webscript);
        }

        return webscript.run(scriptContent.getInputStream(), null, model);
    }

    /* (non-Javadoc)
    * @see org.springframework.extensions.webscripts.ScriptProcessor#unwrapValue(java.lang.Object)
    */

    public Object unwrapValue(Object value) {
        return value;
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.ScriptProcessor#reset()
     */

    public void reset() {
        init();
        this.compiledWebScripts = Collections.synchronizedMap(new HashMap<String,WebScript>());
    }
}
