package cn.gdeng.market.dto.lease.settings;

import cn.gdeng.market.entity.lease.settings.MarketEntity;

public class MarketDTO  extends MarketEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2965502383374878699L;
	
	private String companyName;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	private String marketStatusStr;

	public String getMarketStatusStr() {
		if(this.getMarketStatus() ==null){
		}else if (this.getMarketStatus() ==1){
			return "运营中";
		}else if (this.getMarketStatus() ==2){
			return "筹备中";
		}else if (this.getMarketStatus() ==3){
			return "关闭";
		}
		return "";
	}

	public void setMarketStatusStr(String marketStatusStr) {
		this.marketStatusStr = marketStatusStr;
	}
	
	
	
	
}
