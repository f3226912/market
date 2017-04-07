package cn.gdeng.market.util;

import cn.gdeng.market.enums.ApprovalTypeEnum;
import cn.gdeng.market.enums.WorkflowBusTypeEnum;

public final class WorkflowHelper {
	/**是否审批合同的业务 类型
	 * @param wfBusTypeEnum 工作累业务类型枚举
	 * @return
	 */
	public static boolean isContractApproBusType (WorkflowBusTypeEnum wfBusTypeEnum) {
		boolean result = false;
		switch (wfBusTypeEnum) {
			case CONTRACT_MANAGER:
			case CONTRACT_CHANGED:
			case CONTRACT_CLOSE:
				result = true;
				break;
		}
		return result;
	}
	
	/**根据工作流业务类型枚举，转换为合同审批类型的code。<br/>
	 * @param wfBusTypeEnum 工作流类型枚举
	 * @return 返回合同审批类型code码。如果不是与合同相关的枚举，则返回-1。
	 */
	public static Byte convertContractApproTypeCode(WorkflowBusTypeEnum wfBusTypeEnum) {
		if (isContractApproBusType(wfBusTypeEnum)) {
			return ApprovalTypeEnum.valueOf(wfBusTypeEnum.name()).getCode();
		}
		
		return -1;
	}
	
	
	/**根据工作流业务类型枚举，转换为合同审批类型的code。<br/>
	 * @param wfBusTypeEnum 工作流类型枚举
	 * @return 返回合同审批类型枚举。如果不是与合同相关的枚举，则返回null。
	 */
	public static ApprovalTypeEnum convertContractApproTypeEnum(WorkflowBusTypeEnum wfBusTypeEnum) {
		if (isContractApproBusType(wfBusTypeEnum)) {
			return ApprovalTypeEnum.valueOf(wfBusTypeEnum.name());
		}
		
		return null;
	}
}
