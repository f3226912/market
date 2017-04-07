package cn.gdeng.market.service.workflow.bus;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.workflow.WfTaskDTO;
import cn.gdeng.market.exception.BizException;

/**
 * 
 * 流程监控管理
 * @author xphou
 *
 */
public interface WfTaskService{
	
	/**
	 * 根据流程实例ID查询
	 * @param param
	 * @return
	 * @throws BizException
	 */
	List<WfTaskDTO> queryByOrderId(WfTaskDTO dto) throws BizException;
	
	/**
	 * 获取待办任务
	 * @param actorId
	 * @return
	 * @throws BizException
	 */
	public GuDengPage<WfTaskDTO> getGtasks4Page(GuDengPage<WfTaskDTO> page) throws BizException;
	
	/**
	 * 获取待办任务-条数
	 * @param actorId
	 * @return
	 * @throws BizException
	 */
	public int getTotal(Map<String, Object> map) throws BizException;
}
