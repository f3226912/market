package cn.gdeng.market.dto.lease.settings;

import java.util.List;

import cn.gdeng.market.entity.lease.settings.MarketExpenditureEntity;

public class MarketExpenditureDTO extends MarketExpenditureEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sysTypeName;
	
	private String expTypeName;
	
	private List<MarketExpenditureStandardDTO> expStandards;
	
	private List<String> expStandardNames;
	
	private int countExpStrandard;
	
	public List<MarketExpenditureStandardDTO> getExpStandards() {
		return expStandards;
	}

	public void setExpStandards(List<MarketExpenditureStandardDTO> expStandards) {
		this.expStandards = expStandards;
	}

	public List<String> getExpStandardNames() {
		return expStandardNames;
	}

	public void setExpStandardNames(List<String> expStandardNames) {
		this.expStandardNames = expStandardNames;
	}

	public String getExpTypeName() {
		return expTypeName;
	}

	public void setExpTypeName(String expTypeName) {
		this.expTypeName = expTypeName;
	}

	public String getSysTypeName() {
		return sysTypeName;
	}

	public void setSysTypeName(String sysTypeName) {
		this.sysTypeName = sysTypeName;
	}

	public int getCountExpStrandard() {
		return countExpStrandard;
	}

	public void setCountExpStrandard(int countExpStrandard) {
		this.countExpStrandard = countExpStrandard;
	}
	
}
