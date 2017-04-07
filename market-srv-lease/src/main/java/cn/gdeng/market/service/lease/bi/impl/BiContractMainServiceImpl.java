package cn.gdeng.market.service.lease.bi.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.bi.BiContractMainDTO;
import cn.gdeng.market.entity.lease.contract.ContractMainEntity;
import cn.gdeng.market.enums.StatusEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.bi.BiContractMainService;
@Service(value="biContractMainService")
public class BiContractMainServiceImpl implements BiContractMainService {

	@Autowired
	private BaseDao<ContractMainEntity> baseDao;

	@Override
	public GuDengPage<BiContractMainDTO> getContractMainList(GuDengPage<BiContractMainDTO> page,BiContractMainDTO dto)throws BizException  {

		Map<String,Object> paramMap = page.getParaMap();
		paramMap.put("status", StatusEnum.NORMAL.getStatus());
		if (dto.getContractNo() != null) {paramMap.put("contractNo", dto.getContractNo());}
		if (dto.getContractStatus() != null) {paramMap.put("contractStatus", dto.getContractStatus());}
		if (dto.getLeasingResource() != null) {paramMap.put("leasingResource", dto.getLeasingResource());}
		if (dto.getType() != null) {paramMap.put("type", dto.getType());}
		if (dto.getMarketId() != null) {paramMap.put("marketId", dto.getMarketId());}
		int count = countTotal(paramMap);
		page.setTotal(count);
		List<BiContractMainDTO> list = null;
		if (count > 0) {
			list = baseDao.queryForList("ContractMain.getContractMainList", paramMap, BiContractMainDTO.class);
		}
		page.setRecordList(list);
		return page;
	
	}

	@Override
	public int countTotal(Map<String, Object> paramMap)throws BizException {
		return 	baseDao.queryForObject("ContractMain.countContractMainList",paramMap,Integer.class);
	}

	@Override
	public List<BiContractMainDTO> getContractMainList(Map<String, Object> paramMap) throws BizException {
		return  baseDao.queryForList("ContractMain.getContractMainList", paramMap, BiContractMainDTO.class);
	}

}
