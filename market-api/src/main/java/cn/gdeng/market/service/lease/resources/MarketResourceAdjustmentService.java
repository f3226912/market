package cn.gdeng.market.service.lease.resources;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.entity.lease.resources.MarketAreaBuildingEntity;
import cn.gdeng.market.entity.lease.resources.MarketBuildingUnitEntity;
import cn.gdeng.market.entity.lease.resources.MarketResourceEntity;
import cn.gdeng.market.entity.lease.resources.MarketUnitFloorEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 资源调整服务接口
 * author：gaofeng
 * date:2016/10/8
 * */
public interface MarketResourceAdjustmentService {
	
	/**
	 * 资源类型及面积设置
	 * */
	void adjustmentResourceTypeAndArea(List<MarketResourceEntity>	marketResourceEntities)throws BizException;
	/**
	 * 资源拆分
	 * */
	void adjustmentResourceSplit(MarketResourceEntity marketResourceEntity,List<MarketResourceEntity> splitResources)throws BizException;
	/**
	 * 资源合并
	 * */
	void adjustmentResourceMerge(List<MarketResourceEntity> mergeResources,MarketResourceEntity mergeResource)throws BizException;
	
	/**
	 * 新增资源
	 * */
	void saveResource(MarketResourceEntity	marketResourceEntity)throws BizException;
	
	/**
	 * 参数查询资源
	 * */
	MarketResourceEntity	getResourceById(MarketResourceEntity	marketResourceEntity)throws BizException;
	
	/**
	 * 资源调整 的动态 表格 json 
	 * */
	Map<String, Object> getBuildResources(MarketAreaBuildingEntity marketAreaBuildingEntity)throws BizException;
	
	/**
	 * 获取表格 楼层 数据
	 * */
	List<Map<String,Object>> getFloorsList(int buildId)throws BizException;
	
	/**
	 *获取表格单元数据
	 **/
	List<Map<String, Object>> getUnitList(int buildId)throws BizException;
	/**
	 * 新增楼栋
	 * */
	void saveAreaBuild(MarketAreaBuildingEntity	marketAreaBuildingEntity)throws BizException;
	/**
	 * 新增单元
	 * */
	void saveBuildUnit(MarketBuildingUnitEntity marketBuildingUnitEntity)throws BizException;
	/**
	 * 新增楼层
	 * */
	void saveUnitFloor(MarketUnitFloorEntity	marketUnitFloorEntity)throws BizException;
	/**
	 * 生成资源编码和名称
	 * */
	String getResourceCodeOrName(int marketId,int areaId,int buildId,int unitId,int floorId,String shorCode)throws BizException;
	String getResourceCodeOrName(int buildId,String shorCode)throws BizException;
	
	/**
	 * 删除资源
	 * */
	void deleteResourcesByIds(List<MarketResourceEntity> marketResourceEntities)throws BizException;
}
