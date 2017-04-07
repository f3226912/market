package cn.gdeng.market.service.workflow.core;

import org.snaker.engine.IOrderService;

/**snaker流程实例业务类接口
 * 
 * @author wjguo
 *
 * datetime:2016年10月9日 下午12:17:09
 */
public interface SnakerOrderService extends IOrderService{
	
	/**流程实例备注信息的json变量名称。<br/>
	 * desc表示备注内容。
	 * 
	 */
	static final String REMARK_DESC_VAR = "desc";
	
	/**流程实例备注信息的json变量名称。<br/>
	 * operatorInfo表示备注人基本信息。
	 * 
	 */
	static final String REMARK_OPERATOR_INFO_VAR = "operatorInfo";
	
	/**流程实例备注信息的json变量名称。<br/>
	 * operatorID操作人id。
	 * 
	 */
	static final String REMARK_OPERATOR_ID_VAR = "operatorID";
	
	/**流程实例备注信息的json变量名称。<br/>
	 * curTaskDisplayName 当前任务节点的显示名称。
	 * 
	 */
	static final String REMARK_CUR_TASK_DISPLAY_NAME_VAR = "curTaskDisplayName";
	
	/**流程实例备注信息的json变量名称。<br/>
	 * curTaskPerformType 当前任务执行类型。
	 * 
	 */
	static final String REMARK_CUR_TASK_PERFORM_TYPE_VAR = "curTaskPerformType";
	
	/**流程实例备注信息的json变量名称。<br/>
	 * curTaskPerformType 当前任务节点名称。
	 * 
	 */
	static final String REMARK_CUR_TASK_NAME_VAR = "curTaskName";

}
