package cn.gdeng.market.dto.workflow;

import java.io.Serializable;

public class WfTaskActorDTO implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = -2288253487016254521L;
	/**
	 * 任务ID
	 */
	private String taskId;
	/**
	 * 参与者
	 */
	private String actorId;
	
	/**
	 * 参与者描述
	 */
	private String actorDesc;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getActorId() {
		return actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getActorDesc() {
		return actorDesc;
	}
	public void setActorDesc(String actorDesc) {
		this.actorDesc = actorDesc;
	}
	
	
}
