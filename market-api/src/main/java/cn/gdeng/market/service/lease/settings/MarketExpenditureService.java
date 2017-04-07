package cn.gdeng.market.service.lease.settings;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.MarketExpenditureDTO;
import cn.gdeng.market.entity.lease.settings.MarketExpenditureEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 费项服务接口
 * 
 * @author jiangyanli
 *
 */
public interface MarketExpenditureService {
	
	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return DTO
	 * 
	 */
	MarketExpenditureDTO getById(int id) throws BizException;

	/**
	 * 保存
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	Long insert(MarketExpenditureEntity t) throws BizException;
	
	/**
	 * 更新
	 * @param t
	 * @return
	 * @throws BizException
	 */
	int updateExpenditure(MarketExpenditureEntity t) throws BizException;
	
	/**
	 * 删除
	 * @param t
	 * @return
	 * @throws BizException
	 */
	int deleteExpenditure(MarketExpenditureEntity entity) throws BizException;
	
	/**
	 * 分页查询费项
	 * @throws BizException
	 */
	GuDengPage<MarketExpenditureDTO> queryByPage(GuDengPage<MarketExpenditureDTO> page) throws BizException;
	
	/**
	 * 
	 * 根据条件获取总记录数
	 */
	int queryCountByCondition(Map<String, Object> param) throws BizException;
	
	/**
	 * 获取当前市场下得所有费项的费项类型分组
	 * @param marketId
	 * @return
	 * @throws BizException
	 */
	List<Map<String,Object>> getExpTypes(Map<String,Object> paramMap) throws BizException;
	
	/**
	 * 一次性获取所有费项以及对应的计费标准
	 * @param paramMap 参数
	 * @return
	 * @throws BizException
	 */
	Map<String, Object> queryAllExpList(Map<String,Object> paramMap) throws BizException;
	
	
	/**
	 * 根据 map 查询所有费项记录
	 * @param map
	 * @return
	 * @throws BizException
	 */
	List<MarketExpenditureDTO> getExpList(Map<String,Object> map) throws BizException;
	
	/**
	 * 根据 map 查询所有市场下的系统费项记录
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 */
	List<MarketExpenditureDTO> getExpList(MarketExpenditureDTO param) throws BizException;

	/**
	 * 根据 map 查询所有费项记录
	 * 
	 * map.put("marketId",marketId);
	 * map.put("parentId",parentId);
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 */
	MarketExpenditureDTO queryByMarketIdParentId(Map<String,Object> map) throws BizException;

	/**
	 * 根据 map 查询所有市场下的系统费项记录
	 * 
	 * map.put("marketId",marketId);
	 * map.put("parentIds",parentIds);//parentId的list<String>集合
	 * 
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 */
	List<MarketExpenditureDTO> querySysTypeList(Map<String,Object> map) throws BizException;
	
	public MarketExpenditureDTO getByParentId(Map<String,Object> map);
	
}