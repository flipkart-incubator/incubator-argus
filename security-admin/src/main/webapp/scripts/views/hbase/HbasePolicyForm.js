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

 
define(function(require){
    'use strict';

	var Backbone		= require('backbone');

	var XAEnums			= require('utils/XAEnums');
	var localization	= require('utils/XALangSupport');
	var XAUtil			= require('utils/XAUtils');
    
	var VXPermMapList	= require('collections/VXPermMapList');
	var VXGroupList		= require('collections/VXGroupList');
	var VXAuditMapList	= require('collections/VXAuditMapList');
	var VXAuditMap		= require('models/VXAuditMap');
	var VXPermMap		= require('models/VXPermMap');
	var VXUserList		= require('collections/VXUserList');
	var FormInputItemList = require('views/common/FormInputItemList');
	var UserPermissionList = require('views/common/UserPermissionList');



	require('Backbone.BootstrapModal');
	require('backbone-forms.list');
	require('backbone-forms.templates');
	require('backbone-forms');

	var HbasePolicyForm = Backbone.Form.extend(
	/** @lends PolicyForm */
	{
		_viewName : 'PolicyForm',
		
		type : {
			//DATABASE : 1,
			TABLE    : 2,
			COLUMN   : 3,
			COLUMN_FAMILIES   : 4
		},

    	/**
		* intialize a new PolicyForm Form View 
		* @constructs
		*/
		initialize: function(options) {
			console.log("initialized a PolicyForm Form View");
    		Backbone.Form.prototype.initialize.call(this, options);

			_.extend(this, _.pick(options, 'assetModel'));
			this.initializeCollection();
			this.bindEvents();
		},
		
		/** all events binding here */
		bindEvents : function(){
			this.on('_vAuditListToggle:change', function(form, fieldEditor){
    			this.evAuditChange(form, fieldEditor);
    		});
			this.on('resourceStatus:change', function(form, fieldEditor){
    			this.evResourceStatusChange(form, fieldEditor);
    		});
			/*this.on('isEncrypt:change', function(form, fieldEditor){
    			this.evEncryptChange(form, fieldEditor);
    		});*/
		},
		initializeCollection: function(){
			this.permMapList = this.model.isNew() ? new VXPermMapList() : this.model.get('permMapList');
			this.auditList = this.model.isNew() ? new VXAuditMapList() : this.model.get('auditList');
		   
		   /*If the model passed to the fn is new return an empty collection
		    * otherwise return a collection that has models like 
		    * {
		    * 	groupId : 5,
		    * 	permissionList : [4,3]
		    * }
		    * The formInputList will be passed to the forminputitemlist view.
		    */
		   
		   this.formInputList = XAUtil.makeCollForGroupPermission(this.model);
		   this.userPermInputList = XAUtil.makeCollForUserPermission(this.model);
		   
		},
		/** fields for the form
		*/
	//	fields: ['name', 'description', '_vAuditListToggle', 'isEncrypt','isRecursive'],
		schema :function(){
			var that = this;
			//var plugginAttr = this.getPlugginAttr(true);
			return {
				policyName : {
					   type 		: 'Text',
					   title		: localization.tt("lbl.policyName"),
//					   validators  	: [{'type' :'required'}]
					   editorAttrs 	:{ maxlength: 255}
				},
				tables : {
					type		: 'Select2Remote',
					title		: localization.tt("lbl.selectTableName")+' *',
					editorAttrs :{'data-placeholder': 'Select Tables'},
					validators  : ['required'],//,{type:'regexp',regexp:/^[a-zA-Z*?][a-zA-Z0-9_'&-/\$]*[A-Za-z0-9]*$/i,message :localization.tt('validationMessages.enterValidName')}],
					pluginAttr  : this.getPlugginAttr(true,this.type.TABLE),
	                options    : function(callback, editor){
	                    callback();
	                }
				},
				columnFamilies : {
					type		: 'Select2Remote',
					title		: localization.tt("lbl.selectColumnFamilies"),
					editorAttrs :{'data-placeholder': 'Select Column Families'},
	//				editorAttrs :{'disabled' :'disabled','data-placeholder': 'Select Column Families'},
					//validators  : [{type:'regexp',regexp:/^[a-zA-Z*?][a-zA-Z0-9_'&-/\$]*[A-Za-z0-9]*$/i,message :localization.tt('validationMessages.enterValidName')}],
					pluginAttr  : this.getPlugginAttr(true,this.type.COLUMN_FAMILIES),
					options    : function(callback, editor){
						callback();
					}
					
					
				},
				columns : {
					type		: 'Select2Remote',
//					type		: 'Text',
					title		: localization.tt("lbl.enterColumnName"),
		//			validators  : [{type:'regexp',regexp:/^[a-zA-Z*?][a-zA-Z0-9_'&-/\$]*[A-Za-z0-9]*$/i,message :localization.tt('validationMessages.enterValidName')}],
					editorAttrs :{ 'placeholder': 'Enter Column Name'},
					pluginAttr  : this.getPlugginAttr(false,this.type.COLUMN),
				},
				_vAuditListToggle : {
					type		: 'Switch',
					title		: localization.tt("lbl.auditLogging"),
					listType	: 'VNameValue',
					switchOn	: true
				},
				/*isEncrypt : {
					type		: 'Switch',
					title		: localization.tt("lbl.encrypted"),
					switchOn	: false
	//				fieldAttrs : {style : 'display:none;'}
				},*/
				permMapList : {
					getValue : function(){
						console.log(this);
					}
				},
				resourceStatus : {
					type		: 'Switch',
					title		: localization.tt("lbl.policyStatus"),
					onText		: 'enabled',
					offText		: 'disabled',
					width		: '85',
					switchOn	: true
				},
				description : { 
					type		: 'TextArea',
					title		: localization.tt("lbl.description"), 
				//	validators	: [/^[A-Za-z ,.'-]+$/i],
					template	:_.template('<div class="altField" data-editor></div>')
				},
			};	
		},

		evAuditChange : function(form, fieldEditor){
			XAUtil.checkDirtyFieldForToggle(fieldEditor);
			if(fieldEditor.getValue() == 1){
				this.model.set('auditList', new VXAuditMapList(new VXAuditMap({
					'auditType' : XAEnums.XAAuditType.XA_AUDIT_TYPE_ALL.value,//fieldEditor.getValue()//
					'resourceId' :this.model.get('id')
					
				})));
			} else {
				var validation  = this.formValidation();
				if(validation.groupPermSet || validation.isUsers)
					this.model.unset('auditList');
				else{
					XAUtil.alertPopup({
						msg :localization.tt("msg.policyNotHavingPerm"),
						callback : function(){
							fieldEditor.setValue('ON');
						}
					});
				}
			}
			console.log(fieldEditor);
		},
		evResourceStatusChange : function(form, fieldEditor){
			XAUtil.checkDirtyFieldForToggle(fieldEditor);
		},
		evEncryptChange : function(form, fieldEditor){
			XAUtil.checkDirtyFieldForToggle(fieldEditor);
		},
		/** on render callback */
		render: function(options) {
			var that = this;
			 Backbone.Form.prototype.render.call(this, options);

			this.initializePlugins();
			this.renderSelectTagsFields();
			this.renderCustomFields();
			if(!this.model.isNew()){
				this.setUpSwitches();
				if(!_.isEmpty(this.fields.tables.editor.$el.val()))
					that.fields.columnFamilies.editor.$el.prop('disabled',false);
				if(!_.isEmpty(this.fields.columnFamilies.editor.$el.val()))
					that.fields.columns.editor.$el.prop('disabled',false);
			}
			if(this.model.isNew() && this.fields._vAuditListToggle.editor.getValue() == 1){
				this.model.set('auditList', new VXAuditMapList(new VXAuditMap({
					'auditType' : XAEnums.XAAuditType.XA_AUDIT_TYPE_ALL.value,//fieldEditor.getValue()//
					'resourceId' :this.model.get('id')
					
				})));
			}
		},
		renderSelectTagsFields :function(){
			var that = this;
			
			this.fields.tables.editor.$el.on('change',function(e){
				console.log('change on table Name');
				that.checkMultiselectDirtyField(e, that.type.TABLE);
				that.fields.tables.setValue(that.fields.tables.editor.$el.select2('data').map(function(obj){return obj.text}).toString());
					
			});
			this.fields.columnFamilies.editor.$el.on('change',function(e){
				console.log('change on column Families Name');
				that.checkMultiselectDirtyField(e, that.type.COLUMN_FAMILIES);
				that.fields.columnFamilies.setValue(that.fields.columnFamilies.editor.$el.select2('data').map(function(obj){return obj.text}).toString());
			});
			
		},
		getDataParams : function(type, term){
			var dataParams = {
			//	dataSourceName : 'hadoopdev',
				dataSourceName : this.assetModel.get('name')
			// databaseName: term,
			};
			if (type == this.type.TABLE && this.fields) {
				//dataParams.databaseName = this.fields.databases.editor.$el.select2('data')[0].text;
				dataParams.tableName = term;
			}
			if (type == this.type.COLUMN_FAMILIES && !_.isEmpty(this.fields.tables.getValue())) {
				dataParams.tableName = this.fields.tables.editor.$el.select2('data')[0].text;
				dataParams.columnFamilies  = _.isEmpty(term) ? "*" :  term;
			}
			return dataParams;
		},
		getPlugginAttr :function(autocomplete, searchType){
			var that =this;
			var type = searchType;
			if(!autocomplete)
				return{tags : true,width :'220px',multiple: true,minimumInputLength: 1};
			else {
				
				
				return {
					closeOnSelect : true,
					//placeholder : 'Select User',
					tags:true,
					multiple: true,
					minimumInputLength: 1,
					width :'220px',
					tokenSeparators: [",", " "],
					initSelection : function (element, callback) {
						var data = [];
						$(element.val().split(",")).each(function () {
							data.push({id: this, text: this});
						});
						callback(data);
					},
					createSearchChoice: function(term, data) {
						if ($(data).filter(function() {
							return this.text.localeCompare(term) === 0;
						}).length === 0) {
							return {
								id : term,
								text: term
							};
						}
					},
					/*query: function (query) {
						var url = "service/assets/hive/resources";
						var data = _.extend(that.getDataParams(type, query.term));
						//var results = [ {id: query.term, path: query.term}];

						$.get(url, data, function (resp) {
							var serverRes = [];
							if(resp.resultSize){
								serverRes = resp.vXStrings.map(function(m, i){	return {id : m.value, path: m.value};	});
							}
							query.callback({results: serverRes});
						}, 'json');

						//query.callback({results: results});
					},*/

					ajax: { 
						url: "service/assets/hbase/resources",
						dataType: 'json',
						params : {
							timeout: 3000
						},
						cache: false,
						data: function (term, page) {
							return _.extend(that.getDataParams(type, term));
							
						},
						results: function (data, page) { 
							var results = [];
							if(data.resultSize != "0"){
								results = data.vXStrings.map(function(m, i){	return {id : m.value, text: m.value};	});
							}
							return { 
								results : results
							};
						},
						transport: function (options) {
							$.ajax(options).error(function() { 
								console.log("ajax failed");
								this.success({
									resultSize : 0
								});
							});
							/*$.ajax.error(function(data) { 
								console.log("ajax failed");
								return {
									results : []
								};
							});*/

						}

					},	
					formatResult : function(result){
						return result.text;
					},
					formatSelection : function(result){
						return result.text;
					},
					formatNoMatches : function(term){
						switch (type){
							case  that.type.COLUMN_FAMILIS :return localization.tt("msg.enterAlteastOneCharactere");
							case  that.type.TABLE :return localization.tt("msg.enterAlteastOneCharactere");
							case  that.type.COLUMN :return localization.tt("msg.enterAlteastOneCharactere");
							default : return "No Matches found";
						}
					}
				};	
			}
		},
		formValidation : function(){
			var groupSet = false,permSet = false,auditStatus= false,encryptStatus= false,groupPermSet = false,
							userSet=false,userPerm = false,isUsers =false;
			console.log('validation called..');
			var breakFlag =false;
			this.formInputList.each(function(m){
				if(m.has('groupId') ||  m.has('_vPermList')){
					if(! breakFlag){
						groupSet = m.has('groupId') ? true : false ; 
						if(!m.has('_vPermList')){
							permSet = false;
						}else
							permSet = true;
						if(groupSet && permSet)
							groupPermSet = true;
						else
							breakFlag=true;
					}
				}
			});
			breakFlag = false;
			this.userPermInputList.each(function(m){
				if(!_.isUndefined(m.attributes)){
					if(! breakFlag){
						userSet = m.has('userId') || m.has('userName') ? true : false ;//|| userSet; 
						if(!m.has('_vPermList')){
							userPerm = false;
						}else
							userPerm = true;
						if(userSet && userPerm)
							isUsers = true;
						else
							breakFlag=true;
					}
				}
			});
			auditStatus = this.fields._vAuditListToggle.editor.getValue();
			//encryptStatus = this.fields.isEncrypt.editor.getValue();
			var auditLoggin = (auditStatus == XAEnums.BooleanValue.BOOL_TRUE.value) ? true : false;
			//var encrypted = (encryptStatus == XAEnums.BooleanValue.BOOL_TRUE.value) ? true : false;
			/*if((groupSet && permSet) || auditLoggin || encrypted )
				return true;*/
			
			return {groupPermSet: groupPermSet , groupSet : groupSet,permSet : permSet,auditLoggin :auditLoggin,/*encrypted : encrypted,*/
				userSet : userSet,userPerm:userPerm,isUsers:isUsers};	
		},
		setUpSwitches :function(){
			var that = this;
			var encryptStatus = false,auditStatus = false,recursiveStatus = false;
			auditStatus = this.model.has('auditList') ? true : false; 
			this.fields._vAuditListToggle.editor.setValue(auditStatus);
			
			/*_.each(_.toArray(XAEnums.BooleanValue),function(m){
				if(parseInt(that.model.get('isEncrypt')) == m.value)
					encryptStatus =  (m.label == XAEnums.BooleanValue.BOOL_TRUE.label) ? true : false;
			});
			this.fields.isEncrypt.editor.setValue(encryptStatus); */
			if(parseInt(this.model.get('resourceStatus')) != XAEnums.BooleanValue.BOOL_TRUE.value)
				this.fields.resourceStatus.editor.setValue(false);
		},
		/** all custom field rendering */
		renderCustomFields: function(){
			var that = this;
			this.groupList = new VXGroupList();
			var params = {sortBy : 'name', cache :false};
			this.groupList.setPageSize(100,{fetch:false});
			this.groupList.fetch({
				cache :true,
				data : params
				}).done(function(){
					that.$('[data-customfields="groupPerms"]').html(new FormInputItemList({
						collection 	: that.formInputList,
						groupList  	: that.groupList,
						model 		: that.model,
						policyType 	: XAEnums.AssetType.ASSET_HBASE.value
					
					}).render().el);
			});	
			
			this.userList = new VXUserList();
			var params = {sortBy : 'name', cache :false};
			this.userList.setPageSize(100,{fetch:false});
			this.userList.fetch({
				cache :true,
				data : params
				}).done(function(){
					that.$('[data-customfields="userPerms"]').html(new UserPermissionList({
						collection 	: that.userPermInputList,
						model 		: that.model,
						userList 	: that.userList,
						policyType 	: XAEnums.AssetType.ASSET_HBASE.value
					}).render().el);
			});
		},
		afterCommit : function(){
			var that = this;
			//TODO FIXME remove the hard coding 
			//this.model.set('assetId', XAGlobals.hardcoded.HiveAssetId);
			this.model.set('assetId', this.assetModel.id);
			var permMapList = new VXPermMapList();
			this.formInputList.each(function(m){
				if(!_.isUndefined(m.get('groupId'))){
					var uniqueID = _.uniqueId(new Date()+':');
					_.each(m.get('groupId').split(","),function(groupId){
						_.each(m.get('_vPermList'), function(perm){
							var params = {
									//groupId: m.get('groupId'),
									groupId: groupId,
									permFor : XAEnums.XAPermForType.XA_PERM_FOR_GROUP.value,
									permType : perm.permType,
									permGroup : uniqueID
							};
							if(parseInt(groupId) == perm.groupId)
								params = $.extend(params, {id : perm.id});
							//TODO FIXME remove the hardcoding
							permMapList.add(new VXPermMap(params));
						}, this);
					});
				}
			}, this);
			
			this.userPermInputList.each(function(m){
				if(!_.isUndefined(m.get('userId'))){
					var uniqueID = _.uniqueId(new Date()+':');
					_.each(m.get('userId').split(","),function(userId){
						_.each(m.get('_vPermList'), function(perm){
							var params = {
									//groupId: m.get('groupId'),
									userId: userId,
									permFor : XAEnums.XAPermForType.XA_PERM_FOR_USER.value,
									permType : perm.permType,
									permGroup : uniqueID
							};
							if(parseInt(userId) == perm.userId)
								params = $.extend(params, {id : perm.id});
							//TODO FIXME remove the hardcoding
							permMapList.add(new VXPermMap(params));
						}, this);
					});
				}
			}, this);
			
			this.model.set('permMapList', permMapList);
			var columns = !_.isEmpty(this.model.get('columns'));
			var columnFamilies = !_.isEmpty(this.model.get('columnFamilies'));
			
			if(columns)
				this.model.set('resourceType',XAEnums.ResourceType.RESOURCE_COLUMN.value);
			else if(columnFamilies)	
				this.model.set('resourceType',XAEnums.ResourceType.RESOURCE_COL_FAM.value);
			else
				this.model.set('resourceType',XAEnums.ResourceType.RESOURCE_TABLE.value);
			
			//TODO Already handled by server side so we need to remove following line 
			if(_.isEmpty(this.model.get('columnFamilies')))	{
				this.model.unset('columnFamilies');
				this.model.unset('columns'); 
			}
				
			if(_.isEmpty(this.model.get('columns')))	this.model.unset('columns');
			if(this.model.get('resourceStatus') != XAEnums.BooleanValue.BOOL_TRUE.value){
				this.model.set('resourceStatus', XAEnums.ActiveStatus.STATUS_DISABLED.value);
			}
			
			
		},
		checkMultiselectDirtyField : function(e, type){
			var elem = $(e.currentTarget),columnName='',newNameList = [], nameList = [];
			switch(type){
				case 2 :columnName = 'tables';break;
				case 4 :columnName = 'columnFamilies';break;
			}
			if(!_.isUndefined(this.model.get(columnName)) && !_.isEqual(this.model.get(columnName),""))
				nameList = this.model.get(columnName).split(',');
			if(!_.isEqual(e.currentTarget.value, ""))
				newNameList = e.currentTarget.value.split(',');
			XAUtil.checkDirtyField(nameList, newNameList, elem);
		},
		/* all post render plugin initialization */
		initializePlugins: function(){
		}

	});

	return HbasePolicyForm;
});
