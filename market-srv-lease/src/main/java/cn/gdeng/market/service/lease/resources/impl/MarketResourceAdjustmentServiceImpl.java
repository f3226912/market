package cn.gdeng.market.service.lease.resources.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gudeng.framework.dba.transaction.annotation.Transactional;
import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.entity.lease.resources.MarketAreaBuildingEntity;
import cn.gdeng.market.entity.lease.resources.MarketAreaEntity;
import cn.gdeng.market.entity.lease.resources.MarketBuildingUnitEntity;
import cn.gdeng.market.entity.lease.resources.MarketResourceEntity;
import cn.gdeng.market.entity.lease.resources.MarketUnitFloorEntity;
import cn.gdeng.market.entity.lease.settings.MarketEntity;
import cn.gdeng.market.entity.lease.settings.MarketResourceTypeEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.resources.MarketResourceAdjustmentService;

/**
 * 资源调整服务类
 * author ： gaofeng
 * date:2016/10/8
 * */
@Service(value="marketResourceAdjustmentService")
public class MarketResourceAdjustmentServiceImpl implements MarketResourceAdjustmentService{
	
	@Autowired
	public BaseDao<MarketResourceEntity> marketResourceDao;
	
	@Autowired
	public BaseDao<MarketEntity>	MarketDao;  //市场dao
	
	@Autowired
	public BaseDao<MarketResourceTypeEntity>	marketResourceTypeDao; //资源类型dao
	
	@Autowired
	public BaseDao<MarketAreaEntity> marketAreaDao; //区域dao
	
	@Autowired
	public BaseDao<MarketAreaBuildingEntity> marketAreaBuildDao; //楼栋dao
	
	@Autowired
	public BaseDao<MarketBuildingUnitEntity> marketBuildUnitDao; //单元dao
	
	@Autowired
	public BaseDao<MarketUnitFloorEntity>	marketUnitFloorDao; //楼层dao

	/**
	 * 资源类型及面积设置
	 * 直接更新资源
	 * */
	@Override
	public void adjustmentResourceTypeAndArea(List<MarketResourceEntity>	marketResourceEntities) throws BizException {
		
		//动态更新 类型及面积 设置 字段
		for(MarketResourceEntity marketResourceEntity:marketResourceEntities){
			this.marketResourceDao.dynamicMerge(marketResourceEntity);
		}
	}

	/**
	 * 一个资源拆分成多个资源
	 * param marketResourceEntity 被拆分资源
	 * param splitResources 拆分后的资源
	 * */
	
	@Transactional
	@Override
	public void adjustmentResourceSplit(MarketResourceEntity marketResourceEntity,List<MarketResourceEntity> splitResources) throws BizException {
		
		this.marketResourceDao.dynamicMerge(marketResourceEntity);  //更新被拆分资源
		for(MarketResourceEntity splitResource:splitResources){
			this.marketResourceDao.persist(splitResource);  //循环插入 拆分后的 资源
		}
		
	}

	/**
	 * 多个资源合并成一个资源
	 * param	mergeResources 被合并的资源
	 * param 	mergeResource  合并后的资源
	 * */
	
	@Transactional
	@Override
	public void adjustmentResourceMerge(List<MarketResourceEntity> mergeResources,MarketResourceEntity mergeResource) throws BizException {
		
		for(MarketResourceEntity mergeRes:mergeResources){
			this.marketResourceDao.dynamicMerge(mergeRes); //更新被合并资源
		}
		this.marketResourceDao.persist(mergeResource); //插入合并后的新资源  
	}

	/**
	 * 新增资源
	 * */
	@Override
	public void saveResource(MarketResourceEntity marketResourceEntity) throws BizException {
		this.marketResourceDao.persist(marketResourceEntity);
		
	}

	/**
	 * 参数查询资源
	 * */
	@Override
	public MarketResourceEntity getResourceById(MarketResourceEntity marketResourceEntity) {
		marketResourceEntity = this.marketResourceDao.find(marketResourceEntity);
		if(marketResourceEntity.getResourceTypeId() != null){
			MarketResourceTypeEntity	marketResourceTypeEntity = new MarketResourceTypeEntity();
			marketResourceTypeEntity.setId(marketResourceEntity.getResourceTypeId());
			marketResourceTypeEntity = this.marketResourceTypeDao.find(marketResourceTypeEntity);
			marketResourceEntity.setResourceTypeId(marketResourceTypeEntity.getParentId());
		}
		return marketResourceEntity;
	}

	/**
	 * 返回 楼栋 资源调整 表格 json 数据
	 * */
	@Override
	public Map<String, Object> getBuildResources(MarketAreaBuildingEntity marketAreaBuildingEntity) {
		Map<String, Object> map = new LinkedHashMap<>();
		List<Map<String, Object>> units = getUnitList(marketAreaBuildingEntity.getId());
		List<Map<String, Object>> floors = getFloorsList(marketAreaBuildingEntity.getId());
		map.put("units", units);
		map.put("floors", floors);
		map.put("resources", getRowSrc(units, floors));
		return map;
	}
	
	/**
	 *获取表格单元数据
	 **/
	@Override
	public List<Map<String, Object>> getUnitList(int buildId){
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String,Object>	paramMap = new HashMap<>();
		paramMap.put("buildingId", buildId);
		List<MarketBuildingUnitEntity> marketBuildingUnitEntities = this.marketBuildUnitDao.queryForList("BuildUnit.getUnitByBuildId", paramMap,MarketBuildingUnitEntity.class);
		for(MarketBuildingUnitEntity marketBuildingUnitEntity :marketBuildingUnitEntities){
			Map<String,Object>	map = new HashMap<>();
			map.put("id", marketBuildingUnitEntity.getId());
			map.put("unitName", marketBuildingUnitEntity.getName());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 获取表格 楼层 数据
	 * */
	@Override
	public List<Map<String,Object>> getFloorsList(int buildId){
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String,Object>	paramMap = new HashMap<>();
		paramMap.put("buildingId", buildId);
		List<MarketUnitFloorEntity> marketUnitFloorEntities = this.marketUnitFloorDao.queryForList("UnitFloor.getFloorBuildId", paramMap,MarketUnitFloorEntity.class);
		for(MarketUnitFloorEntity marketUnitFloorEntity:marketUnitFloorEntities){
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("id", marketUnitFloorEntity.getId());
			map.put("floorName", marketUnitFloorEntity.getName());
			list.add(map);
		}
		return list;
	}
	/**
	 * 返回行 的所有资源
	 * */
	public List<Map<String,Object>> getRowSrc(List<Map<String, Object>> units,List<Map<String, Object>> floors){
		List<Map<String, Object>> list = new ArrayList<>();
		for(Map<String,Object> floor:floors){
			Map<String,Object> floorSrcs = new HashMap<>();
			floorSrcs.put("floorId", floor.get("id"));
			floorSrcs.put("floorName", floor.get("floorName"));
			floorSrcs.put("floorSrcs",getFloorSrcs(Integer.parseInt(floor.get("id").toString()),units));
			list.add(floorSrcs);
		} 
		return list;
	}
	
	/**
	 * 获取 层下 的所有单元的资源
	 * */
	public List<Map<String,Object>> getFloorSrcs(int floorId,List<Map<String, Object>> units){
		List<Map<String,Object>>	list = new ArrayList<>();
		for(Map<String,Object> unit:units){
			Map<String,Object> srcMap = new LinkedHashMap<>();
			srcMap.put("floorId",floorId);
			srcMap.put("unitId",unit.get("id"));
			srcMap.put("unitName",unit.get("unitName"));
			srcMap.put("unitfloorSrcs", getUnitAndFloorSourc(Integer.parseInt(unit.get("id").toString()),floorId));
			list.add(srcMap);
		}
		return list;
	}
	
	/**
	 * 通过 单元 id,楼层 id,参数，获取资源
	 * */
	public List<Map<String, Object>> getUnitAndFloorSourc(int unitId,int floorId){
		Map<String,Object>	paramMap = new HashMap<>();
		paramMap.put("unitId", unitId);
		paramMap.put("floorId", floorId);
		List<Map<String, Object>> marketResourceEntities = this.marketResourceDao.queryForList("MarketResource.getResourceByUnitAndFloor", paramMap);
		return marketResourceEntities;
	}

	/**
	 * 新增楼栋
	 * */
	@Override
	public void saveAreaBuild(MarketAreaBuildingEntity marketAreaBuildingEntity) {
		this.marketAreaBuildDao.persist(marketAreaBuildingEntity);
		
	}

	/**
	 * 新增单元
	 * */
	@Override
	public void saveBuildUnit(MarketBuildingUnitEntity marketBuildingUnitEntity) {
		this.marketBuildUnitDao.persist(marketBuildingUnitEntity);
	}

	/**
	 * 新增楼层
	 * */
	@Override
	public void saveUnitFloor(MarketUnitFloorEntity marketUnitFloorEntity) {
		this.marketUnitFloorDao.persist(marketUnitFloorEntity);
		
	}
	
	
	/**
	 * 批量删除资源
	 * */
	@Override
	public void deleteResourcesByIds(List<MarketResourceEntity> marketResourceEntities) {
		for(MarketResourceEntity marketResourceEntity:marketResourceEntities){
			//逻辑删除资源，就是更新
			this.marketResourceDao.dynamicMerge(marketResourceEntity);
		}
	}

	/**
	 * 生成资源编码和名称
	 * */
	@Override
	public String getResourceCodeOrName(int marketId, int areaId, int buildId, int unitId,int floorId, String shorCode) {
		String result = "";
		MarketEntity	marketEntity = new MarketEntity();
		marketEntity.setId(marketId);
		marketEntity = this.MarketDao.find(marketEntity);
		if(null != marketEntity){
			result += marketEntity.getCode()+"-";
		}
		MarketAreaEntity marketAreaEntity = new MarketAreaEntity();
		marketAreaEntity.setId(areaId);
		marketAreaEntity = this.marketAreaDao.find(marketAreaEntity);
		if(null != marketAreaEntity){
			result += marketAreaEntity.getCode()+"-";
		}
		MarketAreaBuildingEntity marketAreaBuildingEntity = new MarketAreaBuildingEntity();
		marketAreaBuildingEntity.setId(buildId);
		marketAreaBuildingEntity = this.marketAreaBuildDao.find(marketAreaBuildingEntity);
		if(null != marketAreaBuildingEntity){
			result += marketAreaBuildingEntity.getCode()+"-";
		}
		MarketBuildingUnitEntity marketBuildingUnitEntity = new MarketBuildingUnitEntity();
		marketBuildingUnitEntity.setId(unitId);
		marketBuildingUnitEntity = this.marketBuildUnitDao.find(marketBuildingUnitEntity);
		if(null != marketBuildingUnitEntity){
			result += marketBuildingUnitEntity.getName()+"-";
		}
		MarketUnitFloorEntity marketUnitFloorEntity = new MarketUnitFloorEntity();
		marketUnitFloorEntity.setId(floorId);
		marketUnitFloorEntity = this.marketUnitFloorDao.find(marketUnitFloorEntity);
		if(null != marketUnitFloorEntity){
			result += marketUnitFloorEntity.getName()+"-";
		}
		return result+shorCode;
	}

	@Override
	public String getResourceCodeOrName(int buildId, String shorCode) {
		String result = "";
		MarketAreaBuildingEntity marketAreaBuildingEntity = new MarketAreaBuildingEntity();
		marketAreaBuildingEntity.setId(buildId);
		marketAreaBuildingEntity = this.marketAreaBuildDao.find(marketAreaBuildingEntity);
		if(null != marketAreaBuildingEntity){
			result += marketAreaBuildingEntity.getCode()+"-";
		}
		return result+shorCode;
	}

	
	
	
	
	
	
	
	
}
