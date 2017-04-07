package cn.gdeng.market.service.lease.finance.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.lease.finance.FinanceFeeRecordDTO;
import cn.gdeng.market.entity.lease.finance.FinanceFeeRecordEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.finance.FinanceFeeService;

@Service(value="financeFeeService")
public class FinanceFeeServiceImpl implements FinanceFeeService {

	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public Long insertFeeRecord(FinanceFeeRecordEntity entity) throws BizException {
		return baseDao.persist(entity, Long.class);
	}
	
	@Override
	public Long[] insertFeeRecordList(List<FinanceFeeRecordEntity> entitys) throws BizException {
		if (entitys == null || entitys.isEmpty()){
			return new Long[0];
		}
		int len = entitys.size() ;
		Long[] ids = new Long[len];
		for (int i = 0; i < len; i++) {
			ids[i] = insertFeeRecord(entitys.get(i));
		}
		return ids;
	}

	@Override
	public int updateFeeRecord(FinanceFeeRecordDTO dto) throws BizException {
		return baseDao.execute("FinanceFeeRecord.update", dto) ;
	}

	@Override
	public int updateFeeRecord(Map<String, Object> params) throws BizException {
		return baseDao.execute("FinanceFeeRecord.update", params) ;
	}

	@Override
	public List<FinanceFeeRecordDTO> queryFeeRecord(Map<String, Object> params) throws BizException {
		return baseDao.queryForList("FinanceFeeRecord.getListPage", params, FinanceFeeRecordDTO.class);
	}

	@Override
	public int getFeeRecordCount(Map<String, Object> params) throws BizException {
		return baseDao.queryForObject("FinanceFeeRecord.getTotal", params, Integer.class);
	}

	@Override
	public FinanceFeeRecordDTO queryFeeRecordById(Map<String, Object> params) throws BizException {
		return baseDao.queryForObject("FinanceFeeRecord.getById", params, FinanceFeeRecordDTO.class);
	}

	@Override
	public int updateBycontract(Map<String, Object> params) throws BizException {
		return baseDao.execute("FinanceFeeRecord.updateBycontract", params);
	}

	@Override
	public int updateByContractAndStatus(Map<String, Object> params)
			throws BizException {
		return baseDao.execute("FinanceFeeRecord.updateByContractAndStatus", params);
	}



}
