package cn.gdeng.market.service.workflow.bus;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.workflow.WfOrderDTO;
import cn.gdeng.market.dto.workflow.WfTaskActorDTO;
import cn.gdeng.market.dto.workflow.WfTaskDTO;
import cn.gdeng.market.exception.BizException;

/**
 * 
 * 流程监控管理
 * @author xphou
 *
 */
public interface WfOrderService{
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @throws BizException
	 */
	WfOrderDTO getById(String id) throws BizException;
	
	/**
	 * 查询分页条数
	 * @param param
	 * @return
	 * @throws BizException
	 */
	int countByCondition(Map<String,Object> param) throws BizException;
	/**
	 * 查询分页
	 * @param page
	 * @return
	 * @throws BizException
	 */
	GuDengPage<WfOrderDTO> queryByConditionPage(GuDengPage<WfOrderDTO> page) throws BizException;
 
	/**
	 * 流程作废
	 * @param orderId
	 * @param userInfo
	 * @throws BizException
	 */
	public String terminate(String orderId,Map<String, Object> map,SysUserDTO userInfo) throws BizException;
	
	/**
	 * 更改任务参与者
	 * @param map
	 * @param oldActor
	 * @param newActor
	 */
	public int[] taskActorChanged(List<Map<String,String>> list,SysUserDTO userInfo,String orderId) throws BizException;
	/**
	 * 根据实例ID获取任务以及当前责任人
	 * @param orderId
	 * @return
	 */
	public List<WfTaskDTO> getActorList(String orderId) throws BizException;
	/**
	 * 根据流程实例ID获取当前任务节点的所有责任人
	 * @param orderId 流程实例ID
	 * @return
	 */
	public List<WfTaskActorDTO> getActorsByOrderId(String orderId) throws BizException;
}
