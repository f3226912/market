package cn.gdeng.market.service.lease.resources;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.lease.resources.MarketAreaBuildingDTO;
import cn.gdeng.market.dto.lease.resources.MarketBuildingUnitDTO;
import cn.gdeng.market.dto.lease.resources.MarketResourceParam;
import cn.gdeng.market.entity.lease.resources.MarketAreaBuildingEntity;
import cn.gdeng.market.entity.lease.resources.MarketBuildingUnitEntity;
import cn.gdeng.market.entity.lease.resources.MarketResourceEntity;
import cn.gdeng.market.entity.lease.resources.MarketResourceOriginalEntity;
import cn.gdeng.market.entity.lease.resources.MarketUnitFloorEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.BaseService;

/**
 * 
 * 楼栋管理-批量生成资源
 * 
 * @author qrxu
 *
 */
public interface MarketBuildingResourceService extends BaseService<MarketAreaBuildingEntity> {
	/**
	 * 查询当前楼栋是资源为待放租
	 * 
	 * @param buildingId
	 * @return
	 * @throws BizException
	 */
	int querybuildingRes(int buildingId) throws BizException;
	/**
	 * 查询当前楼栋资源为已租 放租
	 * @param buildingId
	 * @return
	 * @throws BizException
	 */
	int querybuildingResTwo(int buildingId)throws BizException;
	/**
	 * 查询当前楼栋资源为待放租
	 * @param buildingId
	 * @return
	 * @throws BizException
	 */
	int querybuildingResOne(int buildingId)throws BizException;

	/**
	 * 批量新增-单元表
	 * 
	 * @param dto
	 * @return
	 * @throws BizException
	 */
	int addMarketBuildingUnit(MarketBuildingUnitEntity entity) throws BizException;

	/**
	 * 批量新增-楼层表
	 * 
	 * @param dto
	 * @return
	 * @throws BizException
	 */
	int addMarketUnitFloor(MarketUnitFloorEntity entity) throws BizException;

	/**
	 * 更新资源
	 * 
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	int addResource(MarketResourceEntity entity ) throws BizException;

	/**
	 * 记录原始资源
	 * 
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	int addResourceOriginal(MarketResourceOriginalEntity entity) throws BizException;

/**
 * 处理新增楼栋资源
 * @param codes
 * @param param
 * @return
 * @throws BizException
 */
	int handlerResource(MarketAreaBuildingDTO codes,MarketResourceParam param) throws BizException;
	/**
	 * 处理批量新增楼栋资源逻辑
	 * @param codes
	 * @param param
	 * @return
	 * @throws BizException
	 */
	int disposeResource(MarketAreaBuildingDTO codes,MarketResourceParam param) throws BizException;
	/**
	 * 根据市场id  查询市场编码以及区域编码
	 * @param codeDTO
	 * @return
	 * @throws BizException
	 */
	MarketAreaBuildingDTO queryCodeById(int marketId,int areaId)throws BizException;
	/**
	 * 根据楼栋id  楼栋编码
	 * @param codeDTO
	 * @return
	 * @throws BizException
	 */
	String queryBuilCodeById(int buildingId)throws BizException;
	/**
	 * 根据楼栋id  区域id
	 * @param codeDTO
	 * @return
	 * @throws BizException
	 */
	int queryAreaBybId(String buildingId)throws BizException;
	
	
	/**
	 * 根据楼栋id，获取当前楼栋下的所有单元以及资源
	 * 
	 * map.put("buildingId",buildingId);
	 * map.put("floorId",floorId);
	 * map.put("resourceTypeId",resourceTypeId);
	 * 
	 * @return
	 * @throws BizException
	 */
	public List<MarketBuildingUnitDTO> getUnitAndResource(Map<String,Object> map) throws BizException;

	/**
	 * 删除楼栋对应的单元
	 * @param id
	 * @return
	 * @throws BizException
	 */
	int deleteUnit(int buildingId) throws BizException;
	/**
	 * 删除楼栋对应的楼层
	 * @param id
	 * @return
	 * @throws BizException
	 */
	int deleteFloor(int buildingId) throws BizException;
	/**
	 * 删除楼栋资源
	 * @param id
	 * @return
	 * @throws BizException
	 */
	int deleteResource(int buildingId) throws BizException;
}
