package org.springframework.extensions.webscripts.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.extensions.webscripts.ScriptProcessor;
import org.springframework.extensions.webscripts.ScriptProcessorFactory;

/**
 * @author Carlo Sciolla &lt;skuro@skuro.tk&gt;
 */
public class ClojureScriptProcessorFactory implements ScriptProcessorFactory, ApplicationContextAware
{
	private static final Log logger = LogFactory.getLog(ClojureScriptProcessorFactory.class);
	
	protected ApplicationContext applicationContext = null;
	
	public void setApplicationContext(ApplicationContext applicationContext)
	{
		this.applicationContext = applicationContext;
	}
		
	/* (non-Javadoc)
	 * @see org.springframework.extensions.webscripts.ScriptProcessorFactory#newInstance()
	 */
	public ScriptProcessor newInstance()
	{
		return new ClojureScriptProcessor();
	}
}
