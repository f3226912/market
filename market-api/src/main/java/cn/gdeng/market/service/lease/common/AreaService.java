package cn.gdeng.market.service.lease.common;

import java.util.List;

import cn.gdeng.market.dto.lease.common.AreaDTO;


/** 地区服务类接口
 * 
 * @author wjguo
 *
 * datetime:2016年10月10日 上午10:24:56
 */
public interface AreaService {
	
	/**查询有效的区域子树。省、市、区（县）均查询出来并进行关联。顶级为省、由省关联市、由市关联区县。
	 * @return
	 */
	public List<AreaDTO> queryAreaChildTree();
	
	/**
	 * 根据areaId查询有效的下级地区。
	 * 
	 * @param areaId
	 * @return 下级地区集合
	 */
	public List<AreaDTO> queryChildrenArea(String areaId) throws Exception;
	/**
	 * 根据areaId查地区
	 * 
	 * @param areaId
	 * @return
	 */
	public AreaDTO getAreaByAreaId(String areaId) throws Exception;

}
