package cn.gdeng.market.dto.lease.contract;

import java.io.Serializable;

import cn.gdeng.market.entity.lease.contract.ContractOthersFeeEntity;

public class ContractOthersFeeDTO extends ContractOthersFeeEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 777497886527071746L;
	/**
	 * 计费单位名称
	 */
	private String chargeUnitName;
	
	/**
	 * 收租模式名称
	 */
	private String rentModeName;

	public String getChargeUnitName() {
		return chargeUnitName;
	}

	public void setChargeUnitName(String chargeUnitName) {
		this.chargeUnitName = chargeUnitName;
	}

	public String getRentModeName() {
		return rentModeName;
	}

	public void setRentModeName(String rentModeName) {
		this.rentModeName = rentModeName;
	}
	
}
