package cn.gdeng.market.dto.lease.settings;

import cn.gdeng.market.entity.lease.settings.MarketMeasureTypeEntity;

public class MarketMeasureTypeDTO extends MarketMeasureTypeEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8656830964974698619L;
	
	/**
	 * 相关费相
	 */
	private String expName;
	
	/**
	 * 是否系统级
	 */
	private String sysTypeStr;

	public String getExpName() {
		return expName;
	}

	public void setExpName(String expName) {
		this.expName = expName;
	}

	public String getSysTypeStr() {
		return sysTypeStr;
	}

	public void setSysTypeStr(String sysTypeStr) {
		this.sysTypeStr = sysTypeStr;
	}
	
	
}
