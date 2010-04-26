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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.extensions.webscripts.ScriptProcessor;
import org.springframework.extensions.webscripts.ScriptProcessorFactory;

/**
 * @author muzquiano
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
