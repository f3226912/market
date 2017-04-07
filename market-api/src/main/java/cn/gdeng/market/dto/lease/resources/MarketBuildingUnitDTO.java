package cn.gdeng.market.dto.lease.resources;

import java.util.ArrayList;
import java.util.List;

import cn.gdeng.market.entity.lease.resources.MarketBuildingUnitEntity;
/**
 * 市场楼栋单元
 * @author qrxu
 *
 */
public class MarketBuildingUnitDTO extends MarketBuildingUnitEntity{
	private static final long serialVersionUID = -8986621122000117891L;
	
	private List<MarketResourceDTO> resources =new ArrayList<MarketResourceDTO>();

	public List<MarketResourceDTO> getResources() {
		return resources;
	}

	public void setResources(List<MarketResourceDTO> resources) {
		this.resources = resources;
	}
	

}
