DELIMITER $$


DROP PROCEDURE IF EXISTS `bi_feetake_info`$$


CREATE PROCEDURE `bi_feetake_info`() 
BEGIN
  DECLARE marketId_ INT(11) DEFAULT '0' ;
  DECLARE areaId_ INT(11)  DEFAULT '0' ;
  DECLARE expId_ CHAR(10) DEFAULT '0' ; 
  DECLARE resourceTypeId_ VARCHAR(50) ; 
  DECLARE accountPayable_ DECIMAL(18,2)  DEFAULT '0.00' ;
  DECLARE accountPayed_ DECIMAL(18,2)  DEFAULT '0.00' ;
  DECLARE discountAmount_ DECIMAL(18,2)  DEFAULT '0.00' ;
  DECLARE dataDate_ INT;
  DECLARE endDay_ DATE;
    DECLARE targetDay_ DATE ;
    DECLARE targetDayStart_ DATE;
     DECLARE targetDayEnd_ DATE;
  DECLARE currentMarketId_ INT (11) DEFAULT NULL ;-- 市场id, 分别统计市场每个市场数据
  -- 遍历数据结束标志
  DECLARE market_done INT DEFAULT FALSE ;-- 声明游标
  DECLARE cur_market CURSOR FOR 
  (SELECT 
    m.id 
  FROM
    `market` m 
  WHERE m.status=1 
  ORDER BY m.id ASC) ;-- 将结束标志绑定到游标
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET market_done = TRUE ;-- 打开游标
  OPEN cur_market;
  market_loop:
  LOOP
    -- 提取游标里的数据
    FETCH cur_market INTO currentMarketId_ ;
    -- 外层循环结束 , 即外层游标结束
    IF market_done 
    THEN LEAVE market_loop ;END IF ;
    -- 当前市场基础表最大日期
  
    SELECT DATE_ADD(CURDATE(),INTERVAL -DAY(CURDATE())+1 DAY) INTO endDay_;
    
    -- 数据源表最大日期
      SELECT 
      DATE_FORMAT(MAX(dataDate), '%Y-%m-%d')  INTO targetDay_ 
    FROM
      bi_feetake_info AS bfi 
    WHERE marketId = currentMarketId_ ;
    
    IF targetDay_ IS NULL 
    THEN 
    SELECT  DATE_ADD(MIN(createTime),INTERVAL - 1 MONTH)  INTO targetDay_ 
    FROM contract_main AS cm 
      WHERE marketId = currentMarketId_ ;
      SELECT DATE_ADD(targetDay_,INTERVAL -DAY(targetDay_)+1 DAY) INTO targetDayStart_; 
    END IF ;
    WHILE TO_DAYS(targetDay_) <= TO_DAYS(endDay_) DO 
      SET targetDay_ := DATE_ADD(targetDay_, INTERVAL 1 MONTH) ;
      
      -- 当前月第一天
      SELECT DATE_ADD(targetDay_,INTERVAL -DAY(targetDay_)+1 DAY) INTO targetDayStart_; 
      -- 下月第一天
       SELECT DATE_ADD(targetDay_-DAY(targetDay_)+1,INTERVAL 1 MONTH) INTO targetDayEnd_;
      BEGIN
        -- 遍历数据结束标志
        DECLARE done INT DEFAULT FALSE ;-- 声明游标  targetDayStart_  targetDayEnd_ currentMarketId_
        DECLARE cur_1 CURSOR FOR 
        (
	SELECT tb1.marketId,tb1.resourceTypeId,tb1.areaId,tb1.expId,tb1.accountPayable,tb2.accountPayed,tb1.accountRebate FROM (
SELECT t.marketId,t.resourceTypeId,t.areaId,cof.feeItemId AS expId,TRUNCATE(IFNULL(SUM(cof.accountPayable)/t.len,0),2)accountPayable,IFNULL(SUM(cof.accountRebate),0)accountRebate FROM (
SELECT t.id,t.marketId,t.marketResourceTypeId AS resourceTypeId,mr.areaId,t.contractNo,t.len  FROM (
SELECT  cm.`id`,cm.marketId,cm.marketResourceTypeId,cm.contractNo,(LENGTH(cm.leasingResourceIds) - LENGTH(REPLACE(cm.leasingResourceIds,',',''))+1) AS len,SUBSTRING_INDEX(SUBSTRING_INDEX(cm.leasingResourceIds,',',ht.help_topic_id+1),',',-1)resourceId
FROM 
contract_main cm
LEFT JOIN
bi_help_topic ht
ON ht.help_topic_id < (LENGTH(cm.leasingResourceIds) - LENGTH(REPLACE(cm.leasingResourceIds,',',''))+1) #拆分合同
WHERE cm.isCancel=0 AND cm.isDeleted=0 AND (cm.contractStatus=1 OR cm.contractStatus=2)   AND cm.marketId=currentMarketId_
) AS t 
LEFT JOIN market_resource AS mr ON mr.id=t.resourceId
GROUP BY t.id,t.marketId,mr.areaId,t.marketResourceTypeId
) AS t
LEFT JOIN  finance_fee_record  AS cof
ON cof.contractNo=t.contractNo 
WHERE  cof.isRemedy=1 AND cof.isDeteled=0 AND cof.timeLimit >=targetDayStart_ AND  cof.timeLimit < targetDayEnd_ #应收款 
AND cof.feeItemId IN (SELECT id FROM market_expenditure WHERE parentId=1)
GROUP BY t.marketId,t.areaId,t.resourceTypeId,cof.feeItemId
) AS tb1 LEFT JOIN (
SELECT t.marketId,t.resourceTypeId,t.areaId,ffr.feeItemId AS expId,TRUNCATE(IFNULL(SUM(ffr.accountPayed)/t.len,0),2)accountPayed FROM (
SELECT t.id,t.marketId,t.marketResourceTypeId AS resourceTypeId,mr.areaId,t.contractNo,t.len  FROM (
SELECT  cm.`id`,cm.marketId,cm.marketResourceTypeId,cm.contractNo,(LENGTH(cm.leasingResourceIds) - LENGTH(REPLACE(cm.leasingResourceIds,',',''))+1) AS len,SUBSTRING_INDEX(SUBSTRING_INDEX(cm.leasingResourceIds,',',ht.help_topic_id+1),',',-1)resourceId
FROM 
contract_main cm
LEFT JOIN
bi_help_topic ht
ON ht.help_topic_id < (LENGTH(cm.leasingResourceIds) - LENGTH(REPLACE(cm.leasingResourceIds,',',''))+1) #拆分合同
WHERE cm.isCancel=0 AND cm.isDeleted=0 AND (cm.contractStatus=1 OR cm.contractStatus=2)   AND cm.marketId=currentMarketId_
) AS t 
LEFT JOIN market_resource AS mr ON mr.id=t.resourceId
GROUP BY t.id,t.marketId,mr.areaId,t.marketResourceTypeId
) AS t
LEFT JOIN  finance_fee_received  AS ffr
ON ffr.contractNo=t.contractNo 
WHERE ffr.timeLimit >=targetDayStart_ AND  ffr.timeLimit < targetDayEnd_ #已收款
AND ffr.feeItemId IN (SELECT id FROM market_expenditure WHERE parentId=1)
GROUP BY t.marketId,t.areaId,t.resourceTypeId,ffr.feeItemId
)AS tb2 ON tb1.marketId=tb2.marketId AND  tb1.resourceTypeId=tb2.resourceTypeId AND tb1.areaId=tb2.areaId AND tb1.expId=tb2.expId
	) ;-- 将结束标志绑定到游标
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE ;-- 打开游标
        OPEN cur_1 ;
        read_loop:
        LOOP
          -- 提取游标里的数据
          FETCH cur_1 INTO 
          marketId_,
          resourceTypeId_,
          areaId_,
          expId_,
          accountPayable_,
          accountPayed_,
          discountAmount_;
          IF done 
          THEN LEAVE read_loop ;
          END IF ;
          INSERT INTO bi_feetake_info (
            marketId,
            areaId,
            expid,
            resourceTypeId,
            accountPayable,
            accountPayed,
            discountAmount,
            dataDate
          ) 
          VALUES
            (
              marketId_,
              areaId_,
              expId_,
              resourceTypeId_,
              accountPayable_,
              accountPayed_,
              discountAmount_,
              DATE_FORMAT(targetDayStart_,'%Y%m%d')
            ) ;
        END LOOP read_loop ;-- 关闭游标
        CLOSE cur_1 ;
      END ;
    END WHILE ;-- 外层游标
  END LOOP market_loop ;-- 关闭外层(市场)游标
  CLOSE cur_market ;
END 
