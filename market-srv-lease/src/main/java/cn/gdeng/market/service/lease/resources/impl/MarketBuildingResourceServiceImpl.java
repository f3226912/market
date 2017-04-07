package cn.gdeng.market.service.lease.resources.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.framework.dba.transaction.annotation.Transactional;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.lease.resources.MarketAreaBuildingDTO;
import cn.gdeng.market.dto.lease.resources.MarketBuildingUnitDTO;
import cn.gdeng.market.dto.lease.resources.MarketResourceDTO;
import cn.gdeng.market.dto.lease.resources.MarketResourceParam;
import cn.gdeng.market.entity.lease.resources.MarketAreaBuildingEntity;
import cn.gdeng.market.entity.lease.resources.MarketBuildingUnitEntity;
import cn.gdeng.market.entity.lease.resources.MarketResourceEntity;
import cn.gdeng.market.entity.lease.resources.MarketResourceOriginalEntity;
import cn.gdeng.market.entity.lease.resources.MarketUnitFloorEntity;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.resources.MarketBuildingResourceService;

/**
 * 楼栋管理-批量处理资源
 * 
 * @author qrxu
 *
 */

@Service(value = "marketBuildingResourceService")
public class MarketBuildingResourceServiceImpl implements MarketBuildingResourceService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public MarketAreaBuildingEntity getById(String id) throws BizException {
		return null;
	}

	@Override
	public Integer insert(MarketAreaBuildingEntity t) throws BizException {
		return null;
	}

	@Override
	public List<MarketAreaBuildingEntity> getList(Map<String, Object> map) throws BizException {
		return null;
	}

	@Override
	public List<MarketAreaBuildingEntity> getListPage(Map<String, Object> map) throws BizException {
		return null;
	}

	@Override
	public int deleteById(String id) throws BizException {
		return 0;
	}

	@Override
	public int deleteBatch(List<String> list) throws BizException {
		return 0;
	}

	@Override
	public int update(MarketAreaBuildingEntity t) throws BizException {
		return 0;
	}

	@Override
	public int getTotal(Map<String, Object> map) throws BizException {
		return 0;
	}

	@Override
	public int addResource(MarketResourceEntity entity) throws BizException {
		long count = baseDao.persist(entity, Long.class);
		return (int) count;
	}

	@Override
	public int addResourceOriginal(MarketResourceOriginalEntity entity) throws BizException {
		// int len = resOriginalDTO.size();
		// Map<String, Object>[] batchValues = new HashMap[len];
		// for (int i = 0; i < len; i++) {
		// MarketResourceOriginalDTO dto = resOriginalDTO.get(i);
		// batchValues[i] = DalUtils.convertToMap(dto);
		// }
		//
		// return
		// baseDao.batchUpdate("MarketBuildingResource.addResourceOriginal",
		// batchValues).length;
		long count = baseDao.persist(entity, Long.class);
		return (int) count;
	}

	@Override
	public int addMarketUnitFloor(MarketUnitFloorEntity entity) throws BizException {
		long count = baseDao.persist(entity, Long.class);
		return (int) count;
	}

	@Override
	public int addMarketBuildingUnit(MarketBuildingUnitEntity entity) throws BizException {
		long count = baseDao.persist(entity, Long.class);
		return (int) count;
	}

	@Override
	@Transactional
	public int handlerResource(MarketAreaBuildingDTO codes, MarketResourceParam param) throws BizException {
		// 根据当前楼栋-判断当前楼栋资源状态为 待放租 是否已存在 1：是，将 待放租 资源删除 重新新增 2：直接新增
				int isAddResource = querybuildingRes(Integer.parseInt(param.getBuilId()));
				// 直接新增
				if (isAddResource == 0) {
					//批量新增资源
					// 市场编码+区域编码+楼栋编码+单元+楼层+资源简码
					String resourceCode = codes.getMarketCode() + "-" + codes.getAreaCode() + "-" + param.getBuilCode();
					MarketResourceEntity resour = null;
					MarketResourceOriginalEntity resourceOriginal = null;

					String[] resourceAry = param.getResource().split(",");
					String[] buildingsAry = param.getBuildings().split(",");
					String[] unitsAry = param.getUnits().split(",");
					
					List<MarketUnitFloorEntity> floors=new ArrayList<MarketUnitFloorEntity>();
					for(int i=0;i<buildingsAry.length;i++){
						// 新增对应单元楼层
						MarketUnitFloorEntity floor = new MarketUnitFloorEntity();
						floor.setName(buildingsAry[i]);
						floor.setStatus(Const.NORMAL);
						floor.setFloorImage(null);
						floor.setBuildingId(Integer.parseInt(param.getBuilId()));
						floor.setFloorNo(i + 1);
						floor.setCreateUserId(codes.getCreateUserId());
						floor.setUpdateUserId(codes.getCreateUserId());
						floor.setCreateTime(new Date());
						floor.setUpdateTime(new Date());
						long floorId =  baseDao.persist(floor, Long.class);
						floor.setId((int)floorId);
						floors.add(floor);
					}
					
					List<MarketBuildingUnitEntity> units=new ArrayList<MarketBuildingUnitEntity>();
					for(int i=0;i<unitsAry.length;i++){
						// 新增对应单元楼层
						// 新增单元
						MarketBuildingUnitEntity unit = new MarketBuildingUnitEntity();
						unit.setName(unitsAry[i]);
						unit.setStatus(Const.NORMAL);
						unit.setUnitImage(null);
						unit.setUnitNo(i+1);
						unit.setBuildingId(Integer.parseInt(param.getBuilId()));
						unit.setCreateUserId(codes.getCreateUserId());
						unit.setUpdateUserId(codes.getCreateUserId());
						unit.setCreateTime(new Date());
						unit.setUpdateTime(new Date());
						long unitId =  baseDao.persist(unit, Long.class);
						unit.setId((int)unitId);
						units.add(unit);
					}
					
					for (int i = 0; i < resourceAry.length; i++) {
						String[] resourceAr = resourceAry[i].split("-");
						// 单元+楼层+资源简码
						String briefCode = unitsAry[Integer.valueOf(resourceAr[1]) - 1] + "-"
								+ buildingsAry[Integer.valueOf(resourceAr[0]) - 1] +"-"+ resourceAr[2];
						
						int floorNo = Integer.valueOf(resourceAr[0]) - 1;
						int unitNo = Integer.valueOf(resourceAr[1]) - 1;
						
						// 新增 楼层对应的户数
						resour = new MarketResourceEntity();
						
						resour.setCode(resourceCode + "-" + briefCode);// 市场编码+区域编码+楼栋编码+单元+楼层+资源简码
						resour.setShortCode(resourceAr[2]);
						resour.setOrginCode(resourceCode + "-" + briefCode);
						resour.setOriginOperate(0);
						resour.setName(param.getBuilName() + "-" + briefCode);// 楼栋名称-单元-楼层-资源简码
						resour.setMarketId(codes.getMarketId());
						resour.setAreaId(codes.getAreaId());
						resour.setBudingId(Integer.parseInt(param.getBuilId()));
						resour.setFloorId(floors.get(floorNo).getId());
						resour.setUnitId(units.get(unitNo).getId());
						resour.setInnerArea(null);// 套内面积
						resour.setLeaseArea(null);// 可出租面积
						resour.setLeaseStatus(1);// '租赁状态: 默认 1 待放租 2 待租 3 已租'
						resour.setRentMode(null);
						resour.setStatus(Const.NORMAL);// '状态: 默认 1 正常 0 删除
						resour.setCreateUserId(codes.getCreateUserId());
						resour.setUpdateUserId(codes.getCreateUserId());
						resour.setCreateTime(new Date());
						resour.setUpdateTime(new Date());
						baseDao.persist(resour, Long.class);

						// 记录原始资源表
						resourceOriginal = new MarketResourceOriginalEntity();
						resourceOriginal.setCode(resourceCode + "-" + briefCode);// 市场编码+区域编码+楼栋编码+单元+楼层+资源简码
						resourceOriginal.setName(param.getBuilName() + "-" + briefCode);// 楼栋名称-单元-楼层-资源简码
						resourceOriginal.setMarketId(codes.getMarketId());
						resourceOriginal.setAreaId(codes.getAreaId());
						resourceOriginal.setBudingId(Integer.parseInt(param.getBuilId()));
						resourceOriginal.setFloorId(floors.get(floorNo).getId());
						resourceOriginal.setUnitId(units.get(unitNo).getId());
						resourceOriginal.setInnerArea(null);// 套内面积
						resourceOriginal.setLeaseArea(null);// 可出租面积
						resourceOriginal.setLeaseStatus(1);// '租赁状态: 默认 1 待放租 2 待租 3 已租'
						resourceOriginal.setRentMode(null);
						resourceOriginal.setStatus(Const.NORMAL);// '状态: 默认 1 正常 0 删除
						resourceOriginal.setCreateUserId(codes.getCreateUserId());
						resourceOriginal.setUpdateUserId(codes.getCreateUserId());
						resourceOriginal.setCreateTime(new Date());
						resourceOriginal.setUpdateTime(new Date());
						baseDao.persist(resourceOriginal, Long.class);

					}
					  return 1;
		     
				} else {
					Map<String, Object> builParam = new HashMap<>();
					builParam.put("buildingId", param.getBuilId());
					baseDao.execute("MarketBuildingResource.delFloor", builParam);// 删除楼层
					baseDao.execute("MarketBuildingResource.delUnit", builParam);// 删除单元
					baseDao.execute("MarketBuildingResource.delRes", builParam);;// 删除楼栋对应资源
					//批量新增资源
					// 市场编码+区域编码+楼栋编码+单元+楼层+资源简码
					String resourceCode = codes.getMarketCode() + "-" + codes.getAreaCode() + "-" + param.getBuilCode();
					MarketResourceEntity resour = null;
					MarketResourceOriginalEntity resourceOriginal = null;

					String[] resourceAry = param.getResource().split(",");
					String[] buildingsAry = param.getBuildings().split(",");
					String[] unitsAry = param.getUnits().split(",");
					
					List<MarketUnitFloorEntity> floors=new ArrayList<MarketUnitFloorEntity>();
					for(int i=0;i<buildingsAry.length;i++){
						// 新增对应单元楼层
						MarketUnitFloorEntity floor = new MarketUnitFloorEntity();
						floor.setName(buildingsAry[i]);
						floor.setStatus(Const.NORMAL);
						floor.setFloorImage(null);
						floor.setBuildingId(Integer.parseInt(param.getBuilId()));
						floor.setFloorNo(i + 1);
						floor.setCreateUserId(codes.getCreateUserId());
						floor.setUpdateUserId(codes.getCreateUserId());
						floor.setCreateTime(new Date());
						floor.setUpdateTime(new Date());
						long floorId =  baseDao.persist(floor, Long.class);
						floor.setId((int)floorId);
						floors.add(floor);
					}
					
					List<MarketBuildingUnitEntity> units=new ArrayList<MarketBuildingUnitEntity>();
					for(int i=0;i<unitsAry.length;i++){
						// 新增对应单元楼层
						// 新增单元
						MarketBuildingUnitEntity unit = new MarketBuildingUnitEntity();
						unit.setName(unitsAry[i]);
						unit.setStatus(Const.NORMAL);
						unit.setUnitImage(null);
						unit.setUnitNo(i+1);
						unit.setBuildingId(Integer.parseInt(param.getBuilId()));
						unit.setCreateUserId(codes.getCreateUserId());
						unit.setUpdateUserId(codes.getCreateUserId());
						unit.setCreateTime(new Date());
						unit.setUpdateTime(new Date());
						long unitId =  baseDao.persist(unit, Long.class);
						unit.setId((int)unitId);
						units.add(unit);
					}
					
					for (int i = 0; i < resourceAry.length; i++) {
						String[] resourceAr = resourceAry[i].split("-");
						// 单元+楼层+资源简码
						String briefCode = unitsAry[Integer.valueOf(resourceAr[1]) - 1] + "-"
								+ buildingsAry[Integer.valueOf(resourceAr[0]) - 1] +"-"+ resourceAr[2];
						
						int floorNo = Integer.valueOf(resourceAr[0]) - 1;
						int unitNo = Integer.valueOf(resourceAr[1]) - 1;
						
						// 新增 楼层对应的户数
						resour = new MarketResourceEntity();
						
						resour.setCode(resourceCode + "-" + briefCode);// 市场编码+区域编码+楼栋编码+单元+楼层+资源简码
						resour.setShortCode(resourceAr[2]);
						resour.setOrginCode(resourceCode + "-" + briefCode);
						resour.setOriginOperate(0);
						resour.setName(param.getBuilName() + "-" + briefCode);// 楼栋名称-单元-楼层-资源简码
						resour.setMarketId(codes.getMarketId());
						resour.setAreaId(codes.getAreaId());
						resour.setBudingId(Integer.parseInt(param.getBuilId()));
						resour.setFloorId(floors.get(floorNo).getId());
						resour.setUnitId(units.get(unitNo).getId());
						resour.setInnerArea(null);// 套内面积
						resour.setLeaseArea(null);// 可出租面积
						resour.setLeaseStatus(1);// '租赁状态: 默认 1 待放租 2 待租 3 已租'
						resour.setRentMode(null);
						resour.setStatus(Const.NORMAL);// '状态: 默认 1 正常 0 删除
						resour.setCreateUserId(codes.getCreateUserId());
						resour.setUpdateUserId(codes.getCreateUserId());
						resour.setCreateTime(new Date());
						resour.setUpdateTime(new Date());
						baseDao.persist(resour, Long.class);

						// 记录原始资源表
						resourceOriginal = new MarketResourceOriginalEntity();
						resourceOriginal.setCode(resourceCode + "-" + briefCode);// 市场编码+区域编码+楼栋编码+单元+楼层+资源简码
						resourceOriginal.setName(param.getBuilName() + "-" + briefCode);// 楼栋名称-单元-楼层-资源简码
						resourceOriginal.setMarketId(codes.getMarketId());
						resourceOriginal.setAreaId(codes.getAreaId());
						resourceOriginal.setBudingId(Integer.parseInt(param.getBuilId()));
						resourceOriginal.setFloorId(floors.get(floorNo).getId());
						resourceOriginal.setUnitId(units.get(unitNo).getId());
						resourceOriginal.setInnerArea(null);// 套内面积
						resourceOriginal.setLeaseArea(null);// 可出租面积
						resourceOriginal.setLeaseStatus(1);// '租赁状态: 默认 1 待放租 2 待租 3 已租'
						resourceOriginal.setRentMode(null);
						resourceOriginal.setStatus(Const.NORMAL);// '状态: 默认 1 正常 0 删除
						resourceOriginal.setCreateUserId(codes.getCreateUserId());
						resourceOriginal.setUpdateUserId(codes.getCreateUserId());
						resourceOriginal.setCreateTime(new Date());
						resourceOriginal.setUpdateTime(new Date());
						baseDao.persist(resourceOriginal, Long.class);

					}
				}
		       return 1;
			}

	@Override
	public int querybuildingRes(int buildingId) throws BizException {
		Map<String, Object> param = new HashMap<>();
		param.put("buildingId", buildingId);
		return baseDao.queryForObject("MarketBuildingResource.queryResources", param, Integer.class);
	}

	@Override
	public MarketAreaBuildingDTO queryCodeById(int marketId,int areaId) throws BizException {
		Map<String, Object> param = new HashMap<>();
		param.put("marketId", marketId);
		param.put("areaId", areaId);
		return baseDao.queryForObject("MarketBuildingResource.queryCodeById", param, MarketAreaBuildingDTO.class);
	}

	@Override
	public String queryBuilCodeById(int buildingId) throws BizException {
		Map<String, Object> param = new HashMap<>();
		param.put("buildingId", buildingId);
		return baseDao.queryForObject("MarketBuildingResource.queryBuilCodeById", param, String.class);

	}

	public List<MarketBuildingUnitDTO> getUnitAndResource(Map<String, Object> map) throws BizException {
		// String resourceTypeId = (String)map.get("resourceTypeId");
		// Map map = new HashMap();
		map.put("buildingId", map.get("budingId"));
		//一会   buildingId 一会 budingId
		List<MarketBuildingUnitDTO> list = baseDao.queryForList("MarketBuildingUnit.getList", map,
				MarketBuildingUnitDTO.class);
		if (list != null) {
			for (MarketBuildingUnitDTO dto : list) {
				Integer unitId = dto.getId();
				map.remove("unitId");
				map.put("unitId", unitId);
				// map.put("resourceTypeId", resourceTypeId);
				List<MarketResourceDTO> resources = baseDao.queryForList("MarketResource.getList", map,
						MarketResourceDTO.class);
				dto.setResources(resources);
			}
		}
		return list;
	}

	@Override
	public int querybuildingResTwo(int buildingId) throws BizException {
		Map<String, Object> param = new HashMap<>();
		param.put("buildingId", buildingId);
		return baseDao.queryForObject("MarketBuildingResource.queryResourcesTwo", param, Integer.class);
	}

	@Override
	public int deleteUnit(int buildingId) throws BizException {
		Map<String, Object> param = new HashMap<>();
		param.put("buildingId", buildingId);
		return baseDao.execute("MarketBuildingResource.delUnit", param);
	}

	@Override
	public int deleteFloor(int buildingId) throws BizException {
		Map<String, Object> param = new HashMap<>();
		param.put("buildingId", buildingId);
		return baseDao.execute("MarketBuildingResource.delFloor", param);
	}

	@Override
	public int deleteResource(int buildingId) throws BizException {
		Map<String, Object> param = new HashMap<>();
		param.put("buildingId", buildingId);
		return baseDao.execute("MarketBuildingResource.delRes", param);
	}

	@Override
	@Transactional
	public int disposeResource(MarketAreaBuildingDTO codes, MarketResourceParam param) throws BizException {

		return 0;
	}

	@Override
	public int queryAreaBybId(String buildingId) throws BizException {
		Map<String, Object> param = new HashMap<>();
		param.put("buildingId", buildingId);
		return baseDao.queryForObject("MarketBuildingResource.queryAreaBybId", param, Integer.class);
	}

	@Override
	public int querybuildingResOne(int buildingId) throws BizException {
		Map<String, Object> param = new HashMap<>();
		param.put("buildingId", buildingId);
		return baseDao.queryForObject("MarketBuildingResource.queryResourcesOne", param, Integer.class);
	}

}
