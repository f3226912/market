package cn.gdeng.market.dto.workflow;

import java.io.Serializable;

public class WfOrderDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2278205845261064900L;
	/**
	 * 主键ID
	 */
	private String id;
	/**
	 * 流程定义ID
	 */
	private String processId;
	
	/**
	 * 流程实例名称
	 */
	private String orderNo;
	
	/**
	 * 工作流业务类型
	 */
	private String busType;
	
	/**
	 * 流程发起人ID
	 */
	private String creator;
	
	
	/**
	 * 关联的业务表id
	 */
	private String busId;
	
	
	
	private WfTaskDTO wfTaskDTO;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public WfTaskDTO getWfTaskDTO() {
		return wfTaskDTO;
	}

	public void setWfTaskDTO(WfTaskDTO wfTaskDTO) {
		this.wfTaskDTO = wfTaskDTO;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}
	
	
}
