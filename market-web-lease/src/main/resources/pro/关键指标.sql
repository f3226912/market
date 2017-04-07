DELIMITER $$


DROP PROCEDURE IF EXISTS `bi_pro_key_index`$$

CREATE  PROCEDURE `bi_pro_key_index`()
-- 关键指标

BEGIN
	-- 数据时间
	DECLARE datatimes_ DATETIME DEFAULT SYSDATE();
	-- 待租面积
	DECLARE stayArea_ DECIMAL(18,2) DEFAULT 0.00;
	-- 已租面积
	DECLARE alreadyArea_ DECIMAL(18,2) DEFAULT 0.00;
	-- 未放租面积
	DECLARE unrentableArea_ DECIMAL(18,2) DEFAULT 0.00;
	-- 实收金额
	DECLARE accountPayed_ DECIMAL(18,2) DEFAULT 0.00;
	-- 应收金额
	DECLARE accountPayable_ DECIMAL(18,2) DEFAULT 0.00;
	-- 当月实收金额
	DECLARE accountPayedMon_ DECIMAL(18,2) DEFAULT 0.00;
	-- 当月应收金额
	DECLARE accountPayableMon_ DECIMAL(18,2) DEFAULT 0.00;
	
	-- 可租面积
	DECLARE ettableArea_ DECIMAL(18,2) DEFAULT 0.00;
	
	-- 资源类型
	DECLARE resourceTypeId_ INT(11);
	-- 市场
	DECLARE marketId_ INT(11);
	
	DECLARE keydataTime_ BIGINT(12);
	
	
	BEGIN
	 -- 遍历数据结束标志
	DECLARE done INT DEFAULT FALSE;
	-- 声明游标
	DECLARE cur_1 CURSOR FOR   
	(
	SELECT t.marketId,t.resourceTypeId,
IFNULL(t1.leaseArea,0) AS stayArea,
IFNULL(t7.countArea,0) AS alreadyArea,
IFNULL(t2.unrentableArea,0) AS unrentableArea,
IFNULL(t3.accountPayable,0)accountPayable,
IFNULL(t6.accountPayed,0)accountPayed,
IFNULL(t4.accountPayable,0)accountPayableMon,
IFNULL(t5.accountPayed,0)accountPayedMon FROM (
SELECT mr.marketId,mr.resourceTypeId FROM `market_resource` AS mr 
WHERE leaseStatus IN ('1','2','3')
GROUP BY mr.marketId,mr.resourceTypeId
) AS t 
LEFT JOIN (
-- 待租
SELECT mr.marketId,mr.resourceTypeId,SUM(mr.leaseArea)leaseArea FROM `market_resource` AS mr 
WHERE mr.leaseStatus = 2
GROUP BY mr.marketId,mr.resourceTypeId
) AS t1 ON t.marketId=t1.marketId AND t.resourceTypeId=t1.resourceTypeId
LEFT JOIN (
-- 未放租
SELECT  mr.marketId,mr.resourceTypeId,SUM(mr.leaseArea)unrentableArea  FROM `market_resource` AS mr 
WHERE mr.leaseStatus = 1
GROUP BY mr.marketId,mr.resourceTypeId 
) AS t2 ON t.marketId=t2.marketId AND t.resourceTypeId=t2.resourceTypeId
LEFT JOIN (
-- 总应收款 ，
SELECT t.marketId,t.resourceTypeId,SUM(ffd.accountPayable)accountPayable FROM (
SELECT t.id,t.marketId,t.marketResourceTypeId AS resourceTypeId,t.contractNo FROM (
SELECT  cm.id,cm.marketId,cm.marketResourceTypeId,cm.contractNo,SUBSTRING_INDEX(SUBSTRING_INDEX(cm.leasingResourceIds,',',ht.help_topic_id+1),',',-1)resourceId
FROM 
contract_main cm
LEFT JOIN
bi_help_topic ht
ON ht.help_topic_id < (LENGTH(cm.leasingResourceIds) - LENGTH(REPLACE(cm.leasingResourceIds,',',''))+1)
WHERE cm.isCancel=0 AND cm.isDeleted=0 AND (cm.contractStatus=1 OR cm.contractStatus=2)
) AS t
GROUP BY t.marketId,t.id,t.marketResourceTypeId
) AS t
LEFT JOIN finance_fee_record AS  ffd 
ON  t.contractNo=ffd.contractNo 
WHERE  ffd.isRemedy=1  AND ffd.isDeteled=0 
AND ffd.feeItemId IN (SELECT id FROM market_expenditure WHERE parentId=1)
AND  ffd.timeLimit < '2016-12-01'-- DATE_ADD(CURDATE(),INTERVAL -DAY(CURDATE())+1 DAY)
GROUP BY t.marketId,t.resourceTypeId
) AS t3 ON t.marketId=t3.marketId AND  t.resourceTypeId=t3.resourceTypeId
LEFT JOIN (
-- 当月应收，
SELECT t.marketId,t.resourceTypeId,SUM(ffd.accountPayable)accountPayable FROM (
SELECT t.id,t.marketId,t.marketResourceTypeId AS resourceTypeId,t.contractNo FROM (
SELECT  cm.id,cm.marketId,cm.marketResourceTypeId,cm.contractNo,SUBSTRING_INDEX(SUBSTRING_INDEX(cm.leasingResourceIds,',',ht.help_topic_id+1),',',-1)resourceId
FROM 
contract_main cm
LEFT JOIN
bi_help_topic ht
ON ht.help_topic_id < (LENGTH(cm.leasingResourceIds) - LENGTH(REPLACE(cm.leasingResourceIds,',',''))+1)
WHERE cm.isCancel=0 AND cm.isDeleted=0 AND (cm.contractStatus=1 OR cm.contractStatus=2)
) AS t
GROUP BY t.marketId,t.id,t.marketResourceTypeId
) AS t
LEFT JOIN finance_fee_record AS  ffd 
ON  t.contractNo=ffd.contractNo 
WHERE  ffd.isRemedy=1  AND ffd.isDeteled=0 
AND ffd.feeItemId IN (SELECT id FROM market_expenditure WHERE parentId=1)
AND DATE_FORMAT(ffd.timeLimit,'%Y%m')='201611' -- DATE_FORMAT(DATE_ADD(CURDATE(),INTERVAL -1 MONTH),'%Y%m')
GROUP BY t.marketId,t.resourceTypeId
) AS t4 ON  t.marketId=t4.marketId AND  t.resourceTypeId=t4.resourceTypeId
LEFT JOIN (
-- 当月实收，
SELECT t.marketId,t.resourceTypeId,SUM(ffr.accountPayed) accountPayed FROM (
SELECT t.id,t.contractNo,t.marketId,t.marketResourceTypeId AS resourceTypeId FROM (
SELECT  cm.id,cm.marketId,cm.marketResourceTypeId,cm.contractNo,SUBSTRING_INDEX(SUBSTRING_INDEX(cm.leasingResourceIds,',',ht.help_topic_id+1),',',-1)resourceId
FROM 
contract_main cm
LEFT JOIN
bi_help_topic ht
ON ht.help_topic_id < (LENGTH(cm.leasingResourceIds) - LENGTH(REPLACE(cm.leasingResourceIds,',',''))+1)
WHERE cm.isCancel=0 AND cm.isDeleted=0 AND (cm.contractStatus=1 OR cm.contractStatus=2)
) AS t
GROUP BY t.marketId,t.id,t.marketResourceTypeId
) AS t
LEFT JOIN finance_fee_received AS  ffr
ON  t.contractNo=ffr.contractNo 
WHERE  ffr.fundType=0 
AND ffr.feeItemId IN (SELECT id FROM market_expenditure WHERE parentId=1)
AND DATE_FORMAT(ffr.timeLimit,'%Y%m')= '201611' -- DATE_FORMAT(DATE_ADD(CURDATE(),INTERVAL -1 MONTH),'%Y%m')
GROUP BY t.marketId,t.resourceTypeId
)AS t5 ON  t.marketId=t5.marketId AND  t.resourceTypeId=t5.resourceTypeId
LEFT JOIN (
-- 总已收款
SELECT t.marketId,t.resourceTypeId,SUM(ffr.accountPayed)accountPayed FROM (
SELECT t.id,t.marketId,t.marketResourceTypeId AS resourceTypeId,t.contractNo FROM (
SELECT  cm.id,cm.marketId,cm.marketResourceTypeId,cm.contractNo,SUBSTRING_INDEX(SUBSTRING_INDEX(cm.leasingResourceIds,',',ht.help_topic_id+1),',',-1)resourceId
FROM 
contract_main cm
LEFT JOIN
bi_help_topic ht
ON ht.help_topic_id < (LENGTH(cm.leasingResourceIds) - LENGTH(REPLACE(cm.leasingResourceIds,',',''))+1)
WHERE cm.isCancel=0 AND cm.isDeleted=0 AND (cm.contractStatus=1 OR cm.contractStatus=2)
) AS t
GROUP BY t.marketId,t.id,t.marketResourceTypeId
) AS t
LEFT JOIN finance_fee_received AS  ffr
ON  t.contractNo=ffr.contractNo 
WHERE  ffr.fundType=0 
AND ffr.feeItemId IN (SELECT id FROM market_expenditure WHERE parentId=1)
AND ffr.timeLimit<'2016-12-01' -- DATE_ADD(CURDATE(),INTERVAL -DAY(CURDATE())+1 DAY)
GROUP BY t.marketId,t.resourceTypeId
) AS t6 ON  t.marketId=t6.marketId AND  t.resourceTypeId=t6.resourceTypeId
LEFT JOIN (
-- 已租面积
SELECT t.marketId,t.resourceTypeId,SUM(t.countArea)countArea FROM (
SELECT t.id,t.marketId,t.marketResourceTypeId AS resourceTypeId,t.countArea FROM (
SELECT  cm.id,cm.marketId,cm.marketResourceTypeId,cm.contractNo,SUBSTRING_INDEX(SUBSTRING_INDEX(cm.leasingResourceIds,',',ht.help_topic_id+1),',',-1)resourceId,
TRUNCATE(cm.countArea/(LENGTH(cm.leasingResourceIds) - LENGTH(REPLACE(cm.leasingResourceIds,',',''))+1),2)countArea
FROM 
contract_main cm
LEFT JOIN
bi_help_topic ht
ON ht.help_topic_id < (LENGTH(cm.leasingResourceIds) - LENGTH(REPLACE(cm.leasingResourceIds,',',''))+1)
WHERE cm.isCancel=0 AND cm.isDeleted=0 AND (cm.contractStatus=1 OR cm.contractStatus=2)
) AS t
) AS t
GROUP BY t.marketId,t.resourceTypeId
)AS t7 ON  t.marketId=t7.marketId AND  t.resourceTypeId=t7.resourceTypeId
WHERE t.resourceTypeId  IS NOT NULL
	);
	
	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	-- 打开游标
	OPEN cur_1;
	
	read_loop: LOOP	
	-- 提取游标里的数据
	FETCH cur_1 INTO marketId_,resourceTypeId_,stayArea_,alreadyArea_,unrentableArea_,accountPayable_,accountPayed_,accountPayableMon_,accountPayedMon_;
	IF done THEN
		LEAVE read_loop;
	END IF;
	
	INSERT INTO bi_key_index(`stayArea`,`alreadyArea`,`unrentableArea`,`accountPayed`,accountPayable,`accountPayedMon`,accountPayableMon,`resourceTypeId`,`keydataTime`,`marketId`)
	VALUE (stayArea_,alreadyArea_,unrentableArea_,accountPayed_,accountPayable_,accountPayedMon_,accountPayableMon_,resourceTypeId_,DATE_FORMAT(datatimes_,'%Y%m%d%H%i%S'),marketId_);
	END LOOP read_loop;  
	  -- 关闭游标
	CLOSE cur_1;
	
		    	
    END;
    END$$

DELIMITER ;