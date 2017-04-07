package cn.gdeng.market.service.admin.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gudeng.framework.dba.util.DalUtils;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.admin.SysMessageDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.entity.admin.SysMessageEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.admin.SysMessageService;

@Service("sysMessageService")
public class SysMessageServiceImpl implements SysMessageService{
	
	@Resource
	private BaseDao<SysMessageEntity> baseDao;

	@Override
	public GuDengPage<SysMessageDTO> getMessage4Page(GuDengPage<SysMessageDTO> page) throws BizException {
		
		Map<String,Object> params = page.getParaMap();
		int total = baseDao.queryForObject("SysMessage.countByCondition", params, Integer.class);
		List<SysMessageDTO> list = null;
		if(total > 0){
			list = baseDao.queryForList("SysMessage.queryByConditionPage", params,SysMessageDTO.class);
		}
		page.setTotal(total);
		page.setRecordList(list);
		return page;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void batchSaveSysMsg(List<SysMessageEntity> entities) throws BizException {
		int len = entities.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			SysMessageEntity entity = entities.get(i);
			batchValues[i] = DalUtils.convertToMap(entity);
		}
		baseDao.batchUpdate("SysMessage.insert", batchValues);
	}

	@Override
	public int getTotalByCondition(Map<String, Object> params) throws BizException {
		
		return baseDao.queryForObject("SysMessage.countByCondition", params, Integer.class);
	}

	@Override
	public void dynamicUpdate(SysMessageEntity entity) {
		
		baseDao.dynamicMerge(entity);
	}

}
