package cn.gdeng.market.dto.workflow;

import java.io.Serializable;
import java.util.List;

public class WfTaskDTO implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = -2288253487016254521L;
	/**
	 * 主键ID
	 */
	private String id;
	/**
	 * 流程实例ID
	 */
	private String orderId;
	
	/**
	 * 任务名称
	 */
	private String taskName;
	
	/**
	 * 参与类型(0：普通任务；1：参与者会签任务）
	 */
	private int performType;
	
	/**
	 * 任务显示名称
	 */
	private String displayName;
	
	/**
	 * 发起人
	 */
	private String creator;
	
	/**
	 * 发起人描述
	 */
	private String creatorDesc;
	
	/**
	 * 责任人Id
	 */
	private String actorId;
	
	/**
	 * 流程名称
	 */
	private String orderNo;
	
	/**
	 * 责任人描述
	 */
	private String actorDesc;
	
	/**
	 * 发起事件
	 */
	private String createTime;
	/**
	 * 责任人
	 */
	private List<WfTaskActorDTO> taskActorList;
	
	/**
	 * 跳转URL
	 */
	public String forwardUrl;
	
	/**
	 * 业务类型
	 */
	public String busType;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<WfTaskActorDTO> getTaskActorList() {
		return taskActorList;
	}

	public void setTaskActorList(List<WfTaskActorDTO> taskActorList) {
		this.taskActorList = taskActorList;
	}

	public int getPerformType() {
		return performType;
	}

	public void setPerformType(int performType) {
		this.performType = performType;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorDesc() {
		return creatorDesc;
	}

	public void setCreatorDesc(String creatorDesc) {
		this.creatorDesc = creatorDesc;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public String getActorDesc() {
		return actorDesc;
	}

	public void setActorDesc(String actorDesc) {
		this.actorDesc = actorDesc;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	
}
