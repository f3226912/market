package cn.gdeng.market.service.lease.settings;

import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.MarketMeasureSettingDTO;
import cn.gdeng.market.entity.lease.settings.MarketExpenditureEntity;
import cn.gdeng.market.entity.lease.settings.MarketMeasureSettingEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 计量分量接口
 * @author cai.xu
 *
 */

public interface MarketMeasureSettingService {

	/**
	 * 查询记录数
	 * @param map
	 * @return 记录数
	 */
	public GuDengPage<MarketMeasureSettingDTO> queryListByPage(GuDengPage<MarketMeasureSettingDTO> page) throws BizException;
	
	/**
	 * 查询总条数 
	 * @param param
	 * @return
	 * @throws BizException
	 */
	public int queryCount(Map<String, Object> param) throws BizException;
	
	/**
	 * 新增计量表
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	public int addMeasureSetting(MarketMeasureSettingEntity entity,int marketId) throws BizException;
	
	/**
	 * 修改计量表
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	public int editMeasureSetting(MarketMeasureSettingEntity entity,int marketId) throws BizException;
	
	/**
	 * 批量新增计量表
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	public int batchAddMeasureSetting(MarketMeasureSettingDTO entity,int marketId) throws BizException;
	
	/**
	 * 根据Id删除计量表
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public int delMeasureSetting(MarketMeasureSettingEntity entity) throws BizException;
	
	/**
	 * 根据Id删除计量表
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public int enableOrDisable(String ids, int optFlag) throws BizException;
	
	/**
	 * 根据计量表分类ID 找到对应的费项名
	 * @param measureTypeId
	 * @return
	 * @throws BizException
	 */
	public MarketExpenditureEntity findById(int measureTypeId) throws BizException;
	
	/**
	 * 根据计量表ID 找到对应的计量表
	 * @param measureTypeId
	 * @return
	 * @throws BizException
	 */
	public MarketMeasureSettingDTO getBySettingId(int settingId) throws BizException;
}
