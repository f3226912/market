package cn.gdeng.market.service.lease.settings.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.GaugeChargeRecordDTO;
import cn.gdeng.market.entity.admin.SysOrgEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.settings.GaugeChargeRecordService;

@Service(value="gaugeChargeRecordService")
public class GaugeChargeRecordServiceImpl implements GaugeChargeRecordService {

	@Autowired
	private BaseDao<SysOrgEntity> baseDao;
	
	@Override
	public GuDengPage<GaugeChargeRecordDTO> queryList(GuDengPage<GaugeChargeRecordDTO> page) throws BizException {
		Map<String,Object> param = page.getParaMap();
		//查询总页数
		int count = queryCount(param);
		page.setTotal(count);
		List<GaugeChargeRecordDTO> list = null;
		if(count>0){
			list = baseDao.queryForList("GaugeChargeRecord.queryByConditionPage", param, GaugeChargeRecordDTO.class);
		}
		page.setRecordList(list);
		return page;
	}
	
	@Override
	public int queryCount(Map<String, Object> param) throws BizException {
		int count = baseDao.queryForObject("GaugeChargeRecord.countByCondition", param,Integer.class);
		return count;
	}
}
