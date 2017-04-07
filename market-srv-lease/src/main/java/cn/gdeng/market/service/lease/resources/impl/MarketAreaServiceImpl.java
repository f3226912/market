package cn.gdeng.market.service.lease.resources.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.framework.dba.transaction.annotation.Transactional;
import com.gudeng.framework.dba.util.DalUtils;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.common.StatisticsDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaBuildingDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaDTO;
import cn.gdeng.market.dto.lease.settings.SummaryNodeDTO;
import cn.gdeng.market.entity.lease.resources.MarketAreaEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.resources.MarketAreaService;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author bdhuang
 *
 */
@Service(value ="marketAreaService")
public class MarketAreaServiceImpl implements MarketAreaService {
	
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public MarketAreaDTO getById(String id) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("MarketArea.getById", map, MarketAreaDTO.class);
	}

	@Override
	public List<MarketAreaDTO> getList(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("MarketArea.getList", map, MarketAreaDTO.class);
	}

	@Override
	@Transactional
	public int deleteById(String id) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		int count = baseDao.execute("MarketArea.deleteById", map);
		map.clear();
		map.put("imageType", 1 );//1代表市场图，2代表区域图，3代表楼层图
		map.put("areaId",id);//区域id
		baseDao.execute("MarketImageSetting.delete", map);//删除市场图下，该区域的描点
		return count;
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
		return baseDao.batchUpdate("MarketArea.deleteById", batchValues).length;
	}

	@Override
	public int update(MarketAreaDTO t) throws BizException {
		return baseDao.execute("MarketArea.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws BizException {
		return baseDao.queryForObject("MarketArea.getTotal", map, Integer.class);
	}

	@Override
	public Integer insert(MarketAreaDTO entity) throws BizException {
		return baseDao.execute("MarketArea.insert", entity);
	}
	
	@Override
	public Long persist(MarketAreaEntity entity) throws BizException {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<MarketAreaDTO> getListPage(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("MarketArea.getListPage", map, MarketAreaDTO.class);
	}

	@Override
	public GuDengPage<MarketAreaDTO> queryByPage(GuDengPage<MarketAreaDTO> page) throws BizException {
		Map<String,Object> map = page.getParaMap();
		int count = getTotal(map);
		page.setTotal(count);
		List<MarketAreaDTO> list = null;
		if(count>0){
			list = getListPage(map);
		}
		page.setRecordList(list);
		return page;
	}

	@Override
	public int insertBatch(List<MarketAreaDTO> areas) throws BizException {
		int len = areas.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			MarketAreaDTO entity = areas.get(i);
			batchValues[i] = DalUtils.convertToMap(entity);
		}
		return baseDao.batchUpdate("MarketArea.insert", batchValues).length;
	}

	@Override
	public List<MarketAreaDTO> queryByMarket(String marketId) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("marketId", marketId);
		return baseDao.queryForList("MarketArea.getByMarketId", map, MarketAreaDTO.class);
	}

	@Override
	public StatisticsDTO areaResourceStatistics(String areaId, String resourceTypeId, boolean isDrawedDot) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", areaId);
		map.put("resourceTypeId", resourceTypeId);
		map.put("isDrawedDot", isDrawedDot);
		return baseDao.queryForObject("MarketArea.getResourceStatistics", map, StatisticsDTO.class);
	}
		
	@Override
	public List<MarketAreaDTO> getAreaAndBuilding(Map<String, Object> map) throws BizException {
		List<MarketAreaDTO> list=baseDao.queryForList("MarketArea.getList", map, MarketAreaDTO.class);
		if(list!=null){
			for(MarketAreaDTO dto:list){
				map.clear();
				map.put("areaId", dto.getId());
				List<MarketAreaBuildingDTO> buildings = baseDao.queryForList("MarketAreaBuilding.getBuilding", map, MarketAreaBuildingDTO.class);
				dto.setBuildings(buildings);
			}
		}
		return list;
	}

	@Override
	public GuDengPage<MarketAreaDTO> queryAll(GuDengPage<MarketAreaDTO> page) throws BizException {
		// TODO Auto-generated method stub
		List<MarketAreaDTO> list = baseDao.queryForList("MarketArea.getList", page.getParaMap(), MarketAreaDTO.class);
		page.setRecordList(list);
		return page;
	}

	@Override
	public Integer ifExistCode(MarketAreaDTO dto) throws BizException {
		Integer ifExistCode = baseDao.queryForObject("MarketArea.ifExistCode", dto, Integer.class);
		return ifExistCode;
	}
	@Override
	public Integer ifExistName(MarketAreaDTO dto) throws BizException {
		Integer ifExistName = baseDao.queryForObject("MarketArea.ifExistName", dto, Integer.class);
		return ifExistName;
	}

	@Override
	public List<MarketAreaDTO> queryAreaDotByMarket(String marketId) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("marketId", marketId);
		return baseDao.queryForList("MarketArea.getAreaDotByMarketId", map, MarketAreaDTO.class);
	}

	@Override
	public SummaryNodeDTO areaResourceSummary(String areaId, String resourceTypeId, boolean isDrawedDot)
			throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", areaId);
		map.put("resourceTypeId", resourceTypeId);
		map.put("isDrawedDot", isDrawedDot);
		return baseDao.queryForObject("MarketArea.getResourceStatistics", map, SummaryNodeDTO.class);
	}

	@Override
	public List<MarketAreaDTO> queryNoDotByMarket(String marketId) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("marketId", marketId);
		return baseDao.queryForList("MarketArea.getNoDotByMarket", map, MarketAreaDTO.class);
	}
}