package cn.gdeng.market.service.admin;

import java.util.List;

import java.util.Map;

import cn.gdeng.market.dto.admin.SysMessageDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.entity.admin.SysMessageEntity;
import cn.gdeng.market.exception.BizException;

/**
 * 
 * @author sss
 *
 */
public interface SysMessageService {
	
	/**系统发送者ID。当由系统发送消息时，发送者ID使用此常量。
	 * 
	 */
	public static final String SYS_SENDER_ID = "system";

	/**
	 * 分页获取消息
	 * @param page
	 * @return
	 * @throws BizException
	 */
	public GuDengPage<SysMessageDTO> getMessage4Page(GuDengPage<SysMessageDTO> page) throws BizException;

	/**批量保存系统消息
	 * @param entities 实体集合
	 */
	public void batchSaveSysMsg(List<SysMessageEntity> entities) throws BizException;
	
	/**
	 * 获取个数
	 * @param params
	 * @return
	 * @throws BizException
	 */
	int getTotalByCondition(Map<String,Object> params) throws BizException;
	
	/**
	 * 动态更新
	 * @param entity
	 */
	void dynamicUpdate(SysMessageEntity entity);
}
