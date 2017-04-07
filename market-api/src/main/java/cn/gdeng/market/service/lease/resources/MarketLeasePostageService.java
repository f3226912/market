package cn.gdeng.market.service.lease.resources;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.resources.MarketLeasePostageDTO;
import cn.gdeng.market.exception.BizException;

/**
 * 资源和合同管理接口
 *
 */
public interface MarketLeasePostageService {
	
	/**
	 * 根据条件查询总数
	 * @param paramMap
	 * @return
	 * @throws BizException
	 */
	public int findTotalByCondition(Map<String, Object> paramMap) throws BizException;
	
	/**
	 * 根据条件分页查询记录
	 * @param paramMap
	 * @return
	 * @throws BizException
	 */
	public List<MarketLeasePostageDTO> findPageByCondition(Map<String, Object> paramMap) throws BizException;
	
	/**
	 * 根据条件分页查询记录
	 * @param page
	 * @return
	 * @throws BizException
	 */
	public GuDengPage<MarketLeasePostageDTO> getPageByCondition(GuDengPage<MarketLeasePostageDTO> page, 
			MarketLeasePostageDTO dto) throws BizException;

}
