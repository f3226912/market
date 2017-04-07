package cn.gdeng.market.service.lease.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.framework.dba.transaction.annotation.Transactional;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.common.StatisticsDTO;
import cn.gdeng.market.dto.lease.settings.MarketDTO;
import cn.gdeng.market.dto.lease.settings.SummaryDTO;
import cn.gdeng.market.entity.lease.settings.MarketEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.settings.MarketService;
;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author huangbd
 *
 */
@Service(value ="marketService")
public class MarketServiceImpl implements MarketService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public MarketDTO getById(String id) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("Market.getById", map, MarketDTO.class);
	}

	@Override
	public List<MarketDTO> getList(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("Market.getList", map, MarketDTO.class);
	}

	@Override
	public int deleteById(String id) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("Market.deleteById", map);
	}
	
	@Override
	@Transactional
	public int deleteBatch(List<String> list) throws BizException {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("Market.deleteById", batchValues).length;
	}

	@Override
	public int update(MarketDTO t) throws BizException {
		return baseDao.execute("Market.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws BizException {
		return baseDao.queryForObject("Market.getTotal", map, Integer.class);
	}

	@Override
	public Integer insert(MarketDTO entity) throws BizException {
		return baseDao.execute("Market.insert", entity);
	}
	
	@Override
	public Long persist(MarketEntity entity) throws BizException {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<MarketDTO> getListPage(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("Market.getListPage", map, MarketDTO.class);
	}

	@Override
	public GuDengPage<MarketDTO> queryByPage(GuDengPage<MarketDTO> page)  throws BizException{
		Map<String,Object> map = page.getParaMap();
		int count = getTotal(map);
		page.setTotal(count);
		List<MarketDTO> list = null;
		if(count>0){
			list = getListPage(map);
		}
		page.setRecordList(list);
		return page;
	}

	@Override
	public StatisticsDTO marketResourceStatistics(String marketId, String resourceTypeId) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("marketId", marketId);
		map.put("resourceTypeId", resourceTypeId);
		return baseDao.queryForObject("Market.getResourceStatistics", map, StatisticsDTO.class);
	}

	@Override
	public List<MarketDTO> queryListForSelect() throws BizException {
		return baseDao.queryForList("Market.queryListForSelect", null, MarketDTO.class);
	}

	@Override
	public SummaryDTO marketResourceSummary(String marketId, String resourceTypeId) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("marketId", marketId);
		map.put("resourceTypeId", resourceTypeId);
		return baseDao.queryForObject("Market.getResourceStatistics", map, SummaryDTO.class);
	}
}