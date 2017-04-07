package cn.gdeng.market.service.lease.resources;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.common.StatisticsDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaDTO;
import cn.gdeng.market.dto.lease.settings.SummaryNodeDTO;
import cn.gdeng.market.entity.lease.resources.MarketAreaEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.BaseService;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author bdhuang
 *
 */
public interface MarketAreaService extends BaseService<MarketAreaDTO> {
	
	public Long persist(MarketAreaEntity entity) throws BizException;
	
	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 */
	public GuDengPage<MarketAreaDTO> queryByPage(GuDengPage<MarketAreaDTO> page)throws BizException;
	
	/**
	 * 根据条件查询list
	 * @param page
	 * @return
	 * @throws BizException
	 */
	public GuDengPage<MarketAreaDTO> queryAll(GuDengPage<MarketAreaDTO> page)throws BizException;
	
	/**
	 * 批量插入
	 * 
	 * @param list
	 * @return  
	 * 
	 */
	public int insertBatch(List<MarketAreaDTO> list) throws BizException;
	
	/**
	 * 查询市场区域
	 * @param marketId
	 * @return
	 * @throws BizException
	 */
	List<MarketAreaDTO> queryByMarket(String marketId) throws BizException;
	
	
	/**
	 * 区域资源使用情况统计
	 * @param areaId resourceTypeId isDrawedDot是否已经描点
	 * @return
	 * @throws BizException
	 */
	StatisticsDTO areaResourceStatistics(String areaId, String resourceTypeId, boolean isDrawedDot) throws BizException;
	

	/**
	 * 根据市场，查询当前市场下的所有区域和楼栋
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 */
	public List<MarketAreaDTO> getAreaAndBuilding(Map<String, Object> map) throws BizException;
	
	/**
	 * 校验是否存在 code
	 * 
	 * */
	public Integer ifExistCode(MarketAreaDTO dto) throws BizException;
	/**
	 * 校验是否存在 name
	 * 
	 * */
	public Integer ifExistName(MarketAreaDTO dto) throws BizException;
	
	
	/**------------------------------------平面图设置优化接口相关-------------------------------------------*/
	
	/**
	 * 查询市场下已经描点的区域
	 * @param marketId
	 * @return
	 * @throws BizException
	 */
	List<MarketAreaDTO> queryAreaDotByMarket(String marketId) throws BizException;
	
	/**
	 * 区域资源使用情况统计
	 * @param areaId resourceTypeId isDrawedDot是否已经描点
	 * @return
	 * @throws BizException
	 */
	SummaryNodeDTO areaResourceSummary(String areaId, String resourceTypeId, boolean isDrawedDot) throws BizException;

	/**
	 * 查询市场未描点的区域
	 * @param marketId
	 * @return
	 * @throws BizException
	 */
	List<MarketAreaDTO> queryNoDotByMarket(String marketId) throws BizException;
	
}