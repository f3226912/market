package cn.gdeng.market.dto.workflow;

import java.io.Serializable;

/**发起合同审批的DTO
 * 
 * @author wjguo
 *
 * datetime:2016年10月18日 上午11:53:53
 */
public class StartContractApprovalDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8226337770356486014L;
	/**
	 * 主合同id
	 */
	private Integer contractMainId;
	/**
	 * 发起的流程实例名称
	 */
	private String orderNo;
	/**
	 * 发起流程说明
	 */
	private String opinion;
	/**
	 * 发起的流程类型
	 */
	private Byte approvalType;
	
	/**发起的流程id
	 * 
	 */
	private String processId;
	public Integer getContractMainId() {
		return contractMainId;
	}
	public void setContractMainId(Integer contractMainId) {
		this.contractMainId = contractMainId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public Byte getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(Byte approvalType) {
		this.approvalType = approvalType;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
	
}
