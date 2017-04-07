package cn.gdeng.market.service.lease.settings;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.MarketExpenditureStandardDTO;
import cn.gdeng.market.entity.lease.settings.MarketExpenditureStandardEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 计费标准服务接口
 * 
 * @author jiangyanli
 *
 */
public interface MarketExpenditureStandardService {

	/**
	 * 保存
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	Long insert(MarketExpenditureStandardDTO t) throws BizException;
	
	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return DTO
	 * 
	 */
	MarketExpenditureStandardDTO getById(int id) throws BizException;
	
	/**
	 * 根据费项获取计费标准
	 * @param expId
	 * @return
	 * @throws BizException
	 */
	List<MarketExpenditureStandardDTO> getByExpId(int expId) throws BizException;
	
	/**
	 * 分页查询计费标准
	 * @throws BizException
	 */
	GuDengPage<MarketExpenditureStandardDTO> queryByPage(GuDengPage<MarketExpenditureStandardDTO> page) throws BizException;
	
	/**
	 * 
	 * 根据条件获取总记录数
	 */
	int queryCountByCondition(Map<String, Object> param) throws BizException;
	
	/**
	 * 更新
	 * @param t
	 * @return
	 * @throws BizException
	 */
	Number updateExpStandard(MarketExpenditureStandardDTO t) throws BizException;
	
	/**
	 * 删除
	 * @param t
	 * @return
	 * @throws BizException
	 */
	int deleteById(MarketExpenditureStandardEntity entity) throws BizException;

}