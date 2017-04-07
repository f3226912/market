package cn.gdeng.market.dto.lease.resources;

import cn.gdeng.market.entity.lease.resources.MarketAreaBuildingEntity;

/**
 * 楼栋管理
 * @author qrxu
 *
 */
public class MarketAreaBuildingDTO extends MarketAreaBuildingEntity {
	private static final long serialVersionUID = -8986621122000117891L;
	
	/**
	 * 市场ID
	 */
	private Integer marketId;
	/**
	 * 市场编码
	 */
	private String marketCode;


	/**
	 * 区域名称
	 */
	private String areaName;

	/**
	 * 市场区域
	 */
	private String areaCode;


	/**
	 * 楼栋性质
	 */

	private String builNature;
	/**
	 * 楼栋性质数字字典
	 */
	private String  value;
	private String remark;
	
	private String coordinate;
	
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public String getMarketCode() {
		return marketCode;
	}

	public void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}


	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getBuilNature() {
		return builNature;
	}

	public void setBuilNature(String builNature) {
		this.builNature = builNature;
	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	
    
}
