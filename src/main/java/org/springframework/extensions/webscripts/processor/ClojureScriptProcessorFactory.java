package org.springframework.extensions.webscripts.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.extensions.webscripts.ScriptProcessor;
import org.springframework.extensions.webscripts.ScriptProcessorFactory;

/**
 * Enables Clojure web scripts controller lookup
 *
 * @author Carlo Sciolla &lt;skuro@skuro.tk&gt;
 * @see ClojureScriptProcessor
 */
public class ClojureScriptProcessorFactory implements ScriptProcessorFactory, ApplicationContextAware {
    private static final Log LOGGER = LogFactory.getLog(ClojureScriptProcessorFactory.class);

    protected ApplicationContext applicationContext = null;

    /**
     * {@inheritDoc}
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * {@inheritDoc}
     * @return A new {@link ClojureScriptProcessor} instance
     */
    public ScriptProcessor newInstance() {
        return new ClojureScriptProcessor();
    }
}
