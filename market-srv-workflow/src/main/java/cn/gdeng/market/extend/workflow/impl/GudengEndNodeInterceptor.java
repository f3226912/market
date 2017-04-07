package cn.gdeng.market.extend.workflow.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.EndNodeInterceptor;
import org.snaker.engine.SnakerException;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;

import cn.gdeng.market.enums.ApprovalStatusEnum;
import cn.gdeng.market.enums.WorkflowBusTypeEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.lease.contract.ContractWorkflowService;
import cn.gdeng.market.util.WorkflowHelper;

/**谷登专用流程结束节点拦截器
 * 
 * @author wjguo
 *
 * datetime:2016年10月18日 下午3:47:12
 */
public class GudengEndNodeInterceptor implements EndNodeInterceptor {
	/**
	 * 定义记录日志信息
	 */
	private Logger logger = LoggerFactory.getLogger(GudengEndNodeInterceptor.class);
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
		Object[] logArgArray = {process.getDisplayName(), process.getId(), order.getId(), order.getBusId()}; 
		logger.info("{}工作流程成功归档,流程id为{},流程实例id为{},关联的业务id为{}", logArgArray);

		WorkflowBusTypeEnum wfBusTypeEnum = WorkflowBusTypeEnum.getByCode(order.getBusType());
		if (WorkflowHelper.isContractApproBusType(wfBusTypeEnum)) {
			try {
				//审批流程能走到结束节点，则说明前面的所有任务都审批通过，即等同于当前合同审批通过。
				Byte approvalStatus = ApprovalStatusEnum.YTG.getCode();
				Integer contractMainId = Integer.valueOf(order.getBusId());
				switch (wfBusTypeEnum) {
					case CONTRACT_MANAGER:
						contractWorkflowService.contractApproval(contractMainId, approvalStatus);
						break;
					case CONTRACT_CHANGED:
						contractWorkflowService.contractChangeApproval(contractMainId, approvalStatus);
						break;
					case CONTRACT_CLOSE:
						contractWorkflowService.contractSettlementApproval(contractMainId, approvalStatus);
						break;
				}
			} catch (NumberFormatException | BizException e) {
				throw new SnakerException(e);
			}
		}
	}

}
