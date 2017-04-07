package cn.gdeng.market.dto.lease.resources;

import java.io.Serializable;

/**
 * 租赁资源（打印）
 * @author dengjianfeng
 *
 */
public class MarketResourcePrintDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1292550107608536591L;

	private Integer id;
	
	private String areaName;
	
	private String buildingName;
	
	private String unitName;
	
	private String floorName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
	
	
}
