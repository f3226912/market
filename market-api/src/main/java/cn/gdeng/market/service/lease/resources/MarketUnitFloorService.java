package cn.gdeng.market.service.lease.resources;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.lease.common.StatisticsDTO;
import cn.gdeng.market.dto.lease.resources.MarketUnitFloorDTO;
import cn.gdeng.market.dto.lease.settings.SummaryDTO;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.BaseService;

/**
 * 
 * 楼栋管理-批量生成资源
 * 
 * @author hbd
 *
 */
public interface MarketUnitFloorService extends BaseService<MarketUnitFloorDTO>{
	
	
	/**
	 * 
	 * 根据楼栋获取当前的楼层
	 * 
	 * @author hbd
	 * 
	 * */
	public List<MarketUnitFloorDTO> getFloor(Map<String,Object> map) throws BizException;
	
	/**
	 * 楼层资源使用情况统计
	 * @param floorId resourceTypeId
	 * @return
	 * @throws BizException
	 */
	StatisticsDTO floorResourceStatistics(String floorId, String resourceTypeId) throws BizException;
	
	/**-------------------------平面图设置接口优化--------------------------------------*/
	
	SummaryDTO floorResourceSummary(String floorId, String resourceTypeId) throws BizException;
}
