package cn.gdeng.market.dto.lease.bi;

import cn.gdeng.market.entity.lease.bi.BiKeyIndexEntity;

public class BiKeyIndexDTO extends BiKeyIndexEntity {

	private static final long serialVersionUID = -5583132641687002386L;

	/**
	 * 期间出租率
	 */
	private String periodRate;
	
	/**
	 * 累积租金收缴率
	 */
	private String accountRate;
	
	/**
	 * 当月累积租金收缴率
	 */
	private String accountMonRate;

	public String getPeriodRate() {
		return periodRate;
	}

	public void setPeriodRate(String periodRate) {
		this.periodRate = periodRate;
	}

	public String getAccountRate() {
		return accountRate;
	}

	public void setAccountRate(String accountRate) {
		this.accountRate = accountRate;
	}

	public String getAccountMonRate() {
		return accountMonRate;
	}

	public void setAccountMonRate(String accountMonRate) {
		this.accountMonRate = accountMonRate;
	}
	
}
