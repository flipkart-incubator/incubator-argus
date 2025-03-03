-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

DROP FUNCTION if exists removeTrailingSlash $$
CREATE FUNCTION `removeTrailingSlash`(resName varchar(4000), resId bigint, assetId bigint, policyName varchar(1000)) RETURNS varchar(4000) CHARSET latin1
BEGIN

DECLARE noOfCommas int;
DECLARE updatedResName varchar(4000) default '';
DECLARE resource varchar(1000);
DECLARE count int default 0;
DECLARE proceedLoop boolean default true;

-- TrxLog fields
DECLARE createTime datetime;
DECLARE addedById bigint;
DECLARE classType int;
DECLARE objId bigint;
DECLARE parentObjId bigint;
DECLARE parentObjClsType int;
DECLARE parentObjName varchar(1024);
DECLARE objName varchar(1024);
DECLARE attrName varchar(255);
DECLARE prevVal varchar(1024);
DECLARE newVal varchar(1024);
DECLARE trxId varchar(1024);
DECLARE act varchar(255);
DECLARE sessType varchar(30);
DECLARE transId varchar(50);
DECLARE assetTypeInt int;
DECLARE assetType varchar(30);

set noOfCommas = (LENGTH(resName)-LENGTH(REPLACE (resName, ",", "")));
set assetTypeInt = (select asset_type from x_asset where id = assetId);

	if (assetTypeInt = 1) then
		set assetType = 'HDFS';
	elseif  (assetTypeInt = 2) then
		set assetType = 'HBase';
	elseif ((assetTypeInt = 3)) then
		set assetType = 'Hive';
	elseif (assetTypeInt = 4) then
		set assetType = 'XAAGENT';
	elseif (assetTypeInt = 5) then
		set assetType = 'Knox';
	elseif (assetTypeInt = 6) then
		set assetType = 'Storm';
	end if;

WHILE proceedLoop DO
	set count = count +1;
	
	if (count > noOfCommas) then
		set resource = SUBSTRING_INDEX(resName, ',', -1);
		set proceedLoop = false;
	else 
		set resource = REPLACE(SUBSTRING(SUBSTRING_INDEX(resName, ',', count), 
						LENGTH(SUBSTRING_INDEX(resName, ',', count -1)) + 1), ',', '');
	end if;

	if (LENGTH(resource) > 1 && SUBSTRING(resource, -(LENGTH(resource)-(LENGTH(resource)-1))) = '/') then 
		set resource = SUBSTRING(resource, 1, (LENGTH(resource)-1));
	else
		set resource = resource;
	end if;

	if(updatedResName != '') then
		set updatedResName = CONCAT(updatedResName, ',', resource);
	else
		set updatedResName = resource;
	end if;
	
END WHILE;

if (updatedResName != resName) then
-- Generating Trx Log if value has been updated.

set createTime = utc_timestamp();
set addedById = 1;
set classType = 1001;
set objId = resId;
set parentObjId = assetId;
set parentObjClsType = 1000;
set objName = updatedResName;
set attrName = 'Resource Path';
set prevVal = resName;
set newVal = updatedResName;
set act = 'update';
set sessType = 'DB Script';

set parentObjName = (select asset_name from x_asset where id = assetId);
set transId = concat(DATE_FORMAT(utc_timestamp(), "%Y%m%d%H%i%s"), '_', rand());

insert into x_trx_log (create_time, update_time, added_by_id, upd_by_id, 
	class_type, object_id, parent_object_id, parent_object_class_type, 
	parent_object_name, object_name, attr_name, prev_val, new_val, `action`, 
	trx_id, sess_type) 
	values(createTime, createTime, addedById, addedById, classType, objId, 
	parentObjId, parentObjClsType, parentObjName, objName, attrName, prevVal, 
	newVal, act, transId, sessType);

insert into x_trx_log (create_time, update_time, added_by_id, upd_by_id, 
	class_type, object_id, parent_object_id, parent_object_class_type, 
	parent_object_name, object_name, attr_name, prev_val, new_val, `action`, 
	trx_id, sess_type) 
	values(createTime, createTime, addedById, addedById, classType, objId, 
	parentObjId, parentObjClsType, parentObjName, objName, 'Policy Name', policyName, 
	policyName, act, transId, sessType);

insert into x_trx_log (create_time, update_time, added_by_id, upd_by_id, 
	class_type, object_id, parent_object_id, parent_object_class_type, 
	parent_object_name, object_name, attr_name, new_val, `action`, 
	trx_id, sess_type) 
	values(createTime, createTime, addedById, addedById, classType, objId, 
	parentObjId, parentObjClsType, parentObjName, objName, 'Repository Type', 
	assetType, act, transId, sessType);

end if;

RETURN updatedResName;
END $$
DELIMITER ;
UPDATE x_resource res, x_asset asset set res_name = removeTrailingSlash(res.res_name, res.id, res.asset_id, res.policy_name) 
	WHERE asset.asset_type = 1 and asset.id = res.asset_id;

DROP FUNCTION if exists removeTrailingSlash;
