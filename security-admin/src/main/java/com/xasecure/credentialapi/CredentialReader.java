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

 package com.xasecure.credentialapi;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.alias.CredentialProvider;
import org.apache.hadoop.security.alias.CredentialProviderFactory;
import org.apache.hadoop.security.alias.JavaKeyStoreProvider;

public class CredentialReader {
	
	public static String getDecryptedString(String CrendentialProviderPath,String alias) {
		  String credential=null;
		  try{
			  if(CrendentialProviderPath==null || alias==null){
				  return null;
			  }		  		  
			  char[] pass = null;
			  Configuration conf = new Configuration();
			  String crendentialProviderPrefix=JavaKeyStoreProvider.SCHEME_NAME + "://file";
			  crendentialProviderPrefix=crendentialProviderPrefix.toLowerCase();
			  CrendentialProviderPath=CrendentialProviderPath.trim();
			  alias=alias.trim();
			  if(CrendentialProviderPath.toLowerCase().startsWith(crendentialProviderPrefix)){
				  conf.set(CredentialProviderFactory.CREDENTIAL_PROVIDER_PATH,
						   //UserProvider.SCHEME_NAME + ":///," +
				  CrendentialProviderPath);
			  }else{
				  conf.set(CredentialProviderFactory.CREDENTIAL_PROVIDER_PATH,
						   //UserProvider.SCHEME_NAME + ":///," +
				  JavaKeyStoreProvider.SCHEME_NAME + "://file" + CrendentialProviderPath);			  
			  }	 	  
			  List<CredentialProvider> providers = CredentialProviderFactory.getProviders(conf);
			  List<String> aliasesList=new ArrayList<String>();
			  CredentialProvider.CredentialEntry credEntry=null;
			  for(CredentialProvider provider: providers) {
	              //System.out.println("Credential Provider :" + provider);
				  aliasesList=provider.getAliases();
				  if(aliasesList!=null && aliasesList.contains(alias.toLowerCase())){
					  credEntry=null;
					  credEntry= provider.getCredentialEntry(alias);
					  pass = credEntry.getCredential();
					  if(pass!=null && pass.length>0){
						  credential=String.valueOf(pass);
						  break;
					  }				  
				  }
			  }
		  }catch(Exception ex){
			  ex.printStackTrace();
			  credential=null;
		  }
		  return credential;
	  }
  
  /*
  public static void main(String args[]) throws Exception{
	  String keystoreFile =new String("/tmp/mykey3.jceks");  
	  String password=CredentialReader.getDecryptedString(keystoreFile, "mykey3");
	   System.out.println(password);
  }*/
}