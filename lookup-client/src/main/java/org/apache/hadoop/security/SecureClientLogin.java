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
 *	@author:  Selvamohan Neethiraj
 *	@version: 1.0.004
 *
 */

package org.apache.hadoop.security;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.apache.hadoop.security.UserGroupInformation.AuthenticationMethod;
import org.apache.hadoop.security.authentication.util.KerberosUtil;

public class SecureClientLogin {

	public synchronized static Subject loginUserFromKeytab(String user, String path) throws IOException {
		try {
			Subject subject = new Subject();
			SecureClientLoginConfiguration loginConf = new SecureClientLoginConfiguration(true, user, path);
			LoginContext login = new LoginContext("hadoop-keytab-kerberos", subject, null, loginConf);
			subject.getPrincipals().add(new User(user, AuthenticationMethod.KERBEROS, login));
			login.login();
			return login.getSubject();
		} catch (LoginException le) {
			throw new IOException("Login failure for " + user + " from keytab " + path, le);
		}
	}

	public synchronized static Subject loginUserWithPassword(String user, String password) throws IOException {
		String tmpPass = password;
		try {
			Subject subject = new Subject();
			SecureClientLoginConfiguration loginConf = new SecureClientLoginConfiguration(false, user, password);
			LoginContext login = new LoginContext("hadoop-keytab-kerberos", subject, null, loginConf);
			subject.getPrincipals().add(new User(user, AuthenticationMethod.KERBEROS, login));
			login.login();
			return login.getSubject();
		} catch (LoginException le) {
			throw new IOException("Login failure for " + user + " using password " + tmpPass.replaceAll(".","*"), le);
		}
	}

	public synchronized static Subject login(String user) throws IOException {
		Subject subject = new Subject();
		subject.getPrincipals().add(new User(user));
		return subject;
	}

	public static Set<Principal> getUserPrincipals(Subject aSubject) {
		if (aSubject != null) {
			Set<User> list = aSubject.getPrincipals(User.class);
			if (list != null) {
				Set<Principal> ret = new HashSet<Principal>();
				for (User a : list) {
					ret.add(a);
				}
				return ret;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public static Principal createUserPrincipal(String aLoginName) {
		return new User(aLoginName) ;
	}

}

class SecureClientLoginConfiguration extends javax.security.auth.login.Configuration {

	private Map<String, String> kerberosOptions = new HashMap<String, String>();
	private boolean usePassword = false ;

	public SecureClientLoginConfiguration(boolean useKeyTab, String principal, String credential) {
		kerberosOptions.put("principal", principal);
		kerberosOptions.put("debug", "false");
		if (useKeyTab) {
			kerberosOptions.put("useKeyTab", "true");
			kerberosOptions.put("keyTab", credential);
			kerberosOptions.put("doNotPrompt", "true");
		} else {
			usePassword = true ;
			kerberosOptions.put("useKeyTab", "false");
			kerberosOptions.put(KrbPasswordSaverLoginModule.USERNAME_PARAM, principal);
			kerberosOptions.put(KrbPasswordSaverLoginModule.PASSWORD_PARAM, credential);
			kerberosOptions.put("doNotPrompt", "false");
			kerberosOptions.put("useFirstPass", "true");
			kerberosOptions.put("tryFirstPass","false") ;
		}
		kerberosOptions.put("storeKey", "true");
		kerberosOptions.put("refreshKrb5Config", "true");
	}

	@Override
	public AppConfigurationEntry[] getAppConfigurationEntry(String appName) {
		AppConfigurationEntry KEYTAB_KERBEROS_LOGIN = new AppConfigurationEntry(KerberosUtil.getKrb5LoginModuleName(), LoginModuleControlFlag.REQUIRED, kerberosOptions);
		if (usePassword) {
			AppConfigurationEntry KERBEROS_PWD_SAVER = new AppConfigurationEntry(KrbPasswordSaverLoginModule.class.getName(), LoginModuleControlFlag.REQUIRED, kerberosOptions);
			return new AppConfigurationEntry[] { KERBEROS_PWD_SAVER, KEYTAB_KERBEROS_LOGIN };
		}
		else {
			return new AppConfigurationEntry[] { KEYTAB_KERBEROS_LOGIN };
		}
	}
	

}
