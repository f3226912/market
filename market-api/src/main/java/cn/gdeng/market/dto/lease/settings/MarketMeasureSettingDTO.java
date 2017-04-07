package cn.gdeng.market.dto.lease.settings;

import cn.gdeng.market.entity.lease.settings.MarketMeasureSettingEntity;

public class MarketMeasureSettingDTO extends MarketMeasureSettingEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8656830964974698619L;
	private int startNum;
	private int endNum;
	private String statusStr;
	private String prefix;
	/**
	 * 计量分类名称
	 */
	private String measureTypeName;

	/**
	 * 计费标准名称
	 */
	private String expName;

	/**
	 * 资源名称
	 */
	private String resourceName;
	
	/**
	 * 资源租赁状态
	 * 
	 */
	private Integer leaseStatus;
	
	public Integer getLeaseStatus() {
		return leaseStatus;
	}
	public void setLeaseStatus(Integer leaseStatus) {
		this.leaseStatus = leaseStatus;
	}
	
	public String getMeasureTypeName() {
		return measureTypeName;
	}
	public void setMeasureTypeName(String measureTypeName) {
		this.measureTypeName = measureTypeName;
	}
	
	public String getExpName() {
		return expName;
	}
	public void setExpName(String expName) {
		this.expName = expName;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
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
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	
}
