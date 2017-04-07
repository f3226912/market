package cn.gdeng.market.service.lease.settings;

import java.util.Map;

import cn.gdeng.market.dto.lease.settings.SysContractRemindSettingDTO;
import cn.gdeng.market.dto.lease.settings.SysContractRemindSettingInteractionDTO;
import cn.gdeng.market.exception.BizException;

/**
 * 合同提醒
 * @author Kwang
 *
 */
public interface SysContractRemindSettingService {
	
	/**
	 * 根据市场查合同提醒信息
	 * @param map
	 * @return SysContractRemindSettingDTO
	 */
	SysContractRemindSettingDTO queryContractRemindSettingByMarket(Map<String,Object> map);

	
	/**
	 * 查询市场租约管理设置
	 */
	SysContractRemindSettingInteractionDTO queryByMarketId(Integer marketId) throws BizException;
	
	/**
	 * 修改租约管理设置
	 */ 
	SysContractRemindSettingInteractionDTO update(SysContractRemindSettingInteractionDTO sysContractRemindSettingInteractionDTO) throws BizException; 

	
}
