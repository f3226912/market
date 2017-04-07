package cn.gdeng.market.service.lease.settings.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.framework.dba.transaction.annotation.Transactional;
import com.gudeng.framework.dba.util.DalUtils;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.MarketExpenditureStandardDTO;
import cn.gdeng.market.entity.lease.resources.MarketSectionalChargeEntity;
import cn.gdeng.market.entity.lease.settings.MarketExpenditureStandardEntity;
import cn.gdeng.market.enums.ExpentitureConstant;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.enums.StatusEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.contract.ContractManageService;
import cn.gdeng.market.service.lease.settings.MarketExpenditureStandardService;

@Service(value = "expenditureStandardService")
public class MarkerExpenditureStandardServiceImpl implements MarketExpenditureStandardService {
	
	@Autowired
	private BaseDao<MarketExpenditureStandardEntity> baseDao;
	
	@Resource	
	private ContractManageService contractManageService;

	@Override
	@Transactional
	public Long insert(MarketExpenditureStandardDTO t) throws BizException {
		//唯一校验
		insertValidate(t);
		
		int expType = t.getExpType();
		double chargeAmount = 0d;
		double chargeUnitPrice = 0d;
		double wastageRate = 0d;
		int sectionalCharge = 0;
		int chargeUnit = 0;
		int rentMode = 0;
		//走表类费项
		if(expType == ExpentitureConstant.EXP_TYPE_METER)
		{
			if(t.getWastageRate() != null)
			{
				wastageRate = t.getWastageRate();
			}
			
			sectionalCharge = t.getSectionalCharge();
			chargeUnitPrice = t.getChargeUnitPrice();
		}
		//其他类型费项
		else
		{
			//周期性费项有计费单位
			if(expType == ExpentitureConstant.EXP_TYPE_CYCLE)
			{
				chargeUnit = t.getChargeUnit();
			}
			
			rentMode = t.getRentMode();
			//指定金额
			if(rentMode == ExpentitureConstant.RENT_MODE_AMOUNT)
			{
				chargeAmount = t.getChargeAmount();
			}
			//手工录入
			else if(rentMode == ExpentitureConstant.RENT_MODE_MANUAL)
			{
				
			}
			//按各种面积
			else
			{
				chargeUnitPrice = t.getChargeUnitPrice();
			}
		}
		
		MarketExpenditureStandardEntity entity = new MarketExpenditureStandardEntity();
		entity.setExpId(t.getExpId());
		entity.setCode(t.getCode());
		entity.setName(t.getName());
		entity.setRentMode(t.getRentMode());
		entity.setChargeAmount(chargeAmount);
		entity.setChargeUnit(chargeUnit);
		entity.setChargeUnitPrice(chargeUnitPrice);
		entity.setSectionalCharge(sectionalCharge);
		entity.setWastageRate(wastageRate);
		entity.setStatus(StatusEnum.NORMAL.getStatus());
		entity.setCreateUserId(String.valueOf(t.getUserId()));
		entity.setCreateTime(new Date());
		entity.setUpdateUserId(String.valueOf(t.getUserId()));
		entity.setUpdateTime(new Date());
		
		long expStandardId =  baseDao.persist(entity,Long.class);
		
		if(expType == ExpentitureConstant.EXP_TYPE_METER  && sectionalCharge == 1)
		{
			List<MarketSectionalChargeEntity> list = t.getSecionalCharges();
			//保存分段计费信息
			int len = list.size();
			Map<String, Object>[] batchValues = new HashMap[len];
			for (int i = 0; i < len; i++) 
			{
				MarketSectionalChargeEntity charge = list.get(i);
				charge.setStatus(StatusEnum.NORMAL.getStatus());
				charge.setExpStandardId((int)expStandardId);
				batchValues[i] = DalUtils.convertToMap(charge);
			}

			baseDao.batchUpdate("MarketSectionalCharge.batchInsertEntity", batchValues);
		}
		
		return expStandardId;
	}

	@Override
	@Transactional
	public Number updateExpStandard(MarketExpenditureStandardDTO t) throws BizException {
		//更新之前唯一校验
		updateValidate(t);
		Number row = 0;
		
		int expType = t.getExpType();
		double chargeAmount = 0d;
		double chargeUnitPrice = 0d;
		double wastageRate = 0d;
		int sectionalCharge = 0;
		int chargeUnit = 0;
		int rentMode = 0;
		//走表类费项
		if(expType == ExpentitureConstant.EXP_TYPE_METER)
		{
			if(t.getWastageRate() != null)
			{
				wastageRate = t.getWastageRate();
			}
			sectionalCharge = t.getSectionalCharge();
			chargeUnitPrice = t.getChargeUnitPrice();
		}
		//其他类型费项
		else
		{
			//周期性费项有计费单位
			if(expType == ExpentitureConstant.EXP_TYPE_CYCLE)
			{
				chargeUnit = t.getChargeUnit();
			}
			
			rentMode = t.getRentMode();
			//指定金额
			if(rentMode == ExpentitureConstant.RENT_MODE_AMOUNT)
			{
				chargeAmount = t.getChargeAmount();
			}
			//手工录入
			else if(rentMode == ExpentitureConstant.RENT_MODE_MANUAL)
			{
				
			}
			//按各种面积
			else
			{
				chargeUnitPrice = t.getChargeUnitPrice();
			}
		}
		
		MarketExpenditureStandardEntity entity = new MarketExpenditureStandardEntity();
		entity.setId(t.getId());
		entity.setUpdateUserId(String.valueOf(t.getUserId()));
		entity.setUpdateTime(new Date());
		
		//判断是否关联已结算的合同
		Map<String,Object> contractParam = new HashMap<>();
		contractParam.put("freightBasisId", t.getId());
		contractParam.put("contractStatus", 2);
		int contractCount = contractManageService.countByExpStandard(contractParam);
		//int contractCount = (int)baseDao.queryForObject("ContractMain.countByExpStandard",contractParam,Integer.class);
		//该情况只针对走表类费项的计费标准，将已结算合同关联的计费标准置为逻辑删除，用于保存修改之前的记录，而不是直接进行修改
		if(contractCount >0 && expType == ExpentitureConstant.EXP_TYPE_METER)
		{
			//先逻辑删除旧的数据
			entity.setStatus(StatusEnum.DELETE.getStatus());
			baseDao.dynamicMerge(entity);
			
			//再重新插入一条新的数据
			MarketExpenditureStandardEntity newEntity = new MarketExpenditureStandardEntity();
			newEntity.setExpId(t.getExpId());
			newEntity.setCode(t.getCode());
			newEntity.setName(t.getName());
			newEntity.setRentMode(t.getRentMode());
			newEntity.setChargeAmount(chargeAmount);
			newEntity.setChargeUnit(chargeUnit);
			newEntity.setChargeUnitPrice(chargeUnitPrice);
			newEntity.setSectionalCharge(sectionalCharge);
			newEntity.setWastageRate(wastageRate);
			newEntity.setStatus(StatusEnum.NORMAL.getStatus());
			newEntity.setCreateUserId(String.valueOf(t.getUserId()));
			newEntity.setCreateTime(new Date());
			row = baseDao.persist(newEntity);
		}else
		{
			entity.setExpId(t.getExpId());
			entity.setCode(t.getCode());
			entity.setName(t.getName());
			entity.setRentMode(t.getRentMode());
			entity.setChargeAmount(chargeAmount);
			entity.setChargeUnit(chargeUnit);
			entity.setChargeUnitPrice(chargeUnitPrice);
			entity.setSectionalCharge(sectionalCharge);
			entity.setWastageRate(wastageRate);
			row = baseDao.dynamicMerge(entity);
		}
		
		//删除分段表中的数据
		MarketSectionalChargeEntity param = new MarketSectionalChargeEntity();
		param.setStatus(StatusEnum.DELETE.getStatus());
		param.setExpStandardId(t.getId());
		param.setUpdateUserId(t.getUpdateUserId());
		baseDao.execute("MarketSectionalCharge.deleteByExpStandardId", param);
		if(expType == ExpentitureConstant.EXP_TYPE_METER  && sectionalCharge == 1)
		{
			List<MarketSectionalChargeEntity> list = t.getSecionalCharges();
			//保存分段计费信息
			int len = list.size();
			Map<String, Object>[] batchValues = new HashMap[len];
			for (int i = 0; i < len; i++) 
			{
				MarketSectionalChargeEntity charge = list.get(i);
				charge.setExpStandardId(t.getId());
				charge.setCreateUserId(t.getUpdateUserId());
				charge.setUpdateUserId(t.getUpdateUserId());
				charge.setStatus(StatusEnum.NORMAL.getStatus());
				batchValues[i] = DalUtils.convertToMap(charge);
			}
			//再插入走表类计费标准分段计费信息数据
			baseDao.batchUpdate("MarketSectionalCharge.batchInsertEntity", batchValues);
		}
		return row;
	}
	
	/**
	 * 插入之前唯一校验
	 * @param t
	 * @throws BizException
	 */
	private void insertValidate(MarketExpenditureStandardDTO t) throws BizException
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("marketId", t.getMarketId());
		param.put("code", t.getCode());
		param.put("status", StatusEnum.NORMAL.getStatus());
		//计费标准编码唯一
		if(queryCountByCondition(param) > 0){
			throw new BizException(MsgCons.C_30004, MsgCons.M_30004);
		}
		
		param.clear();
		param.put("marketId", t.getMarketId());
		param.put("name", t.getName());
		param.put("status", StatusEnum.NORMAL.getStatus());
		//计费标准名称唯一
		if(queryCountByCondition(param) > 0){
			throw new BizException(MsgCons.C_30005, MsgCons.M_30005);
		}
	}
	
	/**
	 * 更新之前唯一校验
	 * @param t
	 * @throws BizException
	 */
	private void updateValidate(MarketExpenditureStandardDTO t) throws BizException
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("marketId", t.getMarketId());
		param.put("code", t.getCode());
		param.put("status", StatusEnum.NORMAL.getStatus());
		List<MarketExpenditureStandardDTO> listByCode = baseDao.queryForList("MarketExpenditureStandard.queryByCondition", param, MarketExpenditureStandardDTO.class);
		for (MarketExpenditureStandardDTO marketExpenditureStandardDTO : listByCode) {
			if(t.getId().intValue() != marketExpenditureStandardDTO.getId().intValue())
			{
				throw new BizException(MsgCons.C_30004, MsgCons.M_30004);
			}
		}
		
		param.clear();
		param.put("marketId", t.getMarketId());
		param.put("name", t.getName());
		param.put("status", StatusEnum.NORMAL.getStatus());
		List<MarketExpenditureStandardDTO> listByName = baseDao.queryForList("MarketExpenditureStandard.queryByCondition", param, MarketExpenditureStandardDTO.class);
		for (MarketExpenditureStandardDTO marketExpenditureStandardDTO : listByName) {
			if(t.getId().intValue() != marketExpenditureStandardDTO.getId().intValue())
			{
				throw new BizException(MsgCons.C_30005, MsgCons.M_30005);
			}
		}
	}
	@Override
	public List<MarketExpenditureStandardDTO> getByExpId(int expId) throws BizException {
		MarketExpenditureStandardEntity param = new MarketExpenditureStandardEntity();
		param.setExpId(expId);
		return baseDao.queryForList("MarketExpenditureStandard.queryByCondition", param, MarketExpenditureStandardDTO.class);
	}

	@Override
	public GuDengPage<MarketExpenditureStandardDTO> queryByPage(GuDengPage<MarketExpenditureStandardDTO> page)
			throws BizException {
		Map<String,Object> param = page.getParaMap();
		int count = queryCountByCondition(param);
		page.setTotal(count);
		
		List<MarketExpenditureStandardDTO> list = null;
		if(count>0)
		{
			list = baseDao.queryForList("MarketExpenditureStandard.queryByConditionPage", param, MarketExpenditureStandardDTO.class);
		}
		
		page.setRecordList(list);
		return page;
	}

	@Override
	public int queryCountByCondition(Map<String, Object> param) throws BizException {
		return baseDao.queryForObject("MarketExpenditureStandard.countByCondition", param, Integer.class);
	}

	@Override
	public MarketExpenditureStandardDTO getById(int id) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		MarketExpenditureStandardDTO data = baseDao.queryForObject("MarketExpenditureStandard.getById", map, MarketExpenditureStandardDTO.class);
		
		int sectionalCharge = data.getSectionalCharge();
		if(sectionalCharge == ExpentitureConstant.SECTIONAL_CHARGE)
		{
			MarketSectionalChargeEntity param = new MarketSectionalChargeEntity();
			param.setExpStandardId(id);
			List<MarketSectionalChargeEntity> list = baseDao.queryForList("MarketSectionalCharge.queryByCondition", param, MarketSectionalChargeEntity.class);
			data.setSecionalCharges(list);
		}
		return data;
	}
	
	@Override
	public int deleteById(MarketExpenditureStandardEntity entity) throws BizException {
		return baseDao.dynamicMerge(entity);
	}
	
}
