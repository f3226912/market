package cn.gdeng.market.dto.lease.resources;

import java.io.Serializable;

/**
 * 处理批量新增楼栋资源参数
 * @author qrxu
 *
 */
public class MarketResourceParam implements Serializable {
	private static final long serialVersionUID = -1861647226385165612L;
	/**
	 * 资源（户数）
	 */
	private String resource;
	/**
	 * 楼层
	 */
	private	String buildings;
	/**
	 * 单元
	 */
	private String units; 
	/**
	 * 楼栋ID
	 */
	private String builId;
	/**
	 * 楼栋名称
	 */
	private String builName;
	/**
	 * 楼栋编码
	 */
	private String builCode;
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getBuildings() {
		return buildings;
	}
	public void setBuildings(String buildings) {
		this.buildings = buildings;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}

	public String getBuilId() {
		return builId;
	}
	public void setBuilId(String builId) {
		this.builId = builId;
	}
	public String getBuilName() {
		return builName;
	}
	public void setBuilName(String builName) {
		this.builName = builName;
	}
	public String getBuilCode() {
		return builCode;
	}
	public void setBuilCode(String builCode) {
		this.builCode = builCode;
	}
	
//	/**
//	 * 楼层
//	 */
//    private List<MarketUnitFloorDTO> floors ;
//    /**
//	 * 单元
//	 */
//	private List<MarketBuildingUnitDTO> units;
//	/**
//	 * 资源
//	 */
//	private List<MarketResourceDTO> resources;
//	public List<MarketUnitFloorDTO> getFloors() {
//		return floors;
//	}
//	public void setFloors(List<MarketUnitFloorDTO> floors) {
//		this.floors = floors;
//	}
//	public List<MarketBuildingUnitDTO> getUnits() {
//		return units;
//	}
//	public void setUnits(List<MarketBuildingUnitDTO> units) {
//		this.units = units;
//	}
//	public List<MarketResourceDTO> getResources() {
//		return resources;
//	}
//	public void setResources(List<MarketResourceDTO> resources) {
//		this.resources = resources;
//	}

}
