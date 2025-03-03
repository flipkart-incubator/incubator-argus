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

 package com.xasecure.authentication.unix.jaas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class RemoteUnixLoginModule implements LoginModule {
	
	
	private static final String REMOTE_UNIX_AUTHENICATION_CONFIG_FILE_PARAM = "configFile";

	private static final String DEBUG_PARAM = "debug";
	private static final String REMOTE_LOGIN_HOST_PARAM = "authServiceHostName";
	private static final String REMOTE_LOGIN_AUTH_SERVICE_PORT_PARAM = "authServicePort";
	private static final String SSL_KEYSTORE_PATH_PARAM = "keyStore";
	private static final String SSL_KEYSTORE_PATH_PASSWORD_PARAM = "keyStorePassword";
	private static final String SSL_TRUSTSTORE_PATH_PARAM = "trustStore";
	private static final String SSL_TRUSTSTORE_PATH_PASSWORD_PARAM = "trustStorePassword";
	private static final String SSL_ENABLED_PARAM = "sslEnabled";
	
	private static final String JAAS_ENABLED_PARAM = "remoteLoginEnabled" ;

	private static final String SSL_ALGORITHM = "SSLv3";

	private String userName;
	private char[] password;
	private Subject subject;
	private CallbackHandler callbackHandler;
	private boolean debug = false;

	private String remoteHostName;
	private int remoteHostAuthServicePort;

	private boolean loginSuccessful = false;
	private String loginGroups = null;

	private String keyStorePath;
	private String keyStorePathPassword;
	private String trustStorePath;
	private String trustStorePathPassword;

	private boolean SSLEnabled = false;
	
	private boolean remoteLoginEnabled = true ;

	public RemoteUnixLoginModule() {
		log("Created RemoteUnixLoginModule");
	}

	@Override
	public boolean abort() throws LoginException {
		log("RemoteUnixLoginModule::abort() has been called.");
		loginSuccessful = false;
		return true;
	}

	@Override
	public boolean commit() throws LoginException {
		log("RemoteUnixLoginModule::commit() has been called. -> isLoginSuccess [" + loginSuccessful + "]");
		if (loginSuccessful) {
			if (subject != null) {
				subject.getPrincipals().add(new UnixUserPrincipal(userName.trim()));
				if (loginGroups != null) {
					loginGroups = loginGroups.trim();
					for (String group : loginGroups.split(",")) {
						subject.getPrincipals().add(new UnixGroupPrincipal(group.trim()));
					}
				}
			}
		} else {
			if (subject != null) {
				subject.getPrincipals().clear();
			}
		}
		return loginSuccessful;
	}

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		
		log("RemoteUnixLoginModule::initialize() has been called with callbackhandler: " + this.callbackHandler) ;

		if (this.callbackHandler == null) {
			this.callbackHandler = new ConsolePromptCallbackHandler();
		}

		Properties config = null ;

		String val = (String) options.get(REMOTE_UNIX_AUTHENICATION_CONFIG_FILE_PARAM);
		logError("Remote Unix Auth Configuration file [" + val + "]") ;
		if (val != null) {
			InputStream in = null ;
			try {
				in = getFileInputStream(val) ;
				if (in != null) {
					config = new Properties() ;
					config.load(in);
				}
				
			}
			catch(Throwable t) {
				logError("Unable to load REMOTE_UNIX_AUTHENICATION_CONFIG_FILE_PARAM [" + val + "]") ;
			}
		}
		
		if (config == null) {
			logError("Remote Unix Auth Configuration is being loaded from XML configuration - not Properties") ;
			config = new Properties() ;
			config.putAll(options);
		}

		initParams(config) ;
		
	}
	
	
	
	public void initParams(Properties  options) {
		
		String val = (String) options.get(JAAS_ENABLED_PARAM) ;
		
		if (val != null) {
			remoteLoginEnabled = val.trim().equalsIgnoreCase("true") ;
			if (! remoteLoginEnabled) {
				System.err.println("Skipping RemoteLogin - [" + JAAS_ENABLED_PARAM + "] => [" + val + "]") ;
				return ;
			}
		}
		else {
			remoteLoginEnabled = true ;
		}

		val = (String) options.get(DEBUG_PARAM);
		if (val != null && (!val.equalsIgnoreCase("false"))) {
			debug = true;
		}

		remoteHostName = (String) options.get(REMOTE_LOGIN_HOST_PARAM);
		log("RemoteHostName:" + remoteHostName);

		val = (String) options.get(REMOTE_LOGIN_AUTH_SERVICE_PORT_PARAM);
		if (val != null) {
			remoteHostAuthServicePort = Integer.parseInt(val.trim());
		}
		log("remoteHostAuthServicePort:" + remoteHostAuthServicePort);
		
		
		val = (String)options.get(SSL_ENABLED_PARAM) ;
		SSLEnabled = (val != null) && val.trim().equalsIgnoreCase("true") ;
		log("SSLEnabled:" + SSLEnabled);

		
		if (SSLEnabled) {
			trustStorePath = (String) options.get(SSL_TRUSTSTORE_PATH_PARAM);
			log("trustStorePath:" + trustStorePath);
	
			if (trustStorePath != null) {
				trustStorePathPassword = (String) options.get(SSL_TRUSTSTORE_PATH_PASSWORD_PARAM);
				if (trustStorePathPassword == null) {
					trustStorePathPassword = "";
				}
				log("trustStorePathPassword:" + trustStorePathPassword);
			}
	
			keyStorePath = (String) options.get(SSL_KEYSTORE_PATH_PARAM);
			log("keyStorePath:" + keyStorePath);
			if (keyStorePath != null) {
				keyStorePathPassword = (String) options.get(SSL_KEYSTORE_PATH_PASSWORD_PARAM);
				if (keyStorePathPassword == null) {
					keyStorePathPassword = "";
				}
				log("keyStorePathPassword:" + keyStorePathPassword);
			}
		}


	}

	@Override
	public boolean login() throws LoginException {
		
		if (remoteLoginEnabled && this.callbackHandler != null) {
			NameCallback nameCallback = new NameCallback("UserName:");
			PasswordCallback passwordCallback = new PasswordCallback("Password:", false);
			try {
				callbackHandler.handle(new Callback[] { nameCallback, passwordCallback });
			} catch (IOException e) {
				throw new LoginException("Unable to get username/password due to exception: " + e);
			} catch (UnsupportedCallbackException e) {
				throw new LoginException("Unable to get username/password due to exception: " + e);
			}

			userName = nameCallback.getName();
			
			String modifiedUserName = userName ;
			
			if (userName != null) {
				int atStartsAt = userName.indexOf("@") ;
				if ( atStartsAt > -1) {
					modifiedUserName = userName.substring(0, atStartsAt) ;
				}
			}
			
			password = passwordCallback.getPassword();
			

			log("userName:" + userName);
			log("modified UserName:" + modifiedUserName);
			// log("password:" + new String(password));

			doLogin(modifiedUserName, new String(password));

			loginSuccessful = true;
		}

		return loginSuccessful;
	}

	@Override
	public boolean logout() throws LoginException {
		if (subject != null) {
			subject.getPrincipals().clear();
		}
		return true;
	}

	public void doLogin(String aUserName, String  aPassword) throws LoginException {

		// POSSIBLE values
		// null
		// OK: group1, group2, group3
		// FAILED: Invalid Password

		String ret = getLoginReplyFromAuthService(aUserName, aPassword);

		if (ret == null) {
			throw new LoginException("FAILED: unable to authenticate to AuthenticationService: " + remoteHostName + ":" + remoteHostAuthServicePort);
		} else if (ret.startsWith("OK:")) {
			loginSuccessful = true;
			if (ret.length() > 3) {
				this.loginGroups = ret.substring(3);
			}
		} else if (ret.startsWith("FAILED:")) {
			loginSuccessful = false;
			throw new LoginException("FAILED: unable to authenticate to AuthenticationService: " + remoteHostName + ":" + remoteHostAuthServicePort);
		} else {
			throw new LoginException("FAILED: unable to authenticate to AuthenticationService: " + remoteHostName + ":" + remoteHostAuthServicePort + ", msg:" + ret);
		}
	}

	private String getLoginReplyFromAuthService(String aUserName, String aPassword) throws LoginException {
		String ret = null;

		Socket sslsocket = null;

		String loginString = "LOGIN:" + aUserName + " " + new String(aPassword) + "\n";

		try {
			try {
				if (SSLEnabled) {
					
					SSLContext context = SSLContext.getInstance(SSL_ALGORITHM);
	
					KeyManager[] km = null;
	
					if (keyStorePath != null) {
						KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
	
						InputStream in = null;
	
						in = getFileInputStream(keyStorePath);
	
						try {
							ks.load(in, keyStorePathPassword.toCharArray());
						} finally {
							if (in != null) {
								in.close();
							}
						}
	
						KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
						kmf.init(ks, keyStorePathPassword.toCharArray());
						km = kmf.getKeyManagers();
					}
	
					TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	
					KeyStore trustStoreKeyStore = null;
	
					if (trustStorePath != null) {
						trustStoreKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
	
						InputStream in = null;
	
						in = getFileInputStream(trustStorePath);
	
						try {
							trustStoreKeyStore.load(in, trustStorePathPassword.toCharArray());
						} finally {
							if (in != null) {
								in.close();
							}
						}
					}
	
					trustManagerFactory.init(trustStoreKeyStore);
	
					TrustManager[] tm = trustManagerFactory.getTrustManagers();
	
					SecureRandom random = new SecureRandom();
	
					context.init(km, tm, random);
	
					SSLSocketFactory sf = context.getSocketFactory();

					sslsocket = sf.createSocket(remoteHostName, remoteHostAuthServicePort);
					
				} else {
					sslsocket = new Socket(remoteHostName, remoteHostAuthServicePort);
				}

				OutputStreamWriter writer = new OutputStreamWriter(sslsocket.getOutputStream());

				writer.write(loginString);

				writer.flush();

				BufferedReader reader = new BufferedReader(new InputStreamReader(sslsocket.getInputStream()));

				ret = reader.readLine();

				reader.close();

				writer.close();

			} finally {
				if (sslsocket != null) {
					sslsocket.close();
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
			throw new LoginException("FAILED: unable to authenticate to AuthenticationService: " + remoteHostName + ":" + remoteHostAuthServicePort + ", Exception: " + t);
		} finally {
			log("Login of user String: {" + aUserName + "}, return from AuthServer: {" + ret + "}");
		}

		return ret;
	}

	private InputStream getFileInputStream(String path) throws FileNotFoundException {

		InputStream ret = null;

		File f = new File(path);

		if (f.exists()) {
			ret = new FileInputStream(f);
		} else {
			ret = getClass().getResourceAsStream(path);
			
			if (ret == null) {
				if (! path.startsWith("/")) {
					ret = getClass().getResourceAsStream("/" + path);
				}
			}
			
			if (ret == null) {
				ret = ClassLoader.getSystemClassLoader().getResourceAsStream(path) ;
				if (ret == null) {
					if (! path.startsWith("/")) {
						ret = ClassLoader.getSystemResourceAsStream("/" + path);
					}
				}
			}
		}

		return ret;
	}

	private void log(String msg) {
		if (debug) {
			System.err.println("RemoteUnixLoginModule: " + msg);
		}
	}
	
	private void logError(String msg) {
		System.err.println("RemoteUnixLoginModule: <ERROR> " + msg);
	}

}
