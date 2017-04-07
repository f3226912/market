package cn.gdeng.market.service.lease.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.MarketExpenditureDTO;
import cn.gdeng.market.dto.lease.settings.MarketExpenditureStandardDTO;
import cn.gdeng.market.entity.lease.settings.MarketExpenditureEntity;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.enums.StatusEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.settings.MarketExpenditureService;

@Service(value = "expenditureService")
public class MarkerExpenditureServiceImpl implements MarketExpenditureService {
	
	@Autowired
	private BaseDao<MarketExpenditureEntity> baseDao;

	@Override
	public MarketExpenditureDTO getById(int id) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("MarketExpenditure.getById", map, MarketExpenditureDTO.class);
	}

	@Override
	public Long insert(MarketExpenditureEntity t) throws BizException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", t.getName());
		param.put("marketId", t.getMarketId());
		param.put("status", StatusEnum.NORMAL.getStatus());
		//费项名称唯一
		if(queryCountByCondition(param) > 0){
			throw new BizException(MsgCons.C_30003, MsgCons.M_30003);
		}
		return baseDao.persist(t,Long.class);
	}

	@Override
	public GuDengPage<MarketExpenditureDTO> queryByPage(GuDengPage<MarketExpenditureDTO> page) throws BizException {
		Map<String,Object> param = page.getParaMap();
		
		//查询总记录数
		int count = queryCountByCondition(param);
		page.setTotal(count);
		
		List<MarketExpenditureDTO> list = null;
		if(count>0){
			list = baseDao.queryForList("MarketExpenditure.queryByConditionPage", param, MarketExpenditureDTO.class);
			
			//查询每个费项的计费标准名称
			Map<String,Object> paramMap = new HashMap<>();
			for (MarketExpenditureDTO marketdExpenditureDTO : list) {
				paramMap.put("expId", marketdExpenditureDTO.getId());
				paramMap.put("status", StatusEnum.NORMAL.getStatus());
				List<String> expStandardNames = baseDao.queryForList("MarketExpenditureStandard.queryExpStandardName", paramMap, String.class);
				marketdExpenditureDTO.setCountExpStrandard(expStandardNames.size());
			}
		}
		page.setRecordList(list);
		return page;
	}
	
	@Override
	public int queryCountByCondition(Map<String, Object> param) throws BizException {
		return baseDao.queryForObject("MarketExpenditure.countByCondition", param,Integer.class);
	}
	
	@Override
	public Map<String,Object> queryAllExpList(Map<String,Object> paramMap) throws BizException
	{
		Map<String,Object> result = new HashMap<>();
		//获取该市场下的所有费项的类型分组
		List<Map<String,Object>> expTypes = getExpTypes(paramMap);
		for (Map<String,Object> map : expTypes) 
		{
			int expTypeId = Integer.parseInt(String.valueOf(map.get("expTypeId")));
			String expTypeName = String.valueOf(map.get("expTypeName"));
			paramMap.put("expType", expTypeId);
			//根据类型获取费项列表
			List<MarketExpenditureDTO> exps = baseDao.queryForList("MarketExpenditure.queryByCondition", paramMap, MarketExpenditureDTO.class);
			
			Map<String,Object> expStandardParams = new HashMap<>();
			for (MarketExpenditureDTO exp : exps) 
			{
				//根据费项ID和stauts获取所有计费标准列表
				expStandardParams.put("expId", exp.getId());
				expStandardParams.put("status", StatusEnum.NORMAL.getStatus());
				
				List<MarketExpenditureStandardDTO> expStandards = baseDao.queryForList("MarketExpenditureStandard.queryByCondition", expStandardParams,  MarketExpenditureStandardDTO.class);
				if(expStandards !=null && expStandards.size()>0)
				{
					exp.setExpStandards(expStandards);
				}
			}
			
			result.put(expTypeName, exps);
		}
		return result;
	}	
	
	@Override
	public List<Map<String,Object>> getExpTypes(Map<String,Object> paramMap) throws BizException {
		return baseDao.queryForList("MarketExpenditure.getExpTypes", paramMap);
	}

	@Override
	public int updateExpenditure(MarketExpenditureEntity t) throws BizException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", t.getName());
		param.put("marketId", t.getMarketId());
		param.put("status", StatusEnum.NORMAL.getStatus());
		List<MarketExpenditureDTO> exps = baseDao.queryForList("MarketExpenditure.queryByCondition", param, MarketExpenditureDTO.class);
		for (MarketExpenditureDTO marketExpenditureDTO : exps) {
			if(t.getId().intValue() != marketExpenditureDTO.getId().intValue())
			{
				throw new BizException(MsgCons.C_30003, MsgCons.M_30003);
			}
		}
		
		return baseDao.dynamicMerge(t);
	}

	@Override
	public int deleteExpenditure(MarketExpenditureEntity entity)throws BizException {
		return baseDao.dynamicMerge(entity);
	}

	@Override
	public List<MarketExpenditureDTO> getExpList(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("MarketExpenditure.queryByCondition", map, MarketExpenditureDTO.class);
	}

	@Override
	public List<MarketExpenditureDTO> getExpList(MarketExpenditureDTO param) throws BizException {
		return baseDao.queryForList("MarketExpenditure.queryByCondition", param, MarketExpenditureDTO.class);
	}
	
	@Override
	public MarketExpenditureDTO queryByMarketIdParentId(Map<String,Object> map) throws BizException {
		return baseDao.queryForObject("MarketExpenditure.queryByMarketIdParentId", map, MarketExpenditureDTO.class);
	}
	
	@Override
	public List<MarketExpenditureDTO> querySysTypeList(Map<String,Object> map) throws BizException {
		return baseDao.queryForList("MarketExpenditure.querySysTypeList", map, MarketExpenditureDTO.class);
	}

	@Override
	public MarketExpenditureDTO getByParentId(Map<String, Object> map) {
		return baseDao.queryForObject("MarketExpenditure.getByParentId", map, MarketExpenditureDTO.class);
	}
}
