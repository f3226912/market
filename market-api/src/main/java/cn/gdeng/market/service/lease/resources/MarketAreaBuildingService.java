package cn.gdeng.market.service.lease.resources;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.common.StatisticsDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaBuildingDTO;
import cn.gdeng.market.dto.lease.settings.SummaryNodeDTO;
import cn.gdeng.market.entity.lease.resources.MarketAreaBuildingEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.BaseService;

/**
 * 
 * 楼栋管理
 * @author qrxu
 *
 */
public interface MarketAreaBuildingService extends BaseService<MarketAreaBuildingEntity>{
	/**
	 * 查询个数
	 * @throws BizException
	 */
	int queryTopOrgCount(Map<String,Object> param) throws BizException;
	/**
	 * 查询个数
	 * @param param
	 * @return
	 * @throws BizException
	 */
	int queryCountByCondition(Map<String,Object> param) throws BizException;
	/**
	 * 分页查询所有的楼栋信息
	 * @throws BizException
	 */
	GuDengPage<MarketAreaBuildingDTO> queryBuilding(GuDengPage<MarketAreaBuildingDTO> page) throws BizException;
    
	/**
	 * 新增楼栋（先根据区域名查询id）
	 * @return
	 * @throws BizException
	 */
	int addAreaBuilding(MarketAreaBuildingEntity entity) throws BizException;
	
	/**
	 * 详情 判断楼栋名称
	 * @return
	 * @throws BizException
	 */
	int queryBybuilDetailName(MarketAreaBuildingDTO dto) throws BizException;
	int queryBybuilDetailCode(MarketAreaBuildingDTO dto) throws BizException;
	
	/**
	 * 查询市场所属区域
	 * @param name
	 * @return
	 * @throws BizException
	 */
	List<MarketAreaBuildingDTO> queryAreaByMarketId(int marketId)throws BizException;
	/**
	 * 查询楼栋性质
	 * @param name
	 * @return
	 * @throws BizException
	 */
	List<MarketAreaBuildingDTO> queryNature()throws BizException;
	/**
	 * 查询楼栋详情
	 * @throws BizException
	 */
	MarketAreaBuildingDTO queryAreaBuilding(String areaId)throws BizException;
	/**
	 * 修改楼栋
	 * @throws BizException
	 */
	int editBuilding(MarketAreaBuildingEntity entity)throws BizException;
	/**
	 * 删除楼栋信息
	 * @param id
	 * @return
	 * @throws BizException
	 */
	int deleteAreaBuilding(MarketAreaBuildingEntity entity) throws BizException;
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
	 * 根据map条件，查询所有的楼栋
	 * @param map (可加：marketId，areaId，name，code，nature，status，areaId)
	 * @return
	 * @throws BizException
	 */
	public List<MarketAreaBuildingDTO> getBuilding(Map<String, Object> map) throws BizException;
	
	/**
	 * 楼栋资源使用情况统计
	 * @param  areaId buildingId resourceTypeId
	 * @return
	 * @throws BizException
	 */
	StatisticsDTO buildingResourceStatistics(Map<String, Object> map) throws BizException;
	
	/**
	 * 查询所有的市场区域楼栋(木有分页)
	 * @param map
	 * @return
	 * @author cai.xu
	 * @throws BizException
	 */
	public List<MarketAreaBuildingDTO> queryAll(Map<String, Object> map) throws BizException;
	/**
	 * 查询当前楼栋是资源为待放租
	 * 
	 * @param buildingId
	 * @return
	 * @throws BizException
	 */
	int querybuildingRes(int buildingId) throws BizException;
	
	/**
	 * 根据id获取楼栋信息
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public MarketAreaBuildingDTO queryBuildingById(String buildingId) throws BizException;
	
	
	/**-----------------------------平面图设置接口优化----------------------------------*/
	
	public List<MarketAreaBuildingDTO> queryBuildingDotByCondition(Map<String, Object> map) throws BizException;

	public SummaryNodeDTO buildingResourceSummary(Map<String, Object> map) throws BizException;
	
	public List<MarketAreaBuildingDTO> queryNoDotByCondition(Map<String, Object> map) throws BizException;
}
