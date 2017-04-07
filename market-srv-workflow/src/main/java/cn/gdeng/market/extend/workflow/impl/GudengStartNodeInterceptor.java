package cn.gdeng.market.extend.workflow.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.StartNodeInterceptor;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;

import cn.gdeng.market.enums.WorkflowBusTypeEnum;
import cn.gdeng.market.service.lease.contract.ContractWorkflowService;
import cn.gdeng.market.util.WorkflowHelper;

/**谷登专用流程开始节点拦截器
 * 
 * @author wjguo
 *
 * datetime:2016年10月18日 下午3:47:42
 */
public class GudengStartNodeInterceptor implements StartNodeInterceptor {
	/**
	 * 定义记录日志信息
	 */
	private Logger logger = LoggerFactory.getLogger(GudengStartNodeInterceptor.class);
	
	/**合同提供给工作流的服务
	 * 
	 */
	private ContractWorkflowService contractWorkflowService;
	
	public void setContractWorkflowService(ContractWorkflowService contractWorkflowService) {
		this.contractWorkflowService = contractWorkflowService;
	}

	@Override
	public void beforeIntercept(Execution execution) {
		// no thing;
	}

	@Override
	public void afterIntercept(Execution execution) {
		Process process = execution.getProcess();
		Order order = execution.getOrder();
		
		WorkflowBusTypeEnum wfBusTypeEnum = WorkflowBusTypeEnum.getByCode(order.getBusType());
		if (WorkflowHelper.isContractApproBusType(wfBusTypeEnum)) {
			Byte approTypeCode = WorkflowHelper.convertContractApproTypeCode(wfBusTypeEnum);
			contractWorkflowService.submitApproval(Integer.valueOf(order.getBusId()), approTypeCode);
		}
		
		Object[] logArgArray = {process.getDisplayName(), process.getId(), order.getId(), order.getBusId()}; 
		logger.info("{}工作流程成功发起,流程id为{},流程实例id为{},关联的业务id为{}", logArgArray);
		
	}
	
	
}
