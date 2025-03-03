/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xasecure.audit.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xasecure.authorization.hadoop.utils.XaSecureCredentialProvider;


/*
 * TODO:
 * 1) Flag to enable/disable audit logging
 * 2) Failed path to be recorded
 * 3) Repo name, repo type from configuration
 */

public class AuditProviderFactory {

	private static final Log LOG = LogFactory.getLog(AuditProviderFactory.class);

	private static final String AUDIT_JPA_CONFIG_PROP_PREFIX        = "xasecure.audit.jpa.";
	private static final String AUDIT_IS_ENABLED_PROP               = "xasecure.audit.is.enabled" ;
	private static final String AUDIT_LOG4J_IS_ENABLED_PROP         = "xasecure.audit.log4j.is.enabled" ;
	private static final String AUDIT_LOG4J_IS_ASYNC_PROP           = "xasecure.audit.log4j.is.async" ;
	private static final String AUDIT_LOG4J_MAX_QUEUE_SIZE_PROP     = "xasecure.audit.log4j.async.max.queue.size" ;
	private static final String AUDIT_LOG4J_MAX_FLUSH_INTERVAL_PROP = "xasecure.audit.log4j.async.max.flush.interval.ms";
	private static final String AUDIT_DB_IS_ENABLED_PROP            = "xasecure.audit.db.is.enabled" ;
	private static final String AUDIT_DB_IS_ASYNC_PROP              = "xasecure.audit.db.is.async" ;
	private static final String AUDIT_DB_MAX_QUEUE_SIZE_PROP        = "xasecure.audit.db.async.max.queue.size" ;
	private static final String AUDIT_DB_MAX_FLUSH_INTERVAL_PROP    = "xasecure.audit.db.async.max.flush.interval.ms";
	private static final String AUDIT_DB_RESUME_QUEUE_SIZE__PROP    = "xasecure.audit.db.async.resume.queue.size" ;
	private static final String AUDIT_DB_BATCH_SIZE_PROP            = "xasecure.audit.db.batch.size" ;
	private static final String AUDIT_DB_CREDENTIAL_PROVIDER_FILE   = "xasecure.audit.credential.provider.file";
	private static final String AUDIT_DB_CREDENTIAL_PROVIDER_ALIAS	= "auditDBCred";
	private static final String AUDIT_JPA_JDBC_PASSWORD  			= "javax.persistence.jdbc.password";

	private static AuditProviderFactory sFactory;

	private AuditProvider mProvider = null;

	private AuditProviderFactory() {
		LOG.info("AuditProviderFactory: creating..");

		mProvider = getDefaultProvider();
	}

	public static AuditProviderFactory getInstance() {
		if(sFactory == null) {
			synchronized(AuditProviderFactory.class) {
				if(sFactory == null) {
					sFactory = new AuditProviderFactory();
				}
			}
		}

		return sFactory;
	}

	public static AuditProvider getAuditProvider() {
		return AuditProviderFactory.getInstance().getProvider();
	}
	
	public AuditProvider getProvider() {
		return mProvider;
	}

	public void init(Properties props) {
		LOG.info("AuditProviderFactory: initializing..");
		
		boolean isEnabled             = getBooleanProperty(props, AUDIT_IS_ENABLED_PROP, false);
		boolean isAuditToLog4jEnabled = getBooleanProperty(props, AUDIT_LOG4J_IS_ENABLED_PROP, false);
		boolean isAuditToLog4jAsync   = getBooleanProperty(props, AUDIT_LOG4J_IS_ASYNC_PROP, false);
		boolean isAuditToDbEnabled    = getBooleanProperty(props, AUDIT_DB_IS_ENABLED_PROP, false);
		boolean isAuditToDbAsync      = getBooleanProperty(props, AUDIT_DB_IS_ASYNC_PROP, false);
		
		List<AuditProvider> providers = new ArrayList<AuditProvider>();


		if(!isEnabled || (!isAuditToDbEnabled && !isAuditToLog4jEnabled)) {
			LOG.info("AuditProviderFactory: Audit not enabled..");
			
			mProvider = getDefaultProvider();

			return;
		}
		
		if(isAuditToDbEnabled) {
			
						
			Map<String, String> jpaInitProperties = getJpaProperties(props);
	
			LOG.info("AuditProviderFactory: found " + jpaInitProperties.size() + " Audit JPA properties");
	
			int dbBatchSize = getIntProperty(props, AUDIT_DB_BATCH_SIZE_PROP, 1000);
			
			if(! isAuditToDbAsync) {
				dbBatchSize = 1; // Batching not supported in sync mode; need to address multiple threads making audit calls
			}

			DbAuditProvider dbProvider = new DbAuditProvider(jpaInitProperties, dbBatchSize);
			
			if(isAuditToDbAsync) {
				AsyncAuditProvider asyncProvider = new AsyncAuditProvider();

				int maxQueueSize     = getIntProperty(props, AUDIT_DB_MAX_QUEUE_SIZE_PROP, -1);
				int maxFlushInterval = getIntProperty(props, AUDIT_DB_MAX_FLUSH_INTERVAL_PROP, -1);
				int resumeQueueSize  = getIntProperty(props, AUDIT_DB_RESUME_QUEUE_SIZE__PROP, 0);


				asyncProvider.setMaxQueueSize(maxQueueSize);
				asyncProvider.setMaxFlushInterval(maxFlushInterval);
				asyncProvider.setResumeQueueSize(resumeQueueSize);
				
				asyncProvider.addAuditProvider(dbProvider);
				
				providers.add(asyncProvider);
			} else {
				providers.add(dbProvider);
			}
		}

		if(isAuditToLog4jEnabled) {
			Log4jAuditProvider log4jProvider = new Log4jAuditProvider();
			
			if(isAuditToLog4jAsync) {
				AsyncAuditProvider asyncProvider = new AsyncAuditProvider();

				int maxQueueSize     = getIntProperty(props, AUDIT_LOG4J_MAX_QUEUE_SIZE_PROP, -1);
				int maxFlushInterval = getIntProperty(props, AUDIT_LOG4J_MAX_FLUSH_INTERVAL_PROP, -1);

				asyncProvider.setMaxQueueSize(maxQueueSize);
				asyncProvider.setMaxFlushInterval(maxFlushInterval);
				
				asyncProvider.addAuditProvider(log4jProvider);
				
				providers.add(asyncProvider);
			} else {
				providers.add(log4jProvider);
			}
		}

		if(providers.size() == 0) {
			mProvider = getDefaultProvider();
		} else if(providers.size() == 1) {
			mProvider = providers.get(0);
		} else {
			MultiDestAuditProvider multiDestProvider = new MultiDestAuditProvider();
			
			multiDestProvider.addAuditProviders(providers);
			
			mProvider = multiDestProvider;
		}
		
		mProvider.start();

		JVMShutdownHook jvmShutdownHook = new JVMShutdownHook(mProvider);

	    Runtime.getRuntime().addShutdownHook(jvmShutdownHook);
	}
	
	private Map<String, String> getJpaProperties(Properties props) {
		Map<String, String> jpaInitProperties = new HashMap<String, String>();
		
		for(String key : props.stringPropertyNames()) {
			if(key == null) {
				continue;
			}
			
			String val = props.getProperty(key);
			
			if(key.startsWith(AuditProviderFactory.AUDIT_JPA_CONFIG_PROP_PREFIX)) {
				key = key.substring(AuditProviderFactory.AUDIT_JPA_CONFIG_PROP_PREFIX.length());

				if(key == null) {
					continue;
				}
				
				jpaInitProperties.put(key, val);
			}
		}

		String jdbcPassword = getCredentialString(getStringProperty(props,AUDIT_DB_CREDENTIAL_PROVIDER_FILE), AUDIT_DB_CREDENTIAL_PROVIDER_ALIAS);

		if(jdbcPassword != null && !jdbcPassword.isEmpty()) {
			jpaInitProperties.put(AUDIT_JPA_JDBC_PASSWORD, jdbcPassword);
		}

            
		return jpaInitProperties;
	}
	
	private boolean getBooleanProperty(Properties props, String propName, boolean defValue) {
		boolean ret = defValue;

		if(props != null && propName != null) {
			String val = props.getProperty(propName);
			
			if(val != null) {
				ret = Boolean.valueOf(val);
			}
		}
		
		return ret;
	}
	
	private int getIntProperty(Properties props, String propName, int defValue) {
		int ret = defValue;

		if(props != null && propName != null) {
			String val = props.getProperty(propName);
			
			if(val != null) {
				ret = Integer.parseInt(val);
			}
		}
		
		return ret;
	}
	
	
	private String getStringProperty(Properties props, String propName) {
	
		String ret = null;
		if(props != null && propName != null) {
			String val = props.getProperty(propName);
			if ( val != null){
				ret = val;
			}
			
		}
		
		return ret;
	}
	
	private AuditProvider getDefaultProvider() {
		return new DummyAuditProvider();
	}

	private static class JVMShutdownHook extends Thread {
		AuditProvider mProvider;

		public JVMShutdownHook(AuditProvider provider) {
			mProvider = provider;
		}

		public void run() {
			mProvider.waitToComplete();
			mProvider.stop();
	    }
	  }

	
	private String getCredentialString(String url,String alias) {
		String ret = null;

		if(url != null && alias != null) {
			char[] cred = XaSecureCredentialProvider.getInstance().getCredentialString(url,alias);

			if ( cred != null ) {
				ret = new String(cred);	
			}
		}
		
		return ret;
	}
}
