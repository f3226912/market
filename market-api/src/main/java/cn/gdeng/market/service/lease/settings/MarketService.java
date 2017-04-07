package cn.gdeng.market.service.lease.settings;

import java.util.List;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.common.StatisticsDTO;
import cn.gdeng.market.dto.lease.settings.MarketDTO;
import cn.gdeng.market.dto.lease.settings.SummaryDTO;
import cn.gdeng.market.entity.lease.settings.MarketEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.BaseService;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author bdhuang
 *
 */
public interface MarketService extends BaseService<MarketDTO> {
	public Long persist(MarketEntity entity) throws BizException;
	
	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return
	 * @throws BizException
	 */
	public GuDengPage<MarketDTO> queryByPage(GuDengPage<MarketDTO> page) throws BizException ;
	
	/**
	 * 市场资源使用情况统计
	 * @param marketId resourceTypeId
	 * @return
	 * @throws BizException
	 */
	StatisticsDTO marketResourceStatistics(String marketId, String resourceTypeId) throws BizException;
	
	/**
	 * 市场类别（用于下拉选择）
	 * @return
	 * @throws BizException
	 */
	public List<MarketDTO> queryListForSelect() throws BizException;
	
	
	
	
	/** ------------------------------平面图设置接口优化--------------------------------- */
	/**
	 * 市场资源使用情况统计
	 * @param marketId resourceTypeId
	 * @return
	 * @throws BizException
	 */
	SummaryDTO marketResourceSummary(String marketId, String resourceTypeId) throws BizException;
	
	
	
	
}