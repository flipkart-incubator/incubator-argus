/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

 /**************************************************************************
 *                                                                        *
 * The information in this document is proprietary to XASecure Inc.,      *
 * It may not be used, reproduced or disclosed without the written        *
 * approval from the XASecure Inc.,                                       *
 *                                                                        *
 * PRIVILEGED AND CONFIDENTIAL XASECURE PROPRIETARY INFORMATION           *

 /**
  *
  *	@version: 1.0.004
  *
  */

package com.xasecure.pdp.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xasecure.pdp.config.gson.PolicyExclusionStrategy;
import com.xasecure.pdp.model.PolicyContainer;

public class PolicyRefresher  {

	private static final Log LOG = LogFactory.getLog(PolicyRefresher.class);
	
	private String url ;
	private long refreshInterval ;

	private ConfigWatcher watcherDaemon = null;

	protected PolicyContainer policyContainer = null ;

	private PolicyChangeListener policyChangeListener = null ;
	
	private String saveAsFileName = null ;
	
	private String sslConfigFileName = null ;
	
    private String lastStoredFileName = null;
	
	private PolicyExclusionStrategy policyExclusionStrategy = new PolicyExclusionStrategy() ;

	public PolicyRefresher(String url, long refreshInterval, String sslConfigFileName, String lastStoredFileName) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Creating PolicyRefreshser with url: " + url +
					", refreshInterval: " + refreshInterval +
					", sslConfigFileName: " + sslConfigFileName +
					", lastStoredFileName: " + lastStoredFileName);
		}
		this.url = url ;
		this.refreshInterval = refreshInterval ;
		this.sslConfigFileName = sslConfigFileName ;
		this.lastStoredFileName = lastStoredFileName; 
		checkFileWatchDogThread();
	}
	
	public PolicyChangeListener getPolicyChangeListener() {
		return policyChangeListener;
	}

	public synchronized void setPolicyChangeListener(PolicyChangeListener policyChangeListener) {
		this.policyChangeListener = policyChangeListener;
		if (this.policyContainer != null) {
			savePolicyToFile() ;
			savePolicyToCacheFile();
			notifyPolicyChange() ;
		}
	}

	private void setPolicyContainer(PolicyContainer aPolicyContainer) {
		this.policyContainer = aPolicyContainer ;
	}
	
	public PolicyContainer getPolicyContainer() {
		return policyContainer ;
	}
	
	public String getSaveAsFileName() {
		return saveAsFileName;
	}

	public void setSaveAsFileName(String saveAsFileName) {
		this.saveAsFileName = saveAsFileName;
	}
	
	public String getSslConfigFileName() {
		return sslConfigFileName;
	}

	public String getLastStoredFileName() {
		return lastStoredFileName;
	}

	public void setLastStoredFileName(String lastStoredFileName) {
		this.lastStoredFileName = lastStoredFileName;
	}
	
	public void setSslConfigFileName(String sslConfigFileName) {
		this.sslConfigFileName = sslConfigFileName;
	}
	

	private synchronized void checkFileWatchDogThread() {
		if (watcherDaemon == null) {
			try {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Starting WatchDog for the Path [" + url + "] ....");
				}
				watcherDaemon = new ConfigWatcher(url, refreshInterval,sslConfigFileName,this.getLastStoredFileName()) {
					public void doOnChange() {
						PolicyContainer newPolicyContainer = getPolicyContainer() ;
						setPolicyContainer(newPolicyContainer) ;
						savePolicyToFile() ;
						savePolicyToCacheFile();
						notifyPolicyChange(); 
					};
				};
				watcherDaemon.start();
				if (LOG.isDebugEnabled()) {
					LOG.debug("Completed kick-off of FileWatchDog for the Path [" + url + "] interval in millisecond:" + refreshInterval);
				}
			} catch (Throwable t) {
				LOG.error("Unable to start the FileWatchDog for path [" + url + "]", t);
			}
		}
	}
	
	private void notifyPolicyChange() {
		if (policyChangeListener != null) {
			try {
				policyChangeListener.OnPolicyChange(policyContainer);
			}
			catch(Throwable t) {
				LOG.error("Error during notification of policy changes to listener [" + policyChangeListener + "]", t) ;
			}
			finally {
				LOG.debug("Completed notification of policy changes to listener [" + policyChangeListener + "]") ;
			}
		}
	}
	
	
	private void savePolicyToFile() {
		if (watcherDaemon != null && !watcherDaemon.iscacheModfied()) {
			// Do not Save the file if the policy is not modified.
			return;
		}
		LOG.debug("savePolicyToFile() is called with [" + saveAsFileName + "] - START") ;
		String fileName = null;
		if (saveAsFileName != null) {
			String currentDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) ;
			fileName = saveAsFileName + "." + currentDateTime ;
			File saveFile = new File(fileName) ;
			Gson gson = new GsonBuilder().setPrettyPrinting().setExclusionStrategies(policyExclusionStrategy).create() ;
			String policyAsJson = gson.toJson(policyContainer) ;
			PrintWriter writer = null ;
			try {
				writer = new PrintWriter(new FileWriter(saveFile)) ;
				writer.println(policyAsJson) ;
			}
			catch(IOException ioe) {
				LOG.error("Unable to save policy into file: [" + saveFile.getAbsolutePath() + "]", ioe);
			}
			finally {
				if (writer != null) {
					writer.close();
				}
			}
			LOG.debug("savePolicyToFile() is called with [" + fileName + "] - END") ;
		}
	}	
	
	 private void savePolicyToCacheFile() {
		 
		 if (watcherDaemon != null && !watcherDaemon.iscacheModfied()) {
			    // Don't Save the file if the policy is not modified.
				return;
		  }
		 
		 LOG.debug("savePolicyToCacheFile() is called with [" + lastStoredFileName + "] - START") ;
		
   		 if (lastStoredFileName != null) {
   		
			File lastSaveFile = new File(lastStoredFileName) ;
			Gson gson = new GsonBuilder().setPrettyPrinting().setExclusionStrategies(policyExclusionStrategy).create() ;
			String policyAsJson = gson.toJson(policyContainer) ;
			PrintWriter writer = null ;
										
			try {
				writer = new PrintWriter(new FileWriter(lastSaveFile));
				writer.println(policyAsJson);
				
			}
			catch(IOException ioe){
				LOG.error("Unable to save the policy into Last Stored Policy File [" + lastSaveFile.getAbsolutePath() + "]", ioe );
			}
		    finally {
		    	 //make the policy file cache to be 600 permission when it gets created and updated
		    	 lastSaveFile.setReadable(false,false);
		    	 lastSaveFile.setWritable(false,false);
		    	 lastSaveFile.setReadable(true,true);
		    	 lastSaveFile.setWritable(true,true);
		    	 if (writer != null) {
				 writer.close();
		    	}
		    }
			
		  }

		LOG.debug("savePolicyToCacheFile() is called with [" + lastStoredFileName + "] - END") ;

	}	

}
