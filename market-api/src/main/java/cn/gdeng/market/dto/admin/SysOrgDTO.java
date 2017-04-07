package cn.gdeng.market.dto.admin;

import cn.gdeng.market.entity.admin.SysOrgEntity;
import cn.gdeng.market.entity.lease.settings.MarketEntity;

public class SysOrgDTO extends SysOrgEntity {

	private static final long serialVersionUID = 1948479207983167674L;
	
	private MarketEntity marKetEntity;

	public MarketEntity getMarKetEntity() {
		return marKetEntity;
	}

	public void setMarKetEntity(MarketEntity marKetEntity) {
		this.marKetEntity = marKetEntity;
	}
}
