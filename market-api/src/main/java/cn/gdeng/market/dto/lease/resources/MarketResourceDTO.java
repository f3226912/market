package cn.gdeng.market.dto.lease.resources;

import java.util.List;

import cn.gdeng.market.entity.lease.resources.MarketResourceEntity;

public class MarketResourceDTO extends MarketResourceEntity {

	private static final long serialVersionUID = -8986621122000117891L;
	private String areaName; // 区域名称
	private String buildingName;// 楼栋名称
	private String leaseStatusName; // '租赁状态: 默认 1 待放租 2 待租 3 已租
	private String buildingUnitName;//楼栋单元名
	private String unitFloorName;//单元楼层名
	private List<MarketResourceMeasureUnionDTO> resourceMeasureUnions;// 物业信息
	private Integer  parentId;
	private String coordinate; //坐标
	private String resourceTypeName;  //资源类型名称

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getLeaseStatusName() {
		return leaseStatusName;
	}

	public void setLeaseStatusName(String leaseStatusName) {
		this.leaseStatusName = leaseStatusName;
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

	public List<MarketResourceMeasureUnionDTO> getResourceMeasureUnions() {
		return resourceMeasureUnions;
	}

	public void setResourceMeasureUnions(List<MarketResourceMeasureUnionDTO> resourceMeasureUnions) {
		this.resourceMeasureUnions = resourceMeasureUnions;
	}

	public String getResourceTypeName() {
		return resourceTypeName;
	}

	public void setResourceTypeName(String resourceTypeName) {
		this.resourceTypeName = resourceTypeName;
	}

	public String getBuildingUnitName() {
		return buildingUnitName;
	}

	public void setBuildingUnitName(String buildingUnitName) {
		this.buildingUnitName = buildingUnitName;
	}

	public String getUnitFloorName() {
		return unitFloorName;
	}

	public void setUnitFloorName(String unitFloorName) {
		this.unitFloorName = unitFloorName;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	
	
}
