package cn.gdeng.market.service.lease.settings;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.MarketMeasureTypeDTO;
import cn.gdeng.market.entity.lease.settings.MarketMeasureTypeEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 计量分量接口
 * @author cai.xu
 *
 */

public interface MarketMeasureTypeService {

	/**
	 * 分页查询记录数
	 * @param map
	 * @return 记录数
	 */
	public GuDengPage<MarketMeasureTypeDTO> queryListByPage(GuDengPage<MarketMeasureTypeDTO> page) throws BizException;
	
	/**
	 * 查询所有记录数
	 * @param page
	 * @return
	 * @throws BizException
	 */
	public GuDengPage<MarketMeasureTypeDTO> queryAllList(GuDengPage<MarketMeasureTypeDTO> page) throws BizException;
	
	/**
	 * 查询总条数 
	 * @param param
	 * @return
	 * @throws BizException
	 */
	public int queryCount(Map<String, Object> param) throws BizException;
	
	/**
	 * 新增计量分类
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	public int addMeasureType(MarketMeasureTypeEntity entity) throws BizException;
	
	/**
	 * 根据Id删除计量分类
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public int delMeasureType(MarketMeasureTypeEntity entity) throws BizException;
	
	/**
	 * 编辑
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	public int editMeasureType(MarketMeasureTypeEntity entity) throws BizException;
}
