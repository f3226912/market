package cn.gdeng.market.dto.lease.resources;

import cn.gdeng.market.entity.lease.resources.MarketSectionalChargeEntity;

public class MarketSectionalChargeDTO extends MarketSectionalChargeEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8603817036678502190L;
	/**
	 * 最小单价
	 */
	private Double minPrice;
	public Double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}
	
	public double getResult(){
		return Math.ceil((this.getUpValue()-this.getDownValue())*this.getChargeUnitPrice()*100)/100;
	}
}
