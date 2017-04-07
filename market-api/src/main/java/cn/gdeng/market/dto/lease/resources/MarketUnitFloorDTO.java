package cn.gdeng.market.dto.lease.resources;

import cn.gdeng.market.entity.lease.resources.MarketUnitFloorEntity;

/**
 * 楼层管理
 * 
 * @author hbd
 *
 */
public class MarketUnitFloorDTO extends MarketUnitFloorEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1861647226385165612L;
	
	private String floorId; //楼层ID 上传平面图参数  区别ID

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}
	
	

}
