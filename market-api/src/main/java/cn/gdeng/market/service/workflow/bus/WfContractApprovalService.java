package cn.gdeng.market.service.workflow.bus;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.admin.SysUserDTO;

/**工作流合同审批服务类
 * 
 * @author wjguo
 *
 * datetime:2016年10月20日 下午3:50:03
 */
public interface WfContractApprovalService {
	/** 查询当前流程实例的活动任务参与者
	 * @param orderId
	 * @return
	 */
	List<SysUserDTO> queryOrderActiveTaskActor(String orderId);
	
	/**终止任务，并作废当前任务所属的流程实例。
	 * @param taskId 任务id
	 * @param taskOperator 任务操作者
	 * @param taskArgs 任务终止参数
	 */
	void terminateTaskAndOrder(String taskId, String taskOperator, Map<String, Object> taskArgs);
}
