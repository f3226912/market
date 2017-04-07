package cn.gdeng.market.service.lease.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.lease.common.AreaDTO;
import cn.gdeng.market.enums.AreaStatusEnum;
import cn.gdeng.market.service.lease.common.AreaService;

@Service(value = "areaService")
public class AreaServiceImpl implements AreaService {
	
	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public List<AreaDTO> queryAreaChildTree() {
		Map<String, Object> paramMap = new HashMap<String, Object>(1);
		//只查询有效的
		paramMap.put("status", AreaStatusEnum.enabled.getCode());
		
		//所有有效的省
		List<AreaDTO> provinces = baseDao.queryForList("Area.queryAllProvin", paramMap, AreaDTO.class);
		//所有有效的市
		List<AreaDTO> citys = baseDao.queryForList("Area.queryAllCity", paramMap, AreaDTO.class);
		//所有有效的区（县）
		List<AreaDTO> counties = baseDao.queryForList("Area.queryAllCounty", paramMap, AreaDTO.class);
		
		//封装地区的关联
		packSubToFather(counties, citys);
		packSubToFather(citys, provinces);
		
		//返回顶级地区-》省。
		return provinces;
	}
	
	/**封装子地区到父地区
	 * @param subs 子地区集合
	 * @param fathers 父地区集合
	 */
	private void packSubToFather(List<AreaDTO> subs, List<AreaDTO> fathers) {
		List<AreaDTO> childAreas = null;
		for (AreaDTO sub : subs) {
			for (AreaDTO father : fathers) {
				if (sub.getFatherId().equals(father.getAreaId())) {
					childAreas = father.getChildAreas();
					//首次关联没有集合对象，需要new
					if (childAreas == null) {
						childAreas = new ArrayList<AreaDTO>();
						father.setChildAreas(childAreas);
					}
					childAreas.add(sub);
					break;
				}
			}
		}
	}
	

	@Override
	public List<AreaDTO> queryChildrenArea(String areaId) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("fatherId", areaId);
		if(null == areaId || "".equals(areaId)){
			paramMap.put("queryProvince", true);
		}
		//只查询有效的
		paramMap.put("status", AreaStatusEnum.enabled.getCode());
		return baseDao.queryForList("Area.queryByCondition", paramMap, AreaDTO.class);
	}

	@Override
	public AreaDTO getAreaByAreaId(String areaId) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>(1);
		paramMap.put("areaId", areaId);
		return baseDao.queryForObject("Area.queryByCondition", paramMap, AreaDTO.class);
	}

}
