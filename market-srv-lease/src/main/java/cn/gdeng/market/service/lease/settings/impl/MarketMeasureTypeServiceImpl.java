package cn.gdeng.market.service.lease.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.MarketMeasureTypeDTO;
import cn.gdeng.market.entity.admin.SysOrgEntity;
import cn.gdeng.market.entity.lease.settings.MarketMeasureTypeEntity;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.enums.StatusEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.settings.MarketMeasureTypeService;
import cn.gdeng.market.util.Assert;

/**
 * 计量表分类接口实现
 * @author cai.xu
 *
 */
@Service(value="marketMeasureTypeService")
public class MarketMeasureTypeServiceImpl implements MarketMeasureTypeService {
	@Autowired
	private BaseDao<SysOrgEntity> baseDao;
	
	@Override
	public GuDengPage<MarketMeasureTypeDTO> queryListByPage(GuDengPage<MarketMeasureTypeDTO> page) throws BizException {
		Map<String,Object> param = page.getParaMap();
		//查询总页数
		int count = queryCount(param);
		page.setTotal(count);
		List<MarketMeasureTypeDTO> list = null;
		if(count>0){
			list = baseDao.queryForList("MarketMeasureType.queryByConditionPage", param, MarketMeasureTypeDTO.class);
		}
		page.setRecordList(list);
		return page;
	}
	
	@Override
	public GuDengPage<MarketMeasureTypeDTO> queryAllList(GuDengPage<MarketMeasureTypeDTO> page) throws BizException {
		Map<String,Object> param = page.getParaMap();
		//查询总页数
		int count = queryCount(param);
		page.setTotal(count);
		List<MarketMeasureTypeDTO> list = null;
		if(count>0){
			list = baseDao.queryForList("MarketMeasureType.queryByCondition", param, MarketMeasureTypeDTO.class);
		}
		page.setRecordList(list);
		return page;
	}
	
	@Override
	public int queryCount(Map<String, Object> param) throws BizException {
		int count = baseDao.queryForObject("MarketMeasureType.countByCondition", param,Integer.class);
		return count;
	}

	@Override
	public int addMeasureType(MarketMeasureTypeEntity entity) throws BizException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("code", entity.getCode());
		param.put("marketId",entity.getMarketId());
		param.put("status", StatusEnum.NORMAL.getStatus());
		//分类编码唯一
		if(queryCount(param) > 0){
			throw new BizException(MsgCons.C_20000, "分类编号已经存在!");
		}
		
		param.clear();
		param.put("name", entity.getName());
		param.put("marketId",entity.getMarketId());
		param.put("status", StatusEnum.NORMAL.getStatus());
		//分类编码唯一
		if(queryCount(param) > 0){
			throw new BizException(MsgCons.C_20000, "分类名称已经存在!");
		}
		long id = baseDao.persist(entity, Long.class);
		return (int)id;
	}

	@Override
	public int delMeasureType(MarketMeasureTypeEntity entity) throws BizException {
		// TODO Auto-generated method stub
		int res = baseDao.dynamicMerge(entity);
		return res;
	}

	@Override
	public int editMeasureType(MarketMeasureTypeEntity entity) throws BizException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", entity.getName());
		param.put("status", StatusEnum.NORMAL.getStatus());
		param.put("marketId",entity.getMarketId());
		List<MarketMeasureTypeDTO> types = baseDao.queryForList("MarketMeasureType.queryByCondition", param, MarketMeasureTypeDTO.class);
		for (MarketMeasureTypeDTO  type: types) {
			if(entity.getId().intValue() != type.getId().intValue())
			{
				throw new BizException(MsgCons.C_20000, "分类名称已经存在!");
			}
		}
		
		param.clear();
		param.put("code", entity.getCode());
		param.put("status", StatusEnum.NORMAL.getStatus());
		param.put("marketId",entity.getMarketId());
		types = baseDao.queryForList("MarketMeasureType.queryByCondition", param, MarketMeasureTypeDTO.class);
		for (MarketMeasureTypeDTO  type: types) {
			if(entity.getId().intValue() != type.getId().intValue())
			{
				throw new BizException(MsgCons.C_20000, "分类编号已经存在!");
			}
		}
		int res = baseDao.dynamicMerge(entity);
		return res;
	}
}
