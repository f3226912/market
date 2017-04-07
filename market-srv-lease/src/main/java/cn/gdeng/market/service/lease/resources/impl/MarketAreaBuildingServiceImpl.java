package cn.gdeng.market.service.lease.resources.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.framework.dba.transaction.annotation.Transactional;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.common.StatisticsDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaBuildingDTO;
import cn.gdeng.market.dto.lease.settings.SummaryNodeDTO;
import cn.gdeng.market.entity.lease.resources.MarketAreaBuildingEntity;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.resources.MarketAreaBuildingService;

/**
 * 楼栋管理 -楼栋新增以及楼栋详情
 * 
 * @author qrxu
 *
 */

@Service(value = "marketAreaBuildingService")
public class MarketAreaBuildingServiceImpl implements MarketAreaBuildingService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GuDengPage<MarketAreaBuildingDTO> queryBuilding(GuDengPage<MarketAreaBuildingDTO> page) throws BizException {
		Map<String, Object> param = page.getParaMap();
		param.put("topOrg", true);
		
		// 查询总页数
		int count = queryTopOrgCount(param);
		page.setTotal(count);
		List<MarketAreaBuildingDTO> list = null;
		if (count > 0) {
			list = baseDao.queryForList("MarketAreaBuilding.queryByConditionPage", param, MarketAreaBuildingDTO.class);
		}
		page.setRecordList(list);

		return page;

	}

	@Override
	public int addAreaBuilding(MarketAreaBuildingEntity entity) throws BizException {
		return baseDao.execute("MarketAreaBuilding.addBuilding", entity);
	}

	@Override
	public List<MarketAreaBuildingDTO> queryAreaByMarketId(int marketId) throws BizException {
	
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("marketId", marketId);
		param.put("status", Const.NORMAL);
		return baseDao.queryForList("MarketAreaBuilding.queryArea", param, MarketAreaBuildingDTO.class);
	}

	@Override
	public MarketAreaBuildingDTO queryAreaBuilding(String id) throws BizException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		return baseDao.queryForObject("MarketAreaBuilding.queryAreaById", param, MarketAreaBuildingDTO.class);
	}

	@Override
	@Transactional
	public int deleteAreaBuilding(MarketAreaBuildingEntity entity) throws BizException {
		//判断当前是否存在资源   不存在则可删除
		int isResource=this.querybuildingRes(entity.getId());
		//当前楼栋已经对应楼栋下面的单元以及楼层进行逻辑删除
		if(isResource==0){
			Map<String,Object> map=new HashMap<>();
			map.put("imageType", 2 );//1代表市场图，2代表区域图，3代表楼层图
			map.put("buildingId",entity.getId());//楼栋id
	        baseDao.execute("MarketImageSetting.delete", map); 
		    baseDao.dynamicMerge(entity);
			deleteUnit(entity.getId());
			return  deleteFloor(entity.getId());
		}
		return -1;
	}

	@Override
	public int queryTopOrgCount(Map<String, Object> param) throws BizException {
		param.put("topOrg", true);
		int count = queryCountByCondition(param);
		return count;
	}

	@Override
	public int queryCountByCondition(Map<String, Object> param) throws BizException {
		int count = baseDao.queryForObject("MarketAreaBuilding.countByCondition", param, Integer.class);
		return count;
	}

	@Override
	public MarketAreaBuildingEntity getById(String id) throws BizException {
		return null;
	}

	@Override
	public Integer insert(MarketAreaBuildingEntity t) throws BizException {
		return null;
	}

	@Override
	public List<MarketAreaBuildingEntity> getList(Map<String, Object> map) throws BizException {
		return null;
	}

	@Override
	public List<MarketAreaBuildingEntity> getListPage(Map<String, Object> map) throws BizException {
		return null;
	}

	@Override
	public int deleteById(String id) throws BizException {
		return 0;
	}

	@Override
	public int deleteBatch(List<String> list) throws BizException {
		return 0;
	}

	@Override
	public int update(MarketAreaBuildingEntity t) throws BizException {
		return 0;
	}

	@Override
	public int getTotal(Map<String, Object> map) throws BizException {
		return  baseDao.queryForObject("MarketAreaBuilding.getTotal", map, Integer.class);
	}

	@Override
	public List<MarketAreaBuildingDTO> queryNature() throws BizException {
		return baseDao.queryForList("MarketAreaBuilding.queryNature", null, MarketAreaBuildingDTO.class);
	}
	
	@Override
	public List<MarketAreaBuildingDTO> getBuilding(Map<String, Object> map ) throws BizException {
		return baseDao.queryForList("MarketAreaBuilding.getBuilding", map, MarketAreaBuildingDTO.class);
	}

	@Override
	public int querybuildingRes(int buildingId) throws BizException {
		Map<String,Object> param=new HashMap<>();
		param.put("buildingId", buildingId);
		return baseDao.queryForObject("MarketAreaBuilding.querybuildingRes", param, Integer.class);
	}

	@Override
	public int deleteUnit(int buildingId) throws BizException {
		Map<String,Object> param=new HashMap<>();
		param.put("buildingId", buildingId);
		return baseDao.execute("MarketAreaBuilding.delUnit", param);
	}

	@Override
	public int deleteFloor(int buildingId) throws BizException {
		Map<String,Object> param=new HashMap<>();
		param.put("buildingId", buildingId);
		return baseDao.execute("MarketAreaBuilding.delFloor", param);
	}

	@Override
	public StatisticsDTO buildingResourceStatistics(Map<String, Object> map) throws BizException {
		return baseDao.queryForObject("MarketAreaBuilding.getResourceStatistics", map, StatisticsDTO.class);
	}

	@Override
	public List<MarketAreaBuildingDTO> queryAll(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("MarketAreaBuilding.queryByCondition", map, MarketAreaBuildingDTO.class);
	}

	@Override
	public MarketAreaBuildingDTO queryBuildingById(String buildingId) throws BizException {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("id", buildingId);
		return baseDao.queryForObject("MarketAreaBuilding.queryBuildingById", paramMap, MarketAreaBuildingDTO.class);
	}

	@Override
	public int editBuilding(MarketAreaBuildingEntity entity) throws BizException {
		return baseDao.dynamicMerge(entity);
	}

	@Override
	public int queryBybuilDetailName(MarketAreaBuildingDTO dto) throws BizException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", dto.getMarketId().toString());
		paramMap.put("name",dto.getName());
		paramMap.put("id",dto.getId());
		return baseDao.queryForObject("MarketAreaBuilding.queryBybuilDetailName", paramMap, Integer.class);
	}

	@Override
	public int queryBybuilDetailCode(MarketAreaBuildingDTO dto) throws BizException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", dto.getMarketId().toString());
		paramMap.put("code",dto.getCode());
		paramMap.put("id",dto.getId());
		return baseDao.queryForObject("MarketAreaBuilding.queryBybuilDetailCode", paramMap, Integer.class);
	}

	@Override
	public List<MarketAreaBuildingDTO> queryBuildingDotByCondition(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("MarketAreaBuilding.queryBuildingDotByCondition", map, MarketAreaBuildingDTO.class);
	}

	@Override
	public SummaryNodeDTO buildingResourceSummary(Map<String, Object> map) throws BizException {
		return baseDao.queryForObject("MarketAreaBuilding.getBuildingResourceStatistics", map, SummaryNodeDTO.class);
	}

	@Override
	public List<MarketAreaBuildingDTO> queryNoDotByCondition(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("MarketAreaBuilding.getNoDotByCondition", map, MarketAreaBuildingDTO.class);
	}

	
}
