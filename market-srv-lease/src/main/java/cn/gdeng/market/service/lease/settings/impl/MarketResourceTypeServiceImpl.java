package cn.gdeng.market.service.lease.settings.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.framework.dba.util.DalUtils;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.MarketResourceTypeDTO;
import cn.gdeng.market.entity.lease.settings.MarketResourceTypeEntity;
import cn.gdeng.market.enums.StatusEnum;
import cn.gdeng.market.enums.SystemTypeEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.settings.MarketResourceTypeService;

/**
 * 资源类型实现
 *
 */

@Service(value = "marketResourceTypeService")
public class MarketResourceTypeServiceImpl implements MarketResourceTypeService {
	
	@Autowired
	private BaseDao<MarketResourceTypeEntity> baseDao;

	/**
	 * 新增资源类型
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	@Override
	public boolean insert(MarketResourceTypeEntity entity) throws BizException {
		entity.setCreateTime(new Date());
		entity.setSysType(SystemTypeEnum.CUSTOMER.getStatus());
		long primaryKey = baseDao.persist(entity, Long.class);
		return primaryKey > 0 ? true : false;
	}

	/**
	 * 修改资源类型
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	@Override
	public boolean update(MarketResourceTypeEntity entity) throws BizException {
		entity.setUpdateTime(new Date());
		return baseDao.dynamicMerge(entity) == 1 ? true : false;
	}

	/**
	 * 逻辑删除资源类型
	 * @param id
	 * @param operatorId
	 * @return
	 * @throws BizException
	 */
	@Override
	public boolean delete(int id, String operatorId) throws BizException {
		MarketResourceTypeEntity entity = new MarketResourceTypeEntity();
		entity.setId(id);
		entity.setStatus(StatusEnum.DELETE.getStatus());
		entity.setUpdateTime(new Date());
		entity.setUpdateUserId(operatorId);
		return baseDao.dynamicMerge(entity) == 1 ? true : false;
	}

	/**
	 * 根据资源类型编码查询总数
	 * @param code
	 * @return
	 * @throws BizException
	 */
	@Override
	public int selCountByCode(String code) throws BizException {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("code", code);
		return baseDao.queryForObject("MarketResourceType.selCountByCode", paramMap, Integer.class);
	}
	
	/**
	 * 根据id和资源类型编码查询总数
	 * @param marketId
	 * @param id
	 * @param code
	 * @return
	 * @throws BizException
	 */
	public int selCountByIdAndCodeAndMarketId(Integer marketId, int id, String code) throws BizException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("status", StatusEnum.NORMAL.getStatus());
		paramMap.put("marketId", marketId);
		paramMap.put("id", id);
		paramMap.put("code", code);
		return baseDao.queryForObject("MarketResourceType.selCountByIdAndCodeAndMarketId", paramMap, Integer.class);
	}
	
	/**
	 * 根据id和资源类型名称查询总数
	 * @param marketId
	 * @param id
	 * @param name
	 * @return
	 * @throws BizException
	 */
	public int selCountByIdAndNameAndMarketId(Integer marketId, int id, String name) throws BizException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("status", StatusEnum.NORMAL.getStatus());
		paramMap.put("marketId", marketId);
		paramMap.put("id", id);
		paramMap.put("name", name);
		return baseDao.queryForObject("MarketResourceType.selCountByIdAndNameAndMarketId", paramMap, Integer.class);
	}
	
	/**
	 * 根据id和资源类型序号查询总数
	 * @param marketId
	 * @param id
	 * @param sort
	 * @return
	 * @throws BizException
	 */
	public int selCountByIdAndSortAndMarketId(Integer marketId, int id, int sort) throws BizException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("status", StatusEnum.NORMAL.getStatus());
		paramMap.put("marketId", marketId);
		paramMap.put("id", id);
		paramMap.put("sort", sort);
		return baseDao.queryForObject("MarketResourceType.selCountByIdAndSortAndMarketId", paramMap, Integer.class);
	}
	
	/**
	 * 根据条件查询总数
	 * @param params
	 * @return
	 * @throws BizException
	 */
	@Override
	public int selCountByCondition(Map<String, Object> paramMap) throws BizException {
		return baseDao.queryForObject("MarketResourceType.countByCondition", paramMap, Integer.class);
	}

	/**
	 * 根据条件分页查询
	 * @param page
	 * @param dto
	 * @return GuDengPage<MarketResourceTypeDTO>
	 * @throws BizException
	 */
	@Override
	public GuDengPage<MarketResourceTypeDTO> selByPage(GuDengPage<MarketResourceTypeDTO> page, 
			MarketResourceTypeDTO dto) throws BizException {
		Map<String,Object> paramMap = page.getParaMap();
		paramMap.put("status", StatusEnum.NORMAL.getStatus());
		if (dto.getCode() != null) {paramMap.put("code", dto.getCode());}
		if (dto.getName() != null) {paramMap.put("name", dto.getName());}
		if (dto.getStatus() != null) {paramMap.put("status", dto.getStatus());}
		if (dto.getSysType() != null) {paramMap.put("sysType", dto.getSysType());}
		if (dto.getMarketId() != null) {paramMap.put("marketId", dto.getMarketId());}
		int count = selCountByCondition(paramMap);
		page.setTotal(count);
		List<MarketResourceTypeDTO> list = null;
		if (count > 0) {
			list = baseDao.queryForList("MarketResourceType.queryByConditionPage", paramMap, MarketResourceTypeDTO.class);
		}
		page.setRecordList(list);
		return page;
	}
	
	/**
	 * 根据条件分页查询
	 * @param paramMap
	 * @return List<MarketResourceTypeDTO>
	 * @throws BizException
	 */
	@Override
	public List<MarketResourceTypeDTO> selByPage(Map<String,Object> paramMap) throws BizException {
		List<MarketResourceTypeDTO> list = baseDao.queryForList("MarketResourceType.queryByConditionPage", paramMap, MarketResourceTypeDTO.class);
		return list.isEmpty() ? null : list;
	}

	/**
	 * 根据条件查询所有资源类型
	 * @param dto
	 * @return
	 * @throws BizException
	 */
	@Override
	public List<MarketResourceTypeDTO> selAllByCondition(MarketResourceTypeDTO dto) throws BizException {
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("status", StatusEnum.NORMAL.getStatus());
		if (dto.getCode() != null) {paramMap.put("code", dto.getCode());}
		if (dto.getName() != null) {paramMap.put("name", dto.getName());}
		if (dto.getStatus() != null) {paramMap.put("status", dto.getStatus());}
		if (dto.getSysType() != null) {paramMap.put("sysType", dto.getSysType());}
		if (dto.getMarketId() != null) {paramMap.put("marketId", dto.getMarketId());}
		if (dto.getSort() != null) {paramMap.put("sort", dto.getSort());}
		List<MarketResourceTypeDTO> list = baseDao.queryForList("MarketResourceType.queryByCondition", paramMap, MarketResourceTypeDTO.class);
		return list.isEmpty() ? null : list;
	}

	@Override
	public List<MarketResourceTypeDTO> queryByMarket(String marketId) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("marketId", marketId);
		return baseDao.queryForList("MarketResourceType.getByMarketId", map, MarketResourceTypeDTO.class);
	}

	@Override
	public List<MarketResourceTypeDTO> getMarketResourceType(Map<String, Object> map) throws BizException {
		List<MarketResourceTypeDTO> list = baseDao.queryForList("MarketResourceType.getMarketResourceType", map, MarketResourceTypeDTO.class);
		return list;
	}

	/**
	 * 根据id获取资源类型
	 * @param id
	 * @return
	 * @throws BizException
	 */
	@Override
	public MarketResourceTypeDTO selMarketResTypeById(Integer id) throws BizException {
		Map<String, Integer> paramMap = new HashMap<>();
		paramMap.put("id", id);
		return baseDao.queryForObject("MarketResourceType.selMarketResourceTypeById", paramMap, MarketResourceTypeDTO.class);
	}

	/**
	 * 批量删除
	 * @param idArray
	 * @param operatorId
	 * @return
	 * @throws BizException
	 */
	@Override
	public boolean batchDelete(int[] idArray, String operatorId) throws BizException {
		int length = idArray.length;
		@SuppressWarnings("unchecked")
		Map<String, Object>[] batchValues = new HashMap[length];
		MarketResourceTypeEntity entity = new MarketResourceTypeEntity();
		for (int i = 0; i < length; i++) {
			entity.setId(idArray[i]);
			entity.setStatus(StatusEnum.DELETE.getStatus());
			entity.setUpdateTime(new Date());
			entity.setUpdateUserId(operatorId);
			batchValues[i] = DalUtils.convertToMap(entity);
		}
		int successNum = baseDao.batchUpdate("MarketResourceType.update", batchValues).length;
		return successNum != length ? false : true;
	}

	@Override
	public int queryByMarketIdParentId(Map<String, Object> map) throws BizException {
		return baseDao.queryForObject("MarketResourceType.queryByMarketIdParentId", map, Integer.class);
	}

}
