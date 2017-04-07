package cn.gdeng.market.dto.workflow;

import java.io.Serializable;

/** 审批合同的DTO
 * 
 * @author wjguo
 *
 * datetime:2016年10月23日 下午4:35:22
 */
public class ApprovalContractDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 392729013817887334L;
	/**
	 * 发起的流程实例名称
	 */
	private String taskId;
	/**
	 * 审批意见
	 */
	private String opinion;
	/**
	 * 审批结果。0表示通过，2表示驳回。
	 */
	private Integer result;
	
	/**消息接收者id，多个id使用英文逗号隔开。
	 * 
	 */
	private String msgReceivers;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getMsgReceivers() {
		return msgReceivers;
	}

	public void setMsgReceivers(String msgReceivers) {
		this.msgReceivers = msgReceivers;
	}
}
