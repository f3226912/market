package cn.gdeng.market.dto.lease.resources;

import java.util.ArrayList;
import java.util.List;

import cn.gdeng.market.entity.lease.resources.MarketResourceEntity;
import cn.gdeng.market.entity.lease.resources.MarketUnitFloorEntity;
import cn.gdeng.market.service.lease.resources.MarketBuildingResourceService;

public class MarketAreaParam   {

	/**
	 * 
	 */
	private List<MarketAreaDTO> marketAreas =new ArrayList<MarketAreaDTO>();


	public List<MarketAreaDTO> getMarketAreas() {
		return marketAreas;
	}

	public void setMarketAreas(List<MarketAreaDTO> marketAreas) {
		this.marketAreas = marketAreas;
	}
	
}
