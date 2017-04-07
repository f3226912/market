package cn.gdeng.market.service.lease.settings;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.MarketResourceTypeDTO;
import cn.gdeng.market.entity.lease.settings.MarketResourceTypeEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 资源类型接口
 * 
 */

public interface MarketResourceTypeService {
	
	/**
	 * 新增资源类型
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	public boolean insert(MarketResourceTypeEntity entity) throws BizException;
	
	/**
	 * 修改资源类型
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	public boolean update(MarketResourceTypeEntity entity) throws BizException;
	
	/**
	 * 逻辑删除资源类型
	 * @param id
	 * @param operatorId
	 * @return
	 * @throws BizException
	 */
	public boolean delete(int id, String operatorId) throws BizException;
	
	/**
	 * 根据资源类型编码查询总数
	 * @param code
	 * @return
	 * @throws BizException
	 */
	public int selCountByCode(String code) throws BizException;
	
	/**
	 * 根据id和资源类型编码查询总数
	 * @param marketId
	 * @param id
	 * @param code
	 * @return
	 * @throws BizException
	 */
	public int selCountByIdAndCodeAndMarketId(Integer marketId, int id, String code) throws BizException;
	
	/**
	 * 根据id和资源类型名称查询总数
	 * @param marketId
	 * @param id
	 * @param name
	 * @return
	 * @throws BizException
	 */
	public int selCountByIdAndNameAndMarketId(Integer marketId, int id, String name) throws BizException;
	
	/**
	 * 根据id和资源类型序号查询总数
	 * @param marketId
	 * @param id
	 * @param sort
	 * @return
	 * @throws BizException
	 */
	public int selCountByIdAndSortAndMarketId(Integer marketId, int id, int sort) throws BizException;
	
	/**
	 * 根据条件查询总数
	 * @param params
	 * @return
	 * @throws BizException
	 */
	public int selCountByCondition(Map<String, Object> params) throws BizException;
	
	/**
	 * 根据条件分页查询
	 * @param page
	 * @param dto
	 * @return GuDengPage<MarketResourceTypeDTO>
	 * @throws BizException
	 */
	public GuDengPage<MarketResourceTypeDTO> selByPage(GuDengPage<MarketResourceTypeDTO> page, 
			MarketResourceTypeDTO dto) throws BizException;
	
	/**
	 * 根据条件分页查询
	 * @param paramMap
	 * @return List<MarketResourceTypeDTO>
	 * @throws BizException
	 */
	public List<MarketResourceTypeDTO> selByPage(Map<String,Object> paramMap) throws BizException;
	
	/**
	 * 根据条件查询所有资源类型
	 * @param dto
	 * @return
	 * @throws BizException
	 */
	public List<MarketResourceTypeDTO> selAllByCondition(MarketResourceTypeDTO dto) throws BizException;

	/**
	 * 查询市场资源类型
	 * @param marketId
	 * @returns
	 * @throws BizException
	 */
	List<MarketResourceTypeDTO> queryByMarket(String marketId) throws BizException;
	
	/**
	 * 根据市场Id获取资源类型，可指定返回条数
	 * 
	 * map.put("status",1);//status=1代表有效，status=0代表无效，不传，查所有
	 * map.put("marketId",1);//指定市场，不传查所有的市场
	 * map.put("number",6); //指定返回条数，不指定number，或者number=0，返回当前市场的全部
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 */
	public List<MarketResourceTypeDTO> getMarketResourceType(Map<String,Object> map)throws BizException;
	
	/**
	 * 根据id获取资源类型
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public MarketResourceTypeDTO selMarketResTypeById(Integer id) throws BizException;
	
	/**
	 * 批量删除
	 * @param idArray
	 * @param operatorId
	 * @return
	 * @throws BizException
	 */
	public boolean batchDelete(int[] idArray, String operatorId) throws BizException;
	
	/**
	 * 根据系统级别初始化的Id(parentId)，找对应市场下的系统资源类型的id
	 * 
	 * map.put("marketId",marketId);
	 * map.put("parentId",parentId);
	 * 
	 * @param  map
	 * @return
	 * @throws BizException
	 */
	public int queryByMarketIdParentId(Map<String,Object> map) throws BizException;
}
