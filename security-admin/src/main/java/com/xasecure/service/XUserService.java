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

 package com.xasecure.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xasecure.common.XACommonEnums;
import com.xasecure.common.MessageEnums;
import com.xasecure.common.PropertiesUtil;
import com.xasecure.common.SearchField;
import com.xasecure.common.SortField;
import com.xasecure.common.StringUtil;
import com.xasecure.common.XAConstants;
import com.xasecure.entity.XXPortalUser;
import com.xasecure.entity.XXPortalUserRole;
import com.xasecure.view.VXPortalUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.xasecure.biz.XABizUtil;
import com.xasecure.common.AppConstants;
import com.xasecure.common.view.VTrxLogAttr;
import com.xasecure.db.XADaoManager;
import com.xasecure.entity.*;
import com.xasecure.util.XAEnumUtil;
import com.xasecure.view.*;

@Service
@Scope("singleton")
public class XUserService extends XUserServiceBase<XXUser, VXUser> {

	public static Long createdByUserId = 1L;

	@Autowired
	XADaoManager daoManager;

	@Autowired
	XPermMapService xPermMapService;

	@Autowired
	StringUtil stringUtil;

	@Autowired
	XAEnumUtil xaEnumUtil;

	@Autowired
	XABizUtil xaBizUtil;

	String hiddenPasswordString;

	static HashMap<String, VTrxLogAttr> trxLogAttrs = new HashMap<String, VTrxLogAttr>();
	static {
		trxLogAttrs.put("name", new VTrxLogAttr("name", "Login ID", false));
		trxLogAttrs.put("firstName", new VTrxLogAttr("firstName", "First Name",
				false));
		trxLogAttrs.put("lastName", new VTrxLogAttr("lastName", "Last Name",
				false));
		trxLogAttrs.put("emailAddress", new VTrxLogAttr("emailAddress",
				"Email Address", false));
		trxLogAttrs.put("password", new VTrxLogAttr("password", "Password",
				false));
	}

	public XUserService() {
		searchFields.add(new SearchField("name", "obj.name",
				SearchField.DATA_TYPE.STRING, SearchField.SEARCH_TYPE.PARTIAL));

		searchFields.add(new SearchField("emailAddress", "xXPortalUser.emailAddress",
				SearchField.DATA_TYPE.STRING, SearchField.SEARCH_TYPE.PARTIAL,
				"XXPortalUser xXPortalUser", "xXPortalUser.loginId = obj.name "));

		searchFields.add(new SearchField("userName", "obj.name",
				SearchField.DATA_TYPE.STRING, SearchField.SEARCH_TYPE.FULL));

		searchFields.add(new SearchField("userSource", "xXPortalUser.userSource",
				SearchField.DATA_TYPE.INTEGER, SearchField.SEARCH_TYPE.FULL,
				"XXPortalUser xXPortalUser", "xXPortalUser.loginId = obj.name "));
		
		searchFields.add(new SearchField("userRoleList", "xXPortalUserRole.userRole",
				SearchField.DATA_TYPE.STRING, SearchField.SEARCH_TYPE.FULL,
				"XXPortalUser xXPortalUser, XXPortalUserRole xXPortalUserRole", 
				"xXPortalUser.id=xXPortalUserRole.userId and xXPortalUser.loginId = obj.name "));
		
		
		createdByUserId = new Long(PropertiesUtil.getIntProperty(
				"xa.xuser.createdByUserId", 1));

		hiddenPasswordString = PropertiesUtil.getProperty("xa.password.hidden",
				"*****");

		sortFields.add(new SortField("name", "obj.name",true,SortField.SORT_ORDER.ASC));
		
	}

	@Override
	protected void validateForCreate(VXUser vObj) {

		XXUser xUser = daoManager.getXXUser().findByUserName(vObj.getName());
		if (xUser != null) {
			throw restErrorUtil.createRESTException("XUser already exists",
					MessageEnums.ERROR_DUPLICATE_OBJECT);
		}

	}

	@Override
	protected void validateForUpdate(VXUser vObj, XXUser mObj) {
		String vObjName = vObj.getName();
		String mObjName = mObj.getName();
		if (vObjName != null && mObjName != null) {
			if (!vObjName.trim().equals(mObjName.trim())) {
				validateForCreate(vObj);
			}
		}
	}

	public VXUser getXUserByUserName(String userName) {
		XXUser xxUser = daoManager.getXXUser().findByUserName(userName);
		if (xxUser == null) {
			throw restErrorUtil.createRESTException(userName + " is Not Found",
					MessageEnums.DATA_NOT_FOUND);
		}
		return super.populateViewBean(xxUser);

	}

	public VXUser createXUserWithOutLogin(VXUser vxUser) {
		XXUser xxUser = daoManager.getXXUser().findByUserName(vxUser.getName());
		boolean userExists = true;
		if (xxUser == null) {
			xxUser = new XXUser();
			userExists = false;
		}

		xxUser = mapViewToEntityBean(vxUser, xxUser, 0);
		XXPortalUser xXPortalUser = daoManager.getXXPortalUser().getById(createdByUserId);
		if (xXPortalUser != null) {
			xxUser.setAddedByUserId(createdByUserId);
			xxUser.setUpdatedByUserId(createdByUserId);
		}

		if (userExists) {
			xxUser = getDao().update(xxUser);
		} else {
			xxUser = getDao().create(xxUser);
		}
		vxUser = postCreate(xxUser);
		return vxUser;
	}

	public VXUser readResourceWithOutLogin(Long id) {
		XXUser resource = getDao().getById(id);
		if (resource == null) {
			// Returns code 400 with DATA_NOT_FOUND as the error message
			throw restErrorUtil.createRESTException(getResourceName()
					+ " not found", MessageEnums.DATA_NOT_FOUND, id, null,
					"preRead: " + id + " not found.");
		}

		VXUser vxUser = populateViewBean(resource);
		return vxUser;
	}

	@Override
	protected VXUser mapEntityToViewBean(VXUser vObj, XXUser mObj) {
		super.mapEntityToViewBean(vObj, mObj);
		String userName = vObj.getName();
		populateUserAttributes(userName, vObj);
		return vObj;
	}

	@Override
	public VXUser populateViewBean(XXUser xUser) {
		VXUser vObj = super.populateViewBean(xUser);
		String userName = vObj.getName();
		populateUserAttributes(userName, vObj);
		populateGroupList(xUser.getId(), vObj);
		return vObj;
	}

	private void populateGroupList(Long xUserId, VXUser vObj) {
		List<XXGroupUser> xGroupUserList = daoManager.getXXGroupUser()
				.findByUserId(xUserId);
		Set<Long> groupIdList = new HashSet<Long>();
		Set<String> groupNameList = new HashSet<String>();
		if (xGroupUserList != null) {
			for (XXGroupUser xGroupUser : xGroupUserList) {
				groupIdList.add(xGroupUser.getParentGroupId());
				groupNameList.add(xGroupUser.getName());
			}
		}
		List<Long> groups = new ArrayList<Long>(groupIdList);
		List<String> groupNames = new ArrayList<String>(groupNameList);
		vObj.setGroupIdList(groups);
		vObj.setGroupNameList(groupNames);
	}

	private void populateUserAttributes(String userName, VXUser vObj) {
		if (userName != null && !userName.isEmpty()) {
			List<String> userRoleList =new ArrayList<String>();
			XXPortalUser xXPortalUser = daoManager.getXXPortalUser().findByLoginId(userName);
			if (xXPortalUser != null) {
				vObj.setFirstName(xXPortalUser.getFirstName());
				vObj.setLastName(xXPortalUser.getLastName());
				vObj.setPassword(PropertiesUtil
						.getProperty("xa.password.hidden"));
				String emailAddress = xXPortalUser.getEmailAddress();
				if (emailAddress != null
						&& stringUtil.validateEmail(emailAddress)) {
					vObj.setEmailAddress(xXPortalUser.getEmailAddress());
				}
				vObj.setStatus(xXPortalUser.getStatus());
				vObj.setUserSource(xXPortalUser.getUserSource());
				List<XXPortalUserRole> gjUserRoleList = daoMgr.getXXPortalUserRole().findByParentId(
						xXPortalUser.getId());
				
				for (XXPortalUserRole gjUserRole : gjUserRoleList) {
					userRoleList.add(gjUserRole.getUserRole());
				}
			}
			if(userRoleList==null || userRoleList.size()==0){
				userRoleList.add(XAConstants.ROLE_USER);
			}			
			vObj.setUserRoleList(userRoleList);
		}
	}

	public List<XXTrxLog> getTransactionLog(VXUser vResource, String action) {
		return getTransactionLog(vResource, null, action);
	}

	public List<XXTrxLog> getTransactionLog(VXUser vObj, VXPortalUser mObj,
			String action) {
		if (vObj == null
				&& (action == null || !action.equalsIgnoreCase("update"))) {
			return null;
		}

		List<XXTrxLog> trxLogList = new ArrayList<XXTrxLog>();
		try {
			Field nameField = vObj.getClass().getDeclaredField("name");
			nameField.setAccessible(true);
			String objectName = "" + nameField.get(vObj);
			Field[] fields = vObj.getClass().getDeclaredFields();

			for (Field field : fields) {
				field.setAccessible(true);
				String fieldName = field.getName();
				if (!trxLogAttrs.containsKey(fieldName)) {
					continue;
				}

				VTrxLogAttr vTrxLogAttr = trxLogAttrs.get(fieldName);

				XXTrxLog xTrxLog = new XXTrxLog();
				xTrxLog.setAttributeName(vTrxLogAttr
						.getAttribUserFriendlyName());

				String value = null;
				if (vTrxLogAttr.isEnum()) {
					String enumName = XXUser.getEnumName(fieldName);
					int enumValue = field.get(vObj) == null ? 0 : Integer
							.parseInt("" + field.get(vObj));
					value = xaEnumUtil.getLabel(enumName, enumValue);
				} else {
					value = "" + field.get(vObj);
					if ((value == null || value.equalsIgnoreCase("null"))
							&& !action.equalsIgnoreCase("update")) {
						continue;
					}
				}

				if (fieldName.equalsIgnoreCase("password")) {
					if (value.equalsIgnoreCase(hiddenPasswordString)) {
						continue;
					}
				}

				if (action.equalsIgnoreCase("create")) {
					if (stringUtil.isEmpty(value)
							|| (fieldName.equalsIgnoreCase("emailAddress") && !stringUtil
									.validateEmail(value))) {
						continue;
					}
					xTrxLog.setNewValue(value);
				} else if (action.equalsIgnoreCase("delete")) {
					if (fieldName.equalsIgnoreCase("emailAddress")
							&& !stringUtil.validateEmail(value)) {
						continue;
					}
					xTrxLog.setPreviousValue(value);
				} else if (action.equalsIgnoreCase("update")) {
					String oldValue = null;
					Field[] mFields = mObj.getClass().getDeclaredFields();
					for (Field mField : mFields) {
						mField.setAccessible(true);
						String mFieldName = mField.getName();
						if (mFieldName.equalsIgnoreCase("loginId")) {
							mFieldName = "name";
						}
						if (fieldName.equalsIgnoreCase(mFieldName)) {
							oldValue = mField.get(mObj) + "";
							break;
						}
					}
					if (oldValue.equalsIgnoreCase(value)) {
						continue;
					}
					if (fieldName.equalsIgnoreCase("emailAddress")) {
						if (stringUtil.validateEmail(oldValue)) {
							xTrxLog.setPreviousValue(oldValue);
						}
						if (stringUtil.validateEmail(value)) {
							xTrxLog.setNewValue(value);
						}
					} else {
						xTrxLog.setPreviousValue(oldValue);
						xTrxLog.setNewValue(value);
					}
				}

				xTrxLog.setAction(action);
				xTrxLog.setObjectClassType(AppConstants.CLASS_TYPE_XA_USER);
				xTrxLog.setObjectId(vObj.getId());
				xTrxLog.setObjectName(objectName);

				trxLogList.add(xTrxLog);
			}

			if (trxLogList.size() == 0) {
				XXTrxLog xTrxLog = new XXTrxLog();
				xTrxLog.setAction(action);
				xTrxLog.setObjectClassType(AppConstants.CLASS_TYPE_XA_USER);
				xTrxLog.setObjectId(vObj.getId());
				xTrxLog.setObjectName(objectName);
				trxLogList.add(xTrxLog);
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return trxLogList;
	}

}
