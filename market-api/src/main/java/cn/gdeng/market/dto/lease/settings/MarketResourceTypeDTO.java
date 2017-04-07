package cn.gdeng.market.dto.lease.settings;

import cn.gdeng.market.entity.lease.settings.MarketResourceTypeEntity;

public class MarketResourceTypeDTO extends MarketResourceTypeEntity{

	private static final long serialVersionUID = 3898094805207394582L;
	
	private String sysTypeStr;
	
	/**
	 * 资源类型id
	 */
	private String resourceTypeId ;

	/**
	 * 资源id
	 */
	private String resourceId ;
	
	public String getResourceTypeId() {
		return resourceTypeId;
	}

	public void setResourceTypeId(String resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getSysTypeStr() {
		return sysTypeStr;
	}

	public void setSysTypeStr(String sysTypeStr) {
		this.sysTypeStr = sysTypeStr;
	}

}
