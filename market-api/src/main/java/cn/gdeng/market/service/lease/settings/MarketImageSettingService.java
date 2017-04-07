package cn.gdeng.market.service.lease.settings;

import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.MarketImageSettingDTO;
import cn.gdeng.market.entity.lease.settings.MarketImageSettingEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 市场平面图接口
 * @author youj
 *
 */

public interface MarketImageSettingService {
	
	/**
	 * 新增市场平面图
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	public int addMarketImageSetting(MarketImageSettingEntity entity) throws BizException;
	
	/**
	 * 查询市场平面图
	 * @param map
	 * 
	 */
    public GuDengPage<MarketImageSettingDTO> queryMarketImageSettingByCondition(GuDengPage<MarketImageSettingDTO> page) throws BizException;
	
	/**
	 * 删除市场平面图
	 * @param map
	 * @return
	 * @throws BizException
	 */
	public int delMarketImageSetting(Map<String, Object> param) throws BizException;
	
	/**
	 * 查询总记录数
	 * @param param
	 * @return
	 * @throws BizException
	 */
	public int queryCount(Map<String, Object> param) throws BizException;
	
	/**
	 * 修改市场平面图布局
	 * @param param
	 * @return
	 * @throws BizException
	 */
	public int updateCoordinate(MarketImageSettingEntity t) throws BizException;
	
	/**
	 * 查询市场平面图
	 * @param map
	 * 
	 */
    public MarketImageSettingDTO queryByCondition(Map<String, Object> map) throws BizException;
	
}
