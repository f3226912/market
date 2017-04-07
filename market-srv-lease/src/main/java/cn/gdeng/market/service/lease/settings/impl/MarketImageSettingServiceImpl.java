package cn.gdeng.market.service.lease.settings.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.MarketImageSettingDTO;
import cn.gdeng.market.entity.admin.SysOrgEntity;
import cn.gdeng.market.entity.lease.settings.MarketImageSettingEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.settings.MarketImageSettingService;

/**
 * 市场平面图接口实现
 * @author youj
 *
 */
@Service(value="marketImageSettingService")
public class MarketImageSettingServiceImpl implements MarketImageSettingService {
	@Autowired
	private BaseDao<SysOrgEntity> baseDao;

	@Override
	public int addMarketImageSetting(MarketImageSettingEntity entity) throws BizException {
		long id = baseDao.persist(entity, Long.class);
		return (int)id;
	}

	@Override
	public GuDengPage<MarketImageSettingDTO> queryMarketImageSettingByCondition(GuDengPage<MarketImageSettingDTO> page)
			throws BizException {
		Map<String,Object> param = page.getParaMap();
		//查询总页数
		int count = queryCount(param);
		page.setTotal(count);
		List<MarketImageSettingDTO> list = null;
		if(count > 0){
			list = baseDao.queryForList("MarketImageSetting.queryByCondition", param, MarketImageSettingDTO.class);
		}
		page.setRecordList(list);
		return page;
	}
	
	@Override
	public int delMarketImageSetting(Map<String, Object> param) throws BizException {
		return baseDao.execute("MarketImageSetting.delete", param);
	}

	@Override
	public int queryCount(Map<String, Object> param) throws BizException {
		int count = baseDao.queryForObject("MarketImageSetting.countByCondition", param, Integer.class);
		return count;
	}

	@Override
	public int updateCoordinate(MarketImageSettingEntity t) throws BizException {
		return baseDao.execute("MarketImageSetting.updateCoordinate", t);
	}

	@Override
	public MarketImageSettingDTO queryByCondition(Map<String, Object> map) throws BizException {
		return baseDao.queryForObject("MarketImageSetting.queryImageSetting", map, MarketImageSettingDTO.class);
	}

}


