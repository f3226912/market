package cn.gdeng.market.service.lease.finance.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.lease.finance.FinanceFeeReceivedDTO;
import cn.gdeng.market.entity.lease.finance.FinanceFeeReceivedEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.finance.FinanceFeeReceivedService;

@Service(value="financeFeeReceivedService")
public class FinanceFeeReceivedServiceImpl implements FinanceFeeReceivedService {

	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public Long insertFeeReceivedRecord(FinanceFeeReceivedEntity entity) throws BizException {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public FinanceFeeReceivedDTO queryFeeReceivedRecordById(Map<String, Object> params) throws BizException {
		return baseDao.queryForObject("FinanceFeeReceived.getById", params, FinanceFeeReceivedDTO.class);
	}

	@Override
	public int getFeeReceivedRecordCount(Map<String, Object> params) throws BizException {
		return baseDao.queryForObject("FinanceFeeReceived.getTotal", params, Integer.class);
	}
	
	@Override
	public List<FinanceFeeReceivedDTO> queryFeeReceivedRecord(Map<String, Object> params) throws BizException {
		return baseDao.queryForList("FinanceFeeReceived.getListPage", params, FinanceFeeReceivedDTO.class);
	}


}
