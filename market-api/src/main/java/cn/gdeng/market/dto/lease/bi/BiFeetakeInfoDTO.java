package cn.gdeng.market.dto.lease.bi;

import cn.gdeng.market.entity.lease.bi.BiFeetakeInfoEntity;

public class BiFeetakeInfoDTO extends BiFeetakeInfoEntity {

	private static final long serialVersionUID = -663581818787906443L;

	/**
	 * 未收款
	 */
	private String accountDidPay;
	
	/**
	 * 区域名称
	 */
	private String areaName;
	/**
	 * 占比
	 */
	private String rate;
	/**
	 * 收缴率
	 */
	private String captureRate;
	public String getAccountDidPay() {
		return accountDidPay;
	}
	public void setAccountDidPay(String accountDidPay) {
		this.accountDidPay = accountDidPay;
	}
	public String getCaptureRate() {
		return captureRate;
	}
	public void setCaptureRate(String captureRate) {
		this.captureRate = captureRate;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	
}
