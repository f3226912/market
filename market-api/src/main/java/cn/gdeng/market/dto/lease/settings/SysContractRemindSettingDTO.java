package cn.gdeng.market.dto.lease.settings;

import java.util.Date;

import cn.gdeng.market.entity.lease.settings.SysContractRemindSettingEntity;
import cn.gdeng.market.enums.ContractRemindTimeTypeEnum;
import cn.gdeng.market.util.DateUtil;

public class SysContractRemindSettingDTO extends SysContractRemindSettingEntity {

 private static final long serialVersionUID = 1118907073158235492L;
  
  /**
   * 创建人
   */
  private String createUserName;

 /**
   * 到期日期
   */	
  private String endDateSrt;
  
  /**
   * 根据提醒类型计算结束日期
   * @return String
   */
  public String getEndDateSrt() {
	if(ContractRemindTimeTypeEnum.DAY.getCode().equals(getRemindTimeType())){
  		return DateUtil.getDateAfter(new Date(),getRemindTime());
  	}else if(ContractRemindTimeTypeEnum.MONTH.getCode().equals(getRemindTimeType())){
  		return DateUtil.getDateMonth(getRemindTime());
  	}else{
  		return null;
  	}
   }

public void setEndDateSrt(String endDateSrt) {
	this.endDateSrt = endDateSrt;
}

public String getCreateUserName() {
	return createUserName;
}

public void setCreateUserName(String createUserName) {
	this.createUserName = createUserName;
}
  

}
