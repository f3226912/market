package cn.gdeng.market.service.lease.resources.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.resources.MarketResourceDTO;
import cn.gdeng.market.dto.lease.resources.MarketResourceMeasureUnionDTO;
import cn.gdeng.market.dto.lease.resources.MarketResourcePrintDTO;
import cn.gdeng.market.dto.lease.settings.ResourceContractDTO;
import cn.gdeng.market.entity.lease.resources.MarketResourceEntity;
import cn.gdeng.market.enums.MarketResourceLeaseStatusEnum;
import cn.gdeng.market.enums.StatusEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.resources.MarketResourceService;

import com.gudeng.framework.dba.transaction.annotation.Transactional;
import com.gudeng.framework.dba.util.DalUtils;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author weiwenke
 *
 */
@Service(value ="marketResourceService")
public class MarketResourceServiceImpl implements MarketResourceService {

	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public int selCountByCondition(Map<String, Object> paramMap) throws BizException {
		return baseDao.queryForObject("MarketResource.countByCondition", paramMap, Integer.class);
	}

	@Override
	public GuDengPage<MarketResourceDTO> selByPage(GuDengPage<MarketResourceDTO> page, MarketResourceDTO dto)
			throws BizException {
		Map<String,Object> paramMap = page.getParaMap();
		paramMap.put("status", StatusEnum.NORMAL.getStatus());
		if (dto.getLeaseStatus() != null) {paramMap.put("leaseStatus", dto.getLeaseStatus());}
		if (dto.getAreaId() != null) {paramMap.put("areaId", dto.getAreaId());}
		if (dto.getBudingId() != null) {paramMap.put("budingId", dto.getBudingId());}
		if (dto.getResourceTypeId() != null) {paramMap.put("resourceTypeId", dto.getResourceTypeId());}
		if (dto.getName() != null) {paramMap.put("name", dto.getName());}
		int count = selCountByCondition(paramMap);
		page.setTotal(count);
		List<MarketResourceDTO> list = null;
		if (count > 0) {
			list = baseDao.queryForList("MarketResource.queryByConditionPage", paramMap, MarketResourceDTO.class);
		}
		page.setRecordList(list);
		return page;
	}

	public MarketResourceDTO getById(String id) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("MarketResource.getById", map, MarketResourceDTO.class);
	}

	@Override
	public int rentFeasibility(List<Integer> idList, MarketResourceLeaseStatusEnum status) throws BizException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("idList", idList);
		paramMap.put("leaseStatus", status.getStatus());
		return baseDao.queryForObject("MarketResource.rentSelCountResource", paramMap, Integer.class);
	}

	@Override
	@Transactional
	public boolean rentAndRecovery(List<Integer> idList, String operatorId, MarketResourceLeaseStatusEnum status) throws BizException {
		int length = idList.size();
		@SuppressWarnings("unchecked")
		Map<String, Object>[] batchValues = new HashMap[length];
		MarketResourceEntity entity = new MarketResourceEntity();
		for (int i = 0; i < length; i++) {
			entity.setId(idList.get(i));
			entity.setLeaseStatus(status.getStatus());
			entity.setUpdateTime(new Date());
			entity.setUpdateUserId(operatorId);
			batchValues[i] = DalUtils.convertToMap(entity);
		}
		int successNum = baseDao.batchUpdate("MarketResource.update", batchValues).length;
		return successNum != length ? false : true;
	}

	@Override
	public boolean insert(MarketResourceEntity entity) throws BizException {
		entity.setOriginOperate(0);
		entity.setCreateTime(new Date());
		//		entity.setLeaseStatus(MarketResourceLeaseStatusEnum.WAIT_FOR_RENT_OUT.getStatus());
		return baseDao.persist(entity, Long.class) > 0 ? true : false;
	}

	@Override
	public boolean update(MarketResourceEntity entity) throws BizException {
		entity.setUpdateTime(new Date());
		return baseDao.dynamicMerge(entity) == 1 ? true : false;
	}

	/**
	 * 批量删除
	 * @param idList
	 * @param operatorId
	 * @return
	 * @throws BizException
	 */
	@Transactional
	public boolean batchDelete(List<Integer> idList, String operatorId) throws BizException {
		int length = idList.size();
		@SuppressWarnings("unchecked")
		Map<String, Object>[] batchValues = new HashMap[length];
		MarketResourceEntity entity = new MarketResourceEntity();
		for (int i = 0; i < length; i++) {
			entity.setId(idList.get(i));
			entity.setStatus(StatusEnum.DELETE.getStatus());
			entity.setUpdateTime(new Date());
			entity.setUpdateUserId(operatorId);
			batchValues[i] = DalUtils.convertToMap(entity);
			Map<String, Object> map = new HashMap<>();
			map.remove("id");			
			map.put("imageType", 3 );//1代表市场图，2代表区域图，3代表楼层图
			map.put("resourceId", entity.getId());//资源id
			baseDao.execute("MarketImageSetting.delete", map) ;
		}
		int successNum = baseDao.batchUpdate("MarketResource.update", batchValues).length;
		return successNum != length ? false : true;
	}

	@Override
	public boolean delete(int id, String operatorId) throws BizException {
		MarketResourceEntity entity = new MarketResourceEntity();
		entity.setId(id);
		entity.setStatus(StatusEnum.DELETE.getStatus());
		entity.setUpdateTime(new Date());
		entity.setUpdateUserId(operatorId);
		return baseDao.dynamicMerge(entity) == 1 ? true : false;
	}


	@Override
	public List<MarketResourceDTO> getList(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("MarketResource.getList", map, MarketResourceDTO.class);
	}

	@Override
	public int deleteById(String id) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("MarketResource.deleteById", map);
	}

	/**
	 * 批量删除
	 */
	@Override
	@Transactional
	public int deleteBatch(List<String> list) throws BizException {
		int len = list.size();
		@SuppressWarnings("unchecked")
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String id=list.get(i);
			map.put("id", id);
			batchValues[i] = map;
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("imageType", 3 );//1代表市场图，2代表区域图，3代表楼层图
			map1.put("resourceId",id);//资源id
			baseDao.execute("MarketImageSetting.delete", map1) ;
		}
		return baseDao.batchUpdate("MarketResource.deleteById", batchValues).length;
	}

	@Override
	public int updateBatch(List<Map<String,Object>> list) throws BizException {
		int len = list.size();
		@SuppressWarnings("unchecked")
		Map<String, Object>[] batchValues = new HashMap[len];
		batchValues=list.toArray(batchValues);
		return baseDao.batchUpdate("MarketResource.update", batchValues).length;
	}

	@Override
	public int update(MarketResourceDTO t) throws BizException {
		return baseDao.execute("MarketResource.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws BizException {
		return baseDao.queryForObject("MarketResource.getTotal", map, Integer.class);
	}

	@Override
	public Integer insert(MarketResourceDTO entity) throws BizException {
		return baseDao.execute("MarketResource.insert", entity);
	}

	@Override
	public Long persist(MarketResourceEntity entity) throws BizException {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<MarketResourceDTO> getListPage(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("MarketResource.getListPage", map, MarketResourceDTO.class);
	}

	@Override
	public GuDengPage<MarketResourceDTO> queryByPage(GuDengPage<MarketResourceDTO> page) throws BizException {
		Map<String,Object> map = page.getParaMap();
		int count = getTotal(map);
		page.setTotal(count);
		List<MarketResourceDTO> list = null;
		if(count>0){
			list = getListPage(map);
		}
		page.setRecordList(list);
		return page;
	}

	/**
	 * 根据条件查询其它资源(分页查询)
	 * @param map
	 * @return
	 * @throws BizException
	 */
	@Override
	public GuDengPage<MarketResourceDTO> queryOtherByPage(GuDengPage<MarketResourceDTO> page) throws BizException {
		Map<String,Object> map = page.getParaMap();
		int count = getOtherResourceTotal(map);
		page.setTotal(count);
		List<MarketResourceDTO> list = null;
		if(count>0){
			list = getOtherResListPage(map);
		}
		page.setRecordList(list);
		return page;
	}

	/**
	 * 获取其它资源总数
	 * @param map
	 * @return
	 * @throws BizException
	 */
	public int getOtherResourceTotal(Map<String, Object> map) throws BizException {
		return baseDao.queryForObject("MarketResource.getOtherResourceTotal", map, Integer.class);
	}

	/**
	 * 获取其它资源列表
	 * @param map
	 * @return
	 * @throws BizException
	 */
	public List<MarketResourceDTO> getOtherResListPage(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("MarketResource.getOtherResListPage", map, MarketResourceDTO.class);
	}

	@Override
	public List<MarketResourceMeasureUnionDTO> getMeasureResourceById(Map<String,Object> map) throws BizException {
		return baseDao.queryForList("MarketResource.getMeasureResourceById", map,MarketResourceMeasureUnionDTO.class);
	}

	@Override
	public List<MarketResourcePrintDTO> queryPrintInfoByIds(String ids) {
		if(StringUtils.isBlank(ids)){
			return null;
		}
		String[] idArr = ids.split(",");
		List<Integer> idList = new ArrayList<Integer>(idArr.length);
		for(String id : idArr){
			idList.add(Integer.parseInt(id));
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idList", idList);
		List<MarketResourcePrintDTO> list = baseDao.queryForList("MarketResource.queryPrintInfoByIds", paramMap, MarketResourcePrintDTO.class);
		return list;
	}

	@Override
	public List<MarketResourceDTO> getResources(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("MarketResource.getResources", map, MarketResourceDTO.class);
	}

	/**
	 * 当前市场根据id和资源编码查询总数
	 * @param paramMap
	 * @return
	 * @throws BizException
	 */
	public int findCountByIdAndCodeAndMarketId(Map<String, Object> paramMap) throws BizException {
		return baseDao.queryForObject("MarketResource.selCountByIdAndCodeAndMarketId", paramMap, Integer.class);
	}

	/**
	 * 当前市场根据id和资源简码查询总数
	 * @param paramMap
	 * @return
	 * @throws BizException
	 */
	public int findCountByIdAndShortCodeAndMarketId(Map<String, Object> paramMap) throws BizException {
		return baseDao.queryForObject("MarketResource.selCountByIdAndShortCodeAndMarketId", paramMap, Integer.class);
	}

	/**
	 * 当前市场根据id和资源名称查询总数
	 * @param paramMap
	 * @return
	 * @throws BizException
	 */
	public int findCountByIdAndNameAndMarketId(Map<String, Object> paramMap) throws BizException {
		return baseDao.queryForObject("MarketResource.selCountByIdAndNameAndMarketId", paramMap, Integer.class);
	}

	@Override
	public List<MarketResourceDTO> queryResourceDotByCondition(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("MarketResource.queryResourceDotByCondition", map, MarketResourceDTO.class);
	}

	@Override
	public ResourceContractDTO queryByResourceId(String resourceId) throws BizException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("resourceId", resourceId);
		return 	baseDao.queryForObject("MarketResource.queryByResourceId", param, ResourceContractDTO.class);
	}

	@Override
	public List<MarketResourceDTO> getNoDotResources(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("MarketResource.getNoDotResources", map, MarketResourceDTO.class);
	}


	public Map<String , Object> queryByResourceIds(Map<String, Object> map){
		Map<String , Object> queryForObject = baseDao.queryForMap("MarketResource.queryByResourceIds", map);
		return 	queryForObject;
	}
}
