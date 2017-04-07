package cn.gdeng.market.dto.lease.settings;

import cn.gdeng.market.entity.lease.settings.MarketImageSettingEntity;

public class MarketImageSettingDTO extends MarketImageSettingEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8656830964974697679L;
	private int startNum;
	private int endNum;
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public int getEndNum() {
		return endNum;
	}
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
	
}
