package cn.gdeng.market.service.lease.settings.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.framework.dba.transaction.annotation.Transactional;
import com.gudeng.framework.dba.util.DalUtils;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.MarketMeasureSettingDTO;
import cn.gdeng.market.entity.lease.resources.MarketResourceMeasureEntity;
import cn.gdeng.market.entity.lease.settings.MarketExpenditureEntity;
import cn.gdeng.market.entity.lease.settings.MarketMeasureSettingEntity;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.enums.StatusEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.settings.MarketMeasureSettingService;

/**
 * 计量表接口实现
 * @author cai.xu
 *
 */
@Service(value="marketMeasureSettingService")
public class MarketMeasureSettingServiceImpl implements MarketMeasureSettingService {
	@Autowired
	private BaseDao<T> baseDao;
	
	@Override
	public GuDengPage<MarketMeasureSettingDTO> queryListByPage(GuDengPage<MarketMeasureSettingDTO> page) throws BizException {
		Map<String,Object> param = page.getParaMap();
		//查询总页数
		int count = queryCount(param);
		page.setTotal(count);
		List<MarketMeasureSettingDTO> list = null;
		if(count>0){
			list = baseDao.queryForList("MarketMeasureSetting.queryByConditionPage", param, MarketMeasureSettingDTO.class);
		}
		page.setRecordList(list);
		return page;
	}
	
	@Override
	public int queryCount(Map<String, Object> param) throws BizException {
		return baseDao.queryForObject("MarketMeasureSetting.countByCondition", param,Integer.class);
	}

	@Override
	@Transactional
	public int addMeasureSetting(MarketMeasureSettingEntity entity,int marketId) throws BizException {
		//判断计量表编号是否已经存在 
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("code", entity.getCode());
		param.put("marketId",marketId);
		param.put("isDeleted", StatusEnum.NORMAL.getStatus());
		if(queryCount(param) > 0){
			throw new BizException(MsgCons.C_20000, "计量表编号已经存在!");
		}
		long id = baseDao.persist(entity, Long.class);
		
		MarketResourceMeasureEntity rel = new MarketResourceMeasureEntity();
		rel.setMeasureId((int)id);
		rel.setResourceId(entity.getResourceId());
		rel.setStatus(StatusEnum.NORMAL.getStatus());
		rel.setCreateTime(new Date());
		rel.setUpdateTime(new Date());
		rel.setCreateUserId(entity.getCreateUserId());
		rel.setUpdateUserId(entity.getCreateUserId());
		baseDao.persist(rel, long.class);
		return (int)id;
	}
	
	@Override
	@Transactional
	public int editMeasureSetting(MarketMeasureSettingEntity entity,int marketId) throws BizException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("code", entity.getCode());
		param.put("marketId",marketId);
		param.put("isDeleted", StatusEnum.NORMAL.getStatus());
		List<MarketMeasureSettingDTO> settings = baseDao.queryForList("MarketMeasureSetting.queryByCondition", param, MarketMeasureSettingDTO.class);
		for (MarketMeasureSettingDTO setting : settings) {
			if(entity.getId().intValue() != setting.getId().intValue())
			{
				throw new BizException(MsgCons.C_20000, "计量表编号已经存在");
			}
		}
		
		long res = baseDao.dynamicMerge(entity);
		
		MarketResourceMeasureEntity rel = new MarketResourceMeasureEntity();
		rel.setMeasureId(entity.getId());
		rel.setResourceId(entity.getResourceId());
		rel.setUpdateTime(new Date());
		rel.setUpdateUserId(entity.getUpdateUserId());
		int rows = baseDao.execute("MarketMeasureSetting.updateMeasureResource", rel);
		if(rows == 0)
		{
			rel.setCreateTime(new Date());
			rel.setCreateUserId(entity.getUpdateUserId());
			rel.setStatus(StatusEnum.NORMAL.getStatus());
			baseDao.persist(rel,Long.class);
		}
		return (int)res;
	}

	@Override
	public int delMeasureSetting(MarketMeasureSettingEntity entity) throws BizException {
		int res = baseDao.dynamicMerge(entity);
		return res;
	}

	@Override
	public int batchAddMeasureSetting(MarketMeasureSettingDTO entity,int marketId) throws BizException {
		Map<String, Object> param = new HashMap<String, Object>();
		//判断计量表编号是否有重复
		for (int i = entity.getStartNum(); i <= entity.getEndNum(); i++) 
		{
			//判断计量表编号是否已经存在 
			param.put("code", entity.getPrefix()+i);
			param.put("marketId",marketId);
			param.put("isDeleted", StatusEnum.NORMAL.getStatus());
			if(queryCount(param) > 0)
			{
				throw new BizException(MsgCons.C_20000, "计量表编号已经存在!");
			}
		}
		
		int len = entity.getEndNum() - entity.getStartNum() + 1;
		Map<String, Object>[] batchValues = new HashMap[len];
		
		List<MarketMeasureSettingDTO> dtoList = new ArrayList<MarketMeasureSettingDTO>();
		int index = 0;
		for (int i = entity.getStartNum(); i <= entity.getEndNum(); i++) {
			MarketMeasureSettingDTO dto = new MarketMeasureSettingDTO();
			dto.setCode(entity.getPrefix()+i);
			dto.setMeasureTypeId(entity.getMeasureTypeId());
			dto.setResourceId(entity.getResourceId());
			dto.setMaxVal(entity.getMaxVal());
			dto.setStatus(entity.getStatus());
			dto.setCreateUserId(entity.getCreateUserId());
			dto.setUpdateUserId(entity.getUpdateUserId());
			dto.setIsDeleted(StatusEnum.NORMAL.getStatus());
			dto.setExpId(entity.getExpId());
			
			//将对象转换成Map
			batchValues[index] = DalUtils.convertToMap(dto);
			index++;
			dtoList.add(dto);
		}
		return baseDao.batchUpdate("MarketMeasureSetting.batchInsertEntity", batchValues).length;
	}

	@Override
	public int enableOrDisable(String ids, int optFlag) throws BizException {
		int len = ids.split(",").length;
		Map<String, Object>[] batchValues = new HashMap[len];
		List<MarketMeasureSettingDTO> dtoList = new ArrayList<MarketMeasureSettingDTO>();
		for (int i = 0; i < len; i++) {
			MarketMeasureSettingDTO dto = new MarketMeasureSettingDTO();
			MarketMeasureSettingDTO setting = getBySettingId(Integer.valueOf(ids.split(",")[i]));
			if(optFlag == 1){//启用操作
				if(null == setting.getResourceId()){
					throw new BizException(MsgCons.C_20000, "编号为"+setting.getCode()+"的计量表未关联资源不能启用。");
				}
				dto.setStatus(StatusEnum.NORMAL.getStatus());
				dto.setIsDeleted(StatusEnum.NORMAL.getStatus());
			}else if(optFlag == 2){//禁用操作
				if(setting.getLeaseStatus()!=null &&  setting.getLeaseStatus().intValue()==3){
					throw new BizException(MsgCons.C_20000, "编号为"+setting.getCode()+"的计量表已关联已租资源不能停用。");
				}
				dto.setStatus(StatusEnum.DELETE.getStatus());
				dto.setIsDeleted(StatusEnum.NORMAL.getStatus());
			}
			dto.setId(Integer.valueOf(ids.split(",")[i]));
			batchValues[i] = DalUtils.convertToMap(dto);
			dtoList.add(dto);
		}
		return baseDao.batchUpdate("MarketMeasureSetting.batchUpdateEntity", batchValues).length;
	}

	@Override
	public MarketExpenditureEntity findById(int measureTypeId) throws BizException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("measureTypeId", measureTypeId);
		MarketExpenditureEntity res = baseDao.queryForObject("MarketExpenditure.getByMeasureType", param, MarketExpenditureEntity.class);
		return res;
	}

	@Override
	public MarketMeasureSettingDTO getBySettingId(int settingId) throws BizException {
//		queryByCondition
		Map param =new HashMap();
		param.put("id", settingId);
		return baseDao.queryForObject("MarketMeasureSetting.queryByCondition", param, MarketMeasureSettingDTO.class);
	}

}


