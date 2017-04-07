package cn.gdeng.market.service.lease.resources.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.lease.common.StatisticsDTO;
import cn.gdeng.market.dto.lease.resources.MarketUnitFloorDTO;
import cn.gdeng.market.dto.lease.settings.SummaryDTO;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.resources.MarketUnitFloorService;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author bdhuang
 *
 */
@Service(value ="marketUnitFloorService")
public class MarketUnitFloorServiceImpl implements MarketUnitFloorService {
	
	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public List<MarketUnitFloorDTO> getFloor(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("MarketUnitFloor.getList", map, MarketUnitFloorDTO.class);
	}

	@Override
	public StatisticsDTO floorResourceStatistics(String floorId, String resourceTypeId) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("floorId", floorId);
		map.put("resourceTypeId", resourceTypeId);
		return baseDao.queryForObject("MarketUnitFloor.getResourceStatistics", map, StatisticsDTO.class);
	}

	@Override
	public MarketUnitFloorDTO getById(String id) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer insert(MarketUnitFloorDTO t) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MarketUnitFloorDTO> getList(Map<String, Object> map) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MarketUnitFloorDTO> getListPage(Map<String, Object> map) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(String id) throws BizException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBatch(List<String> list) throws BizException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(MarketUnitFloorDTO t) throws BizException {
		return baseDao.execute("MarketUnitFloor.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws BizException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SummaryDTO floorResourceSummary(String floorId, String resourceTypeId) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("floorId", floorId);
		map.put("resourceTypeId", resourceTypeId);
		return baseDao.queryForObject("MarketUnitFloor.getResourceStatistics", map, SummaryDTO.class);
	}
}