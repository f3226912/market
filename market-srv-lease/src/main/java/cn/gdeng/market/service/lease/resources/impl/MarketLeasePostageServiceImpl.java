package cn.gdeng.market.service.lease.resources.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.resources.MarketLeasePostageDTO;
import cn.gdeng.market.enums.StatusEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.resources.MarketLeasePostageService;

/**
 * 资源和合同管理实现
 *
 */
@Service(value = "marketLeasePostageService")
public class MarketLeasePostageServiceImpl implements MarketLeasePostageService {
	
	@Autowired
	private BaseDao<?> baseDao;
	
	/**
	 * 根据条件查询总数
	 * @param paramMap
	 * @return
	 * @throws BizException
	 */
	@Override
	public int findTotalByCondition(Map<String, Object> paramMap) throws BizException {
		return baseDao.queryForObject("MarketLeasePostage.countByCondition", paramMap, Integer.class);
	}
	
	/**
	 * 根据条件分页查询记录
	 * @param paramMap
	 * @return
	 * @throws BizException
	 */
	@Override
	public List<MarketLeasePostageDTO> findPageByCondition(Map<String, Object> paramMap) throws BizException {
		return baseDao.queryForList("MarketLeasePostage.queryLeaseOrderByConditionPage", paramMap, MarketLeasePostageDTO.class);
	}
	
	/**
	 * 根据条件分页查询记录
	 * @param page
	 * @return
	 * @throws BizException
	 */
	@Override
	public GuDengPage<MarketLeasePostageDTO> getPageByCondition(GuDengPage<MarketLeasePostageDTO> page, 
			MarketLeasePostageDTO dto) throws BizException {
		Map<String,Object> paramMap = page.getParaMap();
		paramMap.put("status", StatusEnum.NORMAL.getStatus());
		if (dto.getContractId() != null) {paramMap.put("contractId", dto.getContractId());}
		if (dto.getResourceId() != null) {paramMap.put("resourceId", dto.getResourceId());}
		int count = findTotalByCondition(paramMap);
		page.setTotal(count);
		List<MarketLeasePostageDTO> list;
		if (count > 0) {
			list = findPageByCondition(paramMap);
			page.setRecordList(list);
		}
		return page;
	}

}
