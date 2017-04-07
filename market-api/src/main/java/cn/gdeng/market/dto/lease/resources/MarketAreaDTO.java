package cn.gdeng.market.dto.lease.resources;

import java.util.ArrayList;
import java.util.List;

import cn.gdeng.market.entity.lease.resources.MarketAreaEntity;

public class MarketAreaDTO  extends MarketAreaEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8986621122000117891L;
	
	private String areaId; //用于页面传参数,区别于id
	
	private String coordinate; //区域坐标
	/**
	 * 当前区域中的所有楼栋
	 * 
	 * */
	private List<MarketAreaBuildingDTO> buildings=new ArrayList<MarketAreaBuildingDTO>();

	public List<MarketAreaBuildingDTO> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<MarketAreaBuildingDTO> buildings) {
		this.buildings = buildings;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	
	
}
