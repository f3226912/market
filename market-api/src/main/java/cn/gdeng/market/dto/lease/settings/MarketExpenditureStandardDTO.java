package cn.gdeng.market.dto.lease.settings;

import java.util.List;

import cn.gdeng.market.entity.lease.resources.MarketSectionalChargeEntity;
import cn.gdeng.market.entity.lease.settings.MarketExpenditureStandardEntity;

public class MarketExpenditureStandardDTO extends MarketExpenditureStandardEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5259428416988136517L;

	/**
	 * 费项名称
	 */
	private String expName;
	
	/**
	 * 费项类型ID
	 */
	private int expType;
	
	/**
	 * 费项类型
	 */
	private String expTypeName;
	
	/**
	 * 计算方法
	 */
	private String rentModeName;
	
	/**
	 * 分段计费数据
	 */
	private List<MarketSectionalChargeEntity> secionalCharges;
	
	private int userId;
	
	/**
	 * 计费单位 名称 ，如：元/日 
	 * 
	 * */
	private String chargeUnitName;
	
	/**
	 * 市场ID
	 */
	private int marketId;
	
	public String getChargeUnitName() {
		return chargeUnitName;
	}

	public void setChargeUnitName(String chargeUnitName) {
		this.chargeUnitName = chargeUnitName;
	}

	public List<MarketSectionalChargeEntity> getSecionalCharges() {
		return secionalCharges;
	}

	public void setSecionalCharges(List<MarketSectionalChargeEntity> secionalCharges) {
		this.secionalCharges = secionalCharges;
	}

	public String getExpName() {
		return expName;
	}

	public void setExpName(String expName) {
		this.expName = expName;
	}

	public String getExpTypeName() {
		return expTypeName;
	}

	public void setExpTypeName(String expTypeName) {
		this.expTypeName = expTypeName;
	}

	public String getRentModeName() {
		return rentModeName;
	}

	public void setRentModeName(String rentModeName) {
		this.rentModeName = rentModeName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getExpType() {
		return expType;
	}

	public void setExpType(int expType) {
		this.expType = expType;
	}

	public int getMarketId() {
		return marketId;
	}

	public void setMarketId(int marketId) {
		this.marketId = marketId;
	}
	
}
