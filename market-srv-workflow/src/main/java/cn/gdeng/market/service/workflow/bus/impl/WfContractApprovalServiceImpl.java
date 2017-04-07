package cn.gdeng.market.service.workflow.bus.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.snaker.engine.SnakerSpecVariableKeys;
import org.snaker.engine.entity.Task;
import org.snaker.engine.helper.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.service.workflow.bus.WfContractApprovalService;
import cn.gdeng.market.service.workflow.core.SnakerOrderService;
import cn.gdeng.market.service.workflow.core.SnakerQueryService;
import cn.gdeng.market.service.workflow.core.SnakerTaskService;

@Service(value = "wfContractApprovalService")
public class WfContractApprovalServiceImpl implements WfContractApprovalService {
	@Autowired
	private BaseDao<?> baseDao;
	@Autowired
	private SnakerOrderService snakerOrderService;
	@Autowired
	private SnakerTaskService snakerTaskService;
	
	
	@Override
	public List<SysUserDTO> queryOrderActiveTaskActor(String orderId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("orderId", orderId);
		return baseDao.queryForList("WfContractApproval.queryOrderActiveTaskActor", params, SysUserDTO.class);
	}

	@Override
	public void terminateTaskAndOrder(String taskId, String taskOperator, Map<String, Object> args) {
		//终止任务
		Task task = snakerTaskService.terminate(taskId, taskOperator, args);

		//构造终止流程实例的参数
		Map<String, Object> remarkMap = new HashMap<String, Object>();
		remarkMap.put(SnakerOrderService.REMARK_CUR_TASK_DISPLAY_NAME_VAR, task.getDisplayName());
		remarkMap.put(SnakerOrderService.REMARK_CUR_TASK_NAME_VAR, task.getTaskName());
		remarkMap.put(SnakerOrderService.REMARK_CUR_TASK_PERFORM_TYPE_VAR, task.getPerformType());
		remarkMap.put(SnakerOrderService.REMARK_OPERATOR_ID_VAR, taskOperator);
		remarkMap.put(SnakerOrderService.REMARK_OPERATOR_INFO_VAR, args.get(SnakerSpecVariableKeys.TASK_OPERATOR_BASE_INFO_VAR));
		remarkMap.put(SnakerOrderService.REMARK_DESC_VAR, args.get(SnakerSpecVariableKeys.TASK_OPERATOR_OPINION_VAR));
		
		//终止流程实例
		snakerOrderService.terminate(task.getOrderId(), JsonHelper.toJson(remarkMap));
	}

}
