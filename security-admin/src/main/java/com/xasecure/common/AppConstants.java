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

 package com.xasecure.common;

import java.util.*;
import com.xasecure.common.*;

public class AppConstants extends XACommonEnums {

	/***************************************************************
	 * Enum values for AssetType
	 **************************************************************/
	/**
	 * ASSET_UNKNOWN is an element of enum AssetType. Its value is "ASSET_UNKNOWN".
	 */
	public static final int ASSET_UNKNOWN = 0;
	/**
	 * ASSET_HDFS is an element of enum AssetType. Its value is "ASSET_HDFS".
	 */
	public static final int ASSET_HDFS = 1;
	/**
	 * ASSET_HBASE is an element of enum AssetType. Its value is "ASSET_HBASE".
	 */
	public static final int ASSET_HBASE = 2;
	/**
	 * ASSET_HIVE is an element of enum AssetType. Its value is "ASSET_HIVE".
	 */
	public static final int ASSET_HIVE = 3;
	
	/**
	 * enum XAAGENT is reserved for internal use
	 */
	public static final int XAAGENT  = 4 ;
	/**
	 * ASSET_KNOX is an element of enum AssetType. Its value is "ASSET_KNOX".
	 */
	public static final int ASSET_KNOX = 5;
	/**
	 * ASSET_STORM is an element of enum AssetType. Its value is "ASSET_STORM".
	 */
	public static final int ASSET_STORM = 6;

	/**
	 * Max value for enum AssetType_MAX
	 */
	public static final int AssetType_MAX = 6;
	
	/***************************************************************
	 * Enum values for PolicyType
	 **************************************************************/
	/**
	 * POLICY_INCLUSION is an element of enum PolicyType. Its value is "POLICY_INCLUSION".
	 */
	public static final int POLICY_INCLUSION = 0;
	/**
	 * POLICY_EXCLUSION is an element of enum PolicyType. Its value is "POLICY_EXCLUSION".
	 */
	public static final int POLICY_EXCLUSION = 1;
	
	/***************************************************************
	 * Enum values for XAAuditType
	 **************************************************************/
	/**
	 * XA_AUDIT_TYPE_UNKNOWN is an element of enum XAAuditType. Its value is "XA_AUDIT_TYPE_UNKNOWN".
	 */
	public static final int XA_AUDIT_TYPE_UNKNOWN = 0;
	/**
	 * XA_AUDIT_TYPE_ALL is an element of enum XAAuditType. Its value is "XA_AUDIT_TYPE_ALL".
	 */
	public static final int XA_AUDIT_TYPE_ALL = 1;
	/**
	 * XA_AUDIT_TYPE_READ is an element of enum XAAuditType. Its value is "XA_AUDIT_TYPE_READ".
	 */
	public static final int XA_AUDIT_TYPE_READ = 2;
	/**
	 * XA_AUDIT_TYPE_WRITE is an element of enum XAAuditType. Its value is "XA_AUDIT_TYPE_WRITE".
	 */
	public static final int XA_AUDIT_TYPE_WRITE = 3;
	/**
	 * XA_AUDIT_TYPE_CREATE is an element of enum XAAuditType. Its value is "XA_AUDIT_TYPE_CREATE".
	 */
	public static final int XA_AUDIT_TYPE_CREATE = 4;
	/**
	 * XA_AUDIT_TYPE_DELETE is an element of enum XAAuditType. Its value is "XA_AUDIT_TYPE_DELETE".
	 */
	public static final int XA_AUDIT_TYPE_DELETE = 5;
	/**
	 * XA_AUDIT_TYPE_LOGIN is an element of enum XAAuditType. Its value is "XA_AUDIT_TYPE_LOGIN".
	 */
	public static final int XA_AUDIT_TYPE_LOGIN = 6;

	/**
	 * Max value for enum XAAuditType_MAX
	 */
	public static final int XAAuditType_MAX = 6;


	/***************************************************************
	 * Enum values for ResourceType
	 **************************************************************/
	/**
	 * RESOURCE_UNKNOWN is an element of enum ResourceType. Its value is "RESOURCE_UNKNOWN".
	 */
	public static final int RESOURCE_UNKNOWN = 0;
	/**
	 * RESOURCE_PATH is an element of enum ResourceType. Its value is "RESOURCE_PATH".
	 */
	public static final int RESOURCE_PATH = 1;
	/**
	 * RESOURCE_DB is an element of enum ResourceType. Its value is "RESOURCE_DB".
	 */
	public static final int RESOURCE_DB = 2;
	/**
	 * RESOURCE_TABLE is an element of enum ResourceType. Its value is "RESOURCE_TABLE".
	 */
	public static final int RESOURCE_TABLE = 3;
	/**
	 * RESOURCE_COL_FAM is an element of enum ResourceType. Its value is "RESOURCE_COL_FAM".
	 */
	public static final int RESOURCE_COL_FAM = 4;
	/**
	 * RESOURCE_COLUMN is an element of enum ResourceType. Its value is "RESOURCE_COLUMN".
	 */
	public static final int RESOURCE_COLUMN = 5;
	/**
	 * RESOURCE_VIEW is an element of enum ResourceType. Its value is "RESOURCE_VIEW".
	 */
	public static final int RESOURCE_VIEW = 6;
	/**
	 * RESOURCE_UDF is an element of enum ResourceType. Its value is "RESOURCE_UDF".
	 */
	public static final int RESOURCE_UDF = 7;
	/**
	 * RESOURCE_VIEW_COL is an element of enum ResourceType. Its value is "RESOURCE_VIEW_COL".
	 */
	public static final int RESOURCE_VIEW_COL = 8;
	/**
	 * RESOURCE_TOPOLOGY is an element of enum ResourceType. Its value is "RESOURCE_TOPOLOGY".
	 */
	public static final int RESOURCE_TOPOLOGY = 9;
	/**
	 * RESOURCE_SERVICE_NAME is an element of enum ResourceType. Its value is "RESOURCE_SERVICE_NAME".
	 */
	public static final int RESOURCE_SERVICE_NAME = 10;

	/**
	 * Max value for enum ResourceType_MAX
	 */
	public static final int ResourceType_MAX = 10;


	/***************************************************************
	 * Enum values for XAGroupType
	 **************************************************************/
	/**
	 * XA_GROUP_UNKNOWN is an element of enum XAGroupType. Its value is "XA_GROUP_UNKNOWN".
	 */
	public static final int XA_GROUP_UNKNOWN = 0;
	/**
	 * XA_GROUP_USER is an element of enum XAGroupType. Its value is "XA_GROUP_USER".
	 */
	public static final int XA_GROUP_USER = 1;
	/**
	 * XA_GROUP_GROUP is an element of enum XAGroupType. Its value is "XA_GROUP_GROUP".
	 */
	public static final int XA_GROUP_GROUP = 2;
	/**
	 * XA_GROUP_ROLE is an element of enum XAGroupType. Its value is "XA_GROUP_ROLE".
	 */
	public static final int XA_GROUP_ROLE = 3;

	/**
	 * Max value for enum XAGroupType_MAX
	 */
	public static final int XAGroupType_MAX = 3;


	/***************************************************************
	 * Enum values for XAPermForType
	 **************************************************************/
	/**
	 * XA_PERM_FOR_UNKNOWN is an element of enum XAPermForType. Its value is "XA_PERM_FOR_UNKNOWN".
	 */
	public static final int XA_PERM_FOR_UNKNOWN = 0;
	/**
	 * XA_PERM_FOR_USER is an element of enum XAPermForType. Its value is "XA_PERM_FOR_USER".
	 */
	public static final int XA_PERM_FOR_USER = 1;
	/**
	 * XA_PERM_FOR_GROUP is an element of enum XAPermForType. Its value is "XA_PERM_FOR_GROUP".
	 */
	public static final int XA_PERM_FOR_GROUP = 2;

	/**
	 * Max value for enum XAPermForType_MAX
	 */
	public static final int XAPermForType_MAX = 2;


	/***************************************************************
	 * Enum values for XAPermType
	 **************************************************************/
	/**
	 * XA_PERM_TYPE_UNKNOWN is an element of enum XAPermType. Its value is "XA_PERM_TYPE_UNKNOWN".
	 */
	public static final int XA_PERM_TYPE_UNKNOWN = 0;
	/**
	 * XA_PERM_TYPE_RESET is an element of enum XAPermType. Its value is "XA_PERM_TYPE_RESET".
	 */
	public static final int XA_PERM_TYPE_RESET = 1;
	/**
	 * XA_PERM_TYPE_READ is an element of enum XAPermType. Its value is "XA_PERM_TYPE_READ".
	 */
	public static final int XA_PERM_TYPE_READ = 2;
	/**
	 * XA_PERM_TYPE_WRITE is an element of enum XAPermType. Its value is "XA_PERM_TYPE_WRITE".
	 */
	public static final int XA_PERM_TYPE_WRITE = 3;
	/**
	 * XA_PERM_TYPE_CREATE is an element of enum XAPermType. Its value is "XA_PERM_TYPE_CREATE".
	 */
	public static final int XA_PERM_TYPE_CREATE = 4;
	/**
	 * XA_PERM_TYPE_DELETE is an element of enum XAPermType. Its value is "XA_PERM_TYPE_DELETE".
	 */
	public static final int XA_PERM_TYPE_DELETE = 5;
	/**
	 * XA_PERM_TYPE_ADMIN is an element of enum XAPermType. Its value is "XA_PERM_TYPE_ADMIN".
	 */
	public static final int XA_PERM_TYPE_ADMIN = 6;
	/**
	 * XA_PERM_TYPE_OBFUSCATE is an element of enum XAPermType. Its value is "XA_PERM_TYPE_OBFUSCATE".
	 */
	public static final int XA_PERM_TYPE_OBFUSCATE = 7;
	/**
	 * XA_PERM_TYPE_MASK is an element of enum XAPermType. Its value is "XA_PERM_TYPE_MASK".
	 */
	public static final int XA_PERM_TYPE_MASK = 8;
	/**
	 * XA_PERM_TYPE_EXECUTE is an element of enum XAPermType. Its value is "XA_PERM_TYPE_EXECUTE".
	 */
	public static final int XA_PERM_TYPE_EXECUTE = 9;
	/**
	 * XA_PERM_TYPE_SELECT is an element of enum XAPermType. Its value is "XA_PERM_TYPE_SELECT".
	 */
	public static final int XA_PERM_TYPE_SELECT = 10;
	/**
	 * XA_PERM_TYPE_UPDATE is an element of enum XAPermType. Its value is "XA_PERM_TYPE_UPDATE".
	 */
	public static final int XA_PERM_TYPE_UPDATE = 11;
	/**
	 * XA_PERM_TYPE_DROP is an element of enum XAPermType. Its value is "XA_PERM_TYPE_DROP".
	 */
	public static final int XA_PERM_TYPE_DROP = 12;
	/**
	 * XA_PERM_TYPE_ALTER is an element of enum XAPermType. Its value is "XA_PERM_TYPE_ALTER".
	 */
	public static final int XA_PERM_TYPE_ALTER = 13;
	/**
	 * XA_PERM_TYPE_INDEX is an element of enum XAPermType. Its value is "XA_PERM_TYPE_INDEX".
	 */
	public static final int XA_PERM_TYPE_INDEX = 14;
	/**
	 * XA_PERM_TYPE_LOCK is an element of enum XAPermType. Its value is "XA_PERM_TYPE_LOCK".
	 */
	public static final int XA_PERM_TYPE_LOCK = 15;
	/**
	 * XA_PERM_TYPE_ALL is an element of enum XAPermType. Its value is "XA_PERM_TYPE_ALL".
	 */
	public static final int XA_PERM_TYPE_ALL = 16;
	/**
	 * XA_PERM_TYPE_ALLOW is an element of enum XAPermType. Its value is "XA_PERM_TYPE_ALLOW".
	 */
	public static final int XA_PERM_TYPE_ALLOW = 17;
	/**
	 * XA_PERM_TYPE_SUBMIT_TOPOLOGY is an element of enum XAPermType. Its value is "XA_PERM_TYPE_SUBMIT_TOPOLOGY".
	 */
	public static final int XA_PERM_TYPE_SUBMIT_TOPOLOGY = 18;
	/**
	 * XA_PERM_TYPE_FILE_UPLOAD is an element of enum XAPermType. Its value is "XA_PERM_TYPE_FILE_UPLOAD".
	 */
	public static final int XA_PERM_TYPE_FILE_UPLOAD = 19;
	/**
	 * XA_PERM_TYPE_GET_NIMBUS is an element of enum XAPermType. Its value is "XA_PERM_TYPE_GET_NIMBUS".
	 */
	public static final int XA_PERM_TYPE_GET_NIMBUS = 20;
	/**
	 * XA_PERM_TYPE_GET_CLUSTER_INFO is an element of enum XAPermType. Its value is "XA_PERM_TYPE_GET_CLUSTER_INFO".
	 */
	public static final int XA_PERM_TYPE_GET_CLUSTER_INFO = 21;
	/**
	 * XA_PERM_TYPE_FILE_DOWNLOAD is an element of enum XAPermType. Its value is "XA_PERM_TYPE_FILE_DOWNLOAD".
	 */
	public static final int XA_PERM_TYPE_FILE_DOWNLOAD = 22;
	/**
	 * XA_PERM_TYPE_KILL_TOPOLOGY is an element of enum XAPermType. Its value is "XA_PERM_TYPE_KILL_TOPOLOGY".
	 */
	public static final int XA_PERM_TYPE_KILL_TOPOLOGY = 23;
	/**
	 * XA_PERM_TYPE_REBALANCE is an element of enum XAPermType. Its value is "XA_PERM_TYPE_REBALANCE".
	 */
	public static final int XA_PERM_TYPE_REBALANCE = 24;
	/**
	 * XA_PERM_TYPE_ACTIVATE is an element of enum XAPermType. Its value is "XA_PERM_TYPE_ACTIVATE".
	 */
	public static final int XA_PERM_TYPE_ACTIVATE = 25;
	/**
	 * XA_PERM_TYPE_DEACTIVATE is an element of enum XAPermType. Its value is "XA_PERM_TYPE_DEACTIVATE".
	 */
	public static final int XA_PERM_TYPE_DEACTIVATE = 26;
	/**
	 * XA_PERM_TYPE_GET_TOPOLOGY_CONF is an element of enum XAPermType. Its value is "XA_PERM_TYPE_GET_TOPOLOGY_CONF".
	 */
	public static final int XA_PERM_TYPE_GET_TOPOLOGY_CONF = 27;
	/**
	 * XA_PERM_TYPE_GET_TOPOLOGY is an element of enum XAPermType. Its value is "XA_PERM_TYPE_GET_TOPOLOGY".
	 */
	public static final int XA_PERM_TYPE_GET_TOPOLOGY = 28;
	/**
	 * XA_PERM_TYPE_GET_USER_TOPOLOGY is an element of enum XAPermType. Its value is "XA_PERM_TYPE_GET_USER_TOPOLOGY".
	 */
	public static final int XA_PERM_TYPE_GET_USER_TOPOLOGY = 29;
	/**
	 * XA_PERM_TYPE_GET_TOPOLOGY_INFO is an element of enum XAPermType. Its value is "XA_PERM_TYPE_GET_TOPOLOGY_INFO".
	 */
	public static final int XA_PERM_TYPE_GET_TOPOLOGY_INFO = 30;
	/**
	 * XA_PERM_TYPE_UPLOAD_NEW_CREDENTIAL is an element of enum XAPermType. Its value is "XA_PERM_TYPE_UPLOAD_NEW_CREDENTIAL".
	 */
	public static final int XA_PERM_TYPE_UPLOAD_NEW_CREDENTIAL = 31;

	/**
	 * Max value for enum XAPermType_MAX
	 */
	public static final int XAPermType_MAX = 31;


	/***************************************************************
	 * Enum values for ClassTypes
	 **************************************************************/
	/**
	 * CLASS_TYPE_XA_ASSET is an element of enum ClassTypes. Its value is "CLASS_TYPE_XA_ASSET".
	 */
	public static final int CLASS_TYPE_XA_ASSET = 1000;
	/**
	 * CLASS_TYPE_XA_RESOURCE is an element of enum ClassTypes. Its value is "CLASS_TYPE_XA_RESOURCE".
	 */
	public static final int CLASS_TYPE_XA_RESOURCE = 1001;
	/**
	 * CLASS_TYPE_XA_GROUP is an element of enum ClassTypes. Its value is "CLASS_TYPE_XA_GROUP".
	 */
	public static final int CLASS_TYPE_XA_GROUP = 1002;
	/**
	 * CLASS_TYPE_XA_USER is an element of enum ClassTypes. Its value is "CLASS_TYPE_XA_USER".
	 */
	public static final int CLASS_TYPE_XA_USER = 1003;
	/**
	 * CLASS_TYPE_XA_GROUP_USER is an element of enum ClassTypes. Its value is "CLASS_TYPE_XA_GROUP_USER".
	 */
	public static final int CLASS_TYPE_XA_GROUP_USER = 1004;
	/**
	 * CLASS_TYPE_XA_GROUP_GROUP is an element of enum ClassTypes. Its value is "CLASS_TYPE_XA_GROUP_GROUP".
	 */
	public static final int CLASS_TYPE_XA_GROUP_GROUP = 1005;
	/**
	 * CLASS_TYPE_XA_PERM_MAP is an element of enum ClassTypes. Its value is "CLASS_TYPE_XA_PERM_MAP".
	 */
	public static final int CLASS_TYPE_XA_PERM_MAP = 1006;
	/**
	 * CLASS_TYPE_XA_AUDIT_MAP is an element of enum ClassTypes. Its value is "CLASS_TYPE_XA_AUDIT_MAP".
	 */
	public static final int CLASS_TYPE_XA_AUDIT_MAP = 1007;
	/**
	 * CLASS_TYPE_XA_CRED_STORE is an element of enum ClassTypes. Its value is "CLASS_TYPE_XA_CRED_STORE".
	 */
	public static final int CLASS_TYPE_XA_CRED_STORE = 1008;
	/**
	 * CLASS_TYPE_XA_COMN_REF is an element of enum ClassTypes. Its value is "CLASS_TYPE_XA_COMN_REF".
	 */
	public static final int CLASS_TYPE_XA_COMN_REF = 1009;
	/**
	 * CLASS_TYPE_XA_LICENSE is an element of enum ClassTypes. Its value is "CLASS_TYPE_XA_LICENSE".
	 */
	public static final int CLASS_TYPE_XA_LICENSE = 1010;
	/**
	 * CLASS_TYPE_XA_POLICY_EXPORT_AUDIT is an element of enum ClassTypes. Its value is "CLASS_TYPE_XA_POLICY_EXPORT_AUDIT".
	 */
	public static final int CLASS_TYPE_XA_POLICY_EXPORT_AUDIT = 1011;
	/**
	 * CLASS_TYPE_TRX_LOG is an element of enum ClassTypes. Its value is "CLASS_TYPE_TRX_LOG".
	 */
	public static final int CLASS_TYPE_TRX_LOG = 1012;
	/**
	 * CLASS_TYPE_XA_ACCESS_AUDIT is an element of enum ClassTypes. Its value is "CLASS_TYPE_XA_ACCESS_AUDIT".
	 */
	public static final int CLASS_TYPE_XA_ACCESS_AUDIT = 1013;
	/**
	 * CLASS_TYPE_XA_TRANSACTION_LOG_ATTRIBUTE is an element of enum ClassTypes. Its value is "CLASS_TYPE_XA_TRANSACTION_LOG_ATTRIBUTE".
	 */
	public static final int CLASS_TYPE_XA_TRANSACTION_LOG_ATTRIBUTE = 1014;

	/**
	 * Max value for enum ClassTypes_MAX
	 */
	public static final int ClassTypes_MAX = 1014;



	static public String getLabelFor_AssetType( int elementValue ) {
		if( elementValue == 0 ) {
			return "Unknown"; //ASSET_UNKNOWN
		}
		if( elementValue == 1 ) {
			return "HDFS"; //ASSET_HDFS
		}
		if( elementValue == 2 ) {
			return "HBase"; //ASSET_HBASE
		}
		if( elementValue == 3 ) {
			return "Hive"; //ASSET_HIVE
		}
		if( elementValue == 4 ) {
			return "XAAGENT"; // XAAGENT
		}
		if( elementValue == 5 ) {
			return "Knox"; //ASSET_KNOX
		}
		if( elementValue == 6 ) {
			return "Storm"; //ASSET_STORM
		}
		return null;
	}
	
	static public String getLabelFor_PolicyType( int elementValue ) {
		if( elementValue == 0 ) {
			return "Inclusion"; //POLICY_INCLUSION
		}
		if( elementValue == 1 ) {
			return "Exclusion"; //POLICY_EXCLUSION
		}
		return null;
	}

	static public String getLabelFor_XAAuditType( int elementValue ) {
		if( elementValue == 0 ) {
			return "Unknown"; //XA_AUDIT_TYPE_UNKNOWN
		}
		if( elementValue == 1 ) {
			return "All"; //XA_AUDIT_TYPE_ALL
		}
		if( elementValue == 2 ) {
			return "Read"; //XA_AUDIT_TYPE_READ
		}
		if( elementValue == 3 ) {
			return "Write"; //XA_AUDIT_TYPE_WRITE
		}
		if( elementValue == 4 ) {
			return "Create"; //XA_AUDIT_TYPE_CREATE
		}
		if( elementValue == 5 ) {
			return "Delete"; //XA_AUDIT_TYPE_DELETE
		}
		if( elementValue == 6 ) {
			return "Login"; //XA_AUDIT_TYPE_LOGIN
		}
		return null;
	}

	static public String getLabelFor_ResourceType( int elementValue ) {
		if( elementValue == 0 ) {
			return "Unknown"; //RESOURCE_UNKNOWN
		}
		if( elementValue == 1 ) {
			return "Path"; //RESOURCE_PATH
		}
		if( elementValue == 2 ) {
			return "Database"; //RESOURCE_DB
		}
		if( elementValue == 3 ) {
			return "Table"; //RESOURCE_TABLE
		}
		if( elementValue == 4 ) {
			return "Column Family"; //RESOURCE_COL_FAM
		}
		if( elementValue == 5 ) {
			return "Column"; //RESOURCE_COLUMN
		}
		if( elementValue == 6 ) {
			return "VIEW"; //RESOURCE_VIEW
		}
		if( elementValue == 7 ) {
			return "UDF"; //RESOURCE_UDF
		}
		if( elementValue == 8 ) {
			return "View Column"; //RESOURCE_VIEW_COL
		}
		if( elementValue == 9 ) {
			return "Topology"; //RESOURCE_TOPOLOGY
		}
		if( elementValue == 10 ) {
			return "Service"; //RESOURCE_SERVICE
		}
		return null;
	}

	static public String getLabelFor_XAGroupType( int elementValue ) {
		if( elementValue == 0 ) {
			return "Unknown"; //XA_GROUP_UNKNOWN
		}
		if( elementValue == 1 ) {
			return "User"; //XA_GROUP_USER
		}
		if( elementValue == 2 ) {
			return "Group"; //XA_GROUP_GROUP
		}
		if( elementValue == 3 ) {
			return "Role"; //XA_GROUP_ROLE
		}
		return null;
	}

	static public String getLabelFor_XAPermForType( int elementValue ) {
		if( elementValue == 0 ) {
			return "Unknown"; //XA_PERM_FOR_UNKNOWN
		}
		if( elementValue == 1 ) {
			return "Permission for Users"; //XA_PERM_FOR_USER
		}
		if( elementValue == 2 ) {
			return "Permission for Groups"; //XA_PERM_FOR_GROUP
		}
		return null;
	}

	static public String getLabelFor_XAPermType( int elementValue ) {
		if( elementValue == 0 ) {
			return "Unknown"; //XA_PERM_TYPE_UNKNOWN
		}
		if( elementValue == 1 ) {
			return "Reset"; //XA_PERM_TYPE_RESET
		}
		if( elementValue == 2 ) {
			return "Read"; //XA_PERM_TYPE_READ
		}
		if( elementValue == 3 ) {
			return "Write"; //XA_PERM_TYPE_WRITE
		}
		if( elementValue == 4 ) {
			return "Create"; //XA_PERM_TYPE_CREATE
		}
		if( elementValue == 5 ) {
			return "Delete"; //XA_PERM_TYPE_DELETE
		}
		if( elementValue == 6 ) {
			return "Admin"; //XA_PERM_TYPE_ADMIN
		}
		if( elementValue == 7 ) {
			return "Obfuscate"; //XA_PERM_TYPE_OBFUSCATE
		}
		if( elementValue == 8 ) {
			return "Mask"; //XA_PERM_TYPE_MASK
		}
		if( elementValue == 9 ) {
			return "Execute"; //XA_PERM_TYPE_EXECUTE
		}
		if( elementValue == 10 ) {
			return "Select"; //XA_PERM_TYPE_SELECT
		}
		if( elementValue == 11 ) {
			return "Update"; //XA_PERM_TYPE_UPDATE
		}
		if( elementValue == 12 ) {
			return "Drop"; //XA_PERM_TYPE_DROP
		}
		if( elementValue == 13 ) {
			return "Alter"; //XA_PERM_TYPE_ALTER
		}
		if( elementValue == 14 ) {
			return "Index"; //XA_PERM_TYPE_INDEX
		}
		if( elementValue == 15 ) {
			return "Lock"; //XA_PERM_TYPE_LOCK
		}
		if( elementValue == 16 ) {
			return "All"; //XA_PERM_TYPE_ALL
		}
		if( elementValue == 17 ) {
			return "Allow"; //XA_PERM_TYPE_ALLOW
		}
		if( elementValue == 18 ) {
			// return "Submit Topology"; //XA_PERM_TYPE_SUBMIT_TOPOLOGY
			return "submitTopology" ;
		}
		if( elementValue == 19 ) {
			// return "File Upload"; //XA_PERM_TYPE_FILE_UPLOAD
			return "fileUpload" ;
		}
		if( elementValue == 20 ) {
			// return "Get Nimbus Conf"; //XA_PERM_TYPE_GET_NIMBUS
			return "getNimbusConf" ;
		}
		if( elementValue == 21 ) {
			// return "Get Cluster Info"; //XA_PERM_TYPE_GET_CLUSTER_INFO
			return "getClusterInfo" ;
		}
		if( elementValue == 22 ) {
			// return "File Download"; //XA_PERM_TYPE_FILE_DOWNLOAD
			return "fileDownload" ;
		}
		if( elementValue == 23 ) {
			// return "Kill Topology"; //XA_PERM_TYPE_KILL_TOPOLOGY
			return "killTopology" ;
		}
		if( elementValue == 24 ) {
			// return "Rebalance"; //XA_PERM_TYPE_REBALANCE
			return "rebalance" ;
		}
		if( elementValue == 25 ) {
			// return "Activate"; //XA_PERM_TYPE_ACTIVATE
			return "activate" ;
		}
		if( elementValue == 26 ) {
			// return "Deactivate"; //XA_PERM_TYPE_DEACTIVATE
			return "deactivate" ;
		}
		if( elementValue == 27 ) {
			// return "Get Topology Conf"; //XA_PERM_TYPE_GET_TOPOLOGY_CONF
			return "getTopologyConf" ;
		}
		if( elementValue == 28 ) {
			// return "Get Topology"; //XA_PERM_TYPE_GET_TOPOLOGY
			return "getTopology" ;
		}
		if( elementValue == 29 ) {
			// return "Get User Topology"; //XA_PERM_TYPE_GET_USER_TOPOLOGY
			return "getUserTopology" ;
		}
		if( elementValue == 30 ) {
			// return "Get Topology Info"; //XA_PERM_TYPE_GET_TOPOLOGY_INFO
			return "getTopologyInfo" ;
		}
		if( elementValue == 31 ) {
			// return "Upload New Credential"; //XA_PERM_TYPE_UPLOAD_NEW_CREDENTIAL
			return "uploadNewCredentials" ;
		}
		return null;
	}

	static public String getLabelFor_ClassTypes( int elementValue ) {
		if( elementValue == 1000 ) {
			return "Asset"; //CLASS_TYPE_XA_ASSET
		}
		if( elementValue == 1001 ) {
			return "Resource"; //CLASS_TYPE_XA_RESOURCE
		}
		if( elementValue == 1002 ) {
			return "XA Group"; //CLASS_TYPE_XA_GROUP
		}
		if( elementValue == 1003 ) {
			return "XA User"; //CLASS_TYPE_XA_USER
		}
		if( elementValue == 1004 ) {
			return "XA Group of Users"; //CLASS_TYPE_XA_GROUP_USER
		}
		if( elementValue == 1005 ) {
			return "XA Group of groups"; //CLASS_TYPE_XA_GROUP_GROUP
		}
		if( elementValue == 1006 ) {
			return "XA permissions for resource"; //CLASS_TYPE_XA_PERM_MAP
		}
		if( elementValue == 1007 ) {
			return "XA audits for resource"; //CLASS_TYPE_XA_AUDIT_MAP
		}
		if( elementValue == 1008 ) {
			return "XA credential store"; //CLASS_TYPE_XA_CRED_STORE
		}
		if( elementValue == 1009 ) {
			return "XA Common Reference"; //CLASS_TYPE_XA_COMN_REF
		}
		if( elementValue == 1010 ) {
			return "XA License"; //CLASS_TYPE_XA_LICENSE
		}
		if( elementValue == 1011 ) {
			return "XA Policy Export Audit"; //CLASS_TYPE_XA_POLICY_EXPORT_AUDIT
		}
		if( elementValue == 1012 ) {
			return "Transaction log"; //CLASS_TYPE_TRX_LOG
		}
		if( elementValue == 1013 ) {
			return "Access Audit"; //CLASS_TYPE_XA_ACCESS_AUDIT
		}
		return null;
	}

	static public int getEnumFor_AssetType(String label) {
		if (label == null) {
			return 0;
		}
		if (label.equalsIgnoreCase("Unknown")) {
			return AppConstants.ASSET_UNKNOWN; // ASSET_UNKNOWN
		}
		if (label.equalsIgnoreCase("HDFS")) {
			return AppConstants.ASSET_HDFS; // ASSET_HDFS
		}
		if (label.equalsIgnoreCase("HBase")) {
			return AppConstants.ASSET_HBASE; // ASSET_HBASE
		}
		if (label.equalsIgnoreCase("Hive")) {
			return AppConstants.ASSET_HIVE; // ASSET_HIVE
		}
		if (label.equalsIgnoreCase("Knox")) {
			return AppConstants.ASSET_KNOX; // ASSET_KNOX
		}
		if (label.equalsIgnoreCase("Storm")) {
			return AppConstants.ASSET_STORM; // ASSET_STORM
		}
		return 0;
	}

	static public int getEnumFor_BooleanValue(boolean label) {
		if (label) {
			return AppConstants.BOOL_TRUE;
		} else {
			return AppConstants.BOOL_FALSE;
		}
	}

	static public boolean getBooleanFor_BooleanValue(int elementValue) {
		if (elementValue == 1) {
			return true;
		}
		if (elementValue == 2) {
			return false;
		}
		return false;
	}

	static public int getEnumFor_ResourceType(String label) {
		if (label == null) {
			return 0;
		}
		if (label.equalsIgnoreCase("Unknown")) {
			return AppConstants.RESOURCE_UNKNOWN; // RESOURCE_UNKNOWN
		}
		if (label.equalsIgnoreCase("Path")) {
			return AppConstants.RESOURCE_PATH; // RESOURCE_PATH
		}
		if (label.equalsIgnoreCase("Database")) {
			return AppConstants.RESOURCE_DB; // RESOURCE_DB
		}
		if (label.equalsIgnoreCase("Table")) {
			return AppConstants.RESOURCE_TABLE; // RESOURCE_TABLE
		}
		if (label.equalsIgnoreCase("Column Family")) {
			return AppConstants.RESOURCE_COL_FAM; // RESOURCE_COL_FAM
		}
		if (label.equalsIgnoreCase("Column")) {
			return AppConstants.RESOURCE_COLUMN; // RESOURCE_COLUMN
		}
		if (label.equalsIgnoreCase("VIEW")) {
			return AppConstants.RESOURCE_VIEW; // RESOURCE_VIEW
		}
		if (label.equalsIgnoreCase("UDF")) {
			return AppConstants.RESOURCE_UDF; // RESOURCE_UDF
		}
		if (label.equalsIgnoreCase("View Column")) {
			return AppConstants.RESOURCE_VIEW_COL; // RESOURCE_VIEW_COL
		}
		if (label.equalsIgnoreCase("Topology")) {
			return AppConstants.RESOURCE_TOPOLOGY; // RESOURCE_TOPOLOGY
		}
		if (label.equalsIgnoreCase("Service")) {
			return AppConstants.RESOURCE_SERVICE_NAME; // RESOURCE_SERVICE_NAME
		}
		return 0;
	}

	static public int getEnumFor_XAPermType(String label) {
		if (label == null) {
			return 0;
		}
		if (label.equalsIgnoreCase("Unknown")) {
			return AppConstants.XA_PERM_TYPE_UNKNOWN; // XA_PERM_TYPE_UNKNOWN
		}
		if (label.equalsIgnoreCase("Reset")) {
			return AppConstants.XA_PERM_TYPE_RESET; // XA_PERM_TYPE_RESET
		}
		if (label.equalsIgnoreCase("Read")) {
			return AppConstants.XA_PERM_TYPE_READ; // XA_PERM_TYPE_READ
		}
		if (label.equalsIgnoreCase("Write")) {
			return AppConstants.XA_PERM_TYPE_WRITE; // XA_PERM_TYPE_WRITE
		}
		if (label.equalsIgnoreCase("Create")) {
			return AppConstants.XA_PERM_TYPE_CREATE; // XA_PERM_TYPE_CREATE
		}
		if (label.equalsIgnoreCase("Delete")) {
			return AppConstants.XA_PERM_TYPE_DELETE; // XA_PERM_TYPE_DELETE
		}
		if (label.equalsIgnoreCase("Admin")) {
			return AppConstants.XA_PERM_TYPE_ADMIN; // XA_PERM_TYPE_ADMIN
		}
		if (label.equalsIgnoreCase("Obfuscate")) {
			return AppConstants.XA_PERM_TYPE_OBFUSCATE; // XA_PERM_TYPE_OBFUSCATE
		}
		if (label.equalsIgnoreCase("Mask")) {
			return AppConstants.XA_PERM_TYPE_MASK; // XA_PERM_TYPE_MASK
		}
		if (label.equalsIgnoreCase("Execute")) {
			return AppConstants.XA_PERM_TYPE_EXECUTE; // XA_PERM_TYPE_EXECUTE
		}
		if (label.equalsIgnoreCase("Select")) {
			return AppConstants.XA_PERM_TYPE_SELECT; // XA_PERM_TYPE_SELECT
		}
		if (label.equalsIgnoreCase("Update")) {
			return AppConstants.XA_PERM_TYPE_UPDATE; // XA_PERM_TYPE_UPDATE
		}
		if (label.equalsIgnoreCase("Drop")) {
			return AppConstants.XA_PERM_TYPE_DROP; // XA_PERM_TYPE_DROP
		}
		if (label.equalsIgnoreCase("Alter")) {
			return AppConstants.XA_PERM_TYPE_ALTER; // XA_PERM_TYPE_ALTER
		}
		if (label.equalsIgnoreCase("Index")) {
			return AppConstants.XA_PERM_TYPE_INDEX; // XA_PERM_TYPE_INDEX
		}
		if (label.equalsIgnoreCase("Lock")) {
			return AppConstants.XA_PERM_TYPE_LOCK; // XA_PERM_TYPE_LOCK
		}
		if (label.equalsIgnoreCase("All")) {
			return AppConstants.XA_PERM_TYPE_ALL; // XA_PERM_TYPE_ALL
		}
		if(label.equalsIgnoreCase("Allow")) {
			return AppConstants.XA_PERM_TYPE_ALLOW; //XA_PERM_TYPE_ALLOW
		}
		if(label.equalsIgnoreCase("Submit Topology")) {
			return AppConstants.XA_PERM_TYPE_SUBMIT_TOPOLOGY; //XA_PERM_TYPE_SUBMIT_TOPOLOGY
		}
		if(label.equalsIgnoreCase("File Upload")) {
			return AppConstants.XA_PERM_TYPE_FILE_UPLOAD; //XA_PERM_TYPE_FILE_UPLOAD
		}
		if(label.equalsIgnoreCase("Get Nimbus Conf")) {
			return AppConstants.XA_PERM_TYPE_GET_NIMBUS; //XA_PERM_TYPE_GET_NIMBUS
		}
		if(label.equalsIgnoreCase("Get Cluster Info")) {
			return AppConstants.XA_PERM_TYPE_GET_CLUSTER_INFO; //XA_PERM_TYPE_GET_CLUSTER_INFO
		}
		if(label.equalsIgnoreCase("File Download")) {
			return AppConstants.XA_PERM_TYPE_FILE_DOWNLOAD; //XA_PERM_TYPE_FILE_DOWNLOAD
		}
		if(label.equalsIgnoreCase("Kill Topology")) {
			return AppConstants.XA_PERM_TYPE_KILL_TOPOLOGY; //XA_PERM_TYPE_KILL_TOPOLOGY
		}
		if(label.equalsIgnoreCase("Rebalance")) {
			return AppConstants.XA_PERM_TYPE_REBALANCE; //XA_PERM_TYPE_REBALANCE
		}
		if(label.equalsIgnoreCase("Activate")) {
			return AppConstants.XA_PERM_TYPE_ACTIVATE; //XA_PERM_TYPE_ACTIVATE
		}
		if(label.equalsIgnoreCase("Deactivate")) {
			return AppConstants.XA_PERM_TYPE_DEACTIVATE; //XA_PERM_TYPE_DEACTIVATE
		}
		if(label.equalsIgnoreCase("Get Topology Conf")) {
			return AppConstants.XA_PERM_TYPE_GET_TOPOLOGY_CONF; //XA_PERM_TYPE_GET_TOPOLOGY_CONF
		}
		if(label.equalsIgnoreCase("Get Topology")) {
			return AppConstants.XA_PERM_TYPE_GET_TOPOLOGY; //XA_PERM_TYPE_GET_TOPOLOGY
		}
		if(label.equalsIgnoreCase("Get User Topology")) {
			return AppConstants.XA_PERM_TYPE_GET_USER_TOPOLOGY; //XA_PERM_TYPE_GET_USER_TOPOLOGY
		}
		if(label.equalsIgnoreCase("Get Topology Info")) {
			return AppConstants.XA_PERM_TYPE_GET_TOPOLOGY_INFO; //XA_PERM_TYPE_GET_TOPOLOGY_INFO
		}
		if(label.equalsIgnoreCase("Upload New Credential")) {
			return AppConstants.XA_PERM_TYPE_UPLOAD_NEW_CREDENTIAL; //XA_PERM_TYPE_UPLOAD_NEW_CREDENTIAL
		}
		return 0;
	}

	static public int getEnumFor_PolicyType(String label) {
		if (label == null) {
			return 0;
		}
		if (label.equalsIgnoreCase("Inclusion")) {
			return AppConstants.POLICY_INCLUSION; // POLICY_INCLUSION
		}
		if (label.equalsIgnoreCase("Exclusion")) {
			return AppConstants.POLICY_EXCLUSION; // POLICY_EXCLUSION
		}
		return 0;
	}

}

