package cn.gdeng.market.dto.workflow;

import java.io.Serializable;

public class WfProcessDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1287144508447988852L;
	/**
	 * 主键ID
	 */
	private String id;
	/**
	 * 流程名称
	 */
	private String name;
	
	/**
	 * 模板显示名称
	 */
	private String displayName;
	/**
	 * 工作流业务类型
	 */
	private String busType;
	
	/**
	 * 工作流业务类型
	 */
	private String busTypeDesc;
	
	/**
	 * 流程是否可用(1表示可用 0表示不可用)  
	 */
	private String state;
	
	/**
	 * 流程是否可用(1表示可用 0表示不可用)  
	 */
	private String stateDesc;
	
	
	/**
	 * 组织id(所属市场)
	 */
	private String orgId;
	
	/**
	 * 组织id(所属市场)
	 */
	private String orgDesc;
	
	/**
	 * 流程描述
	 */
	private String processDesc;
	
	/**
	 * 修改时间
	 */
	private String modifiedTime;
	/**
	 * 修改人
	 */
	private String modificator;
	
	/**流程监控人id
	 * 
	 */
	private String monitorId;
	
	/**流程监控人描述
	 * 
	 */
	private String monitorDesc;
	
	/**流程模型json字符串。
	 * 
	 */
	private String processModelJson;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getModificator() {
		return modificator;
	}
	public void setModificator(String modificator) {
		this.modificator = modificator;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getBusTypeDesc() {
		return busTypeDesc;
	}
	public void setBusTypeDesc(String busTypeDesc) {
		this.busTypeDesc = busTypeDesc;
	}
	public String getStateDesc() {
		return stateDesc;
	}
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}
	public String getOrgDesc() {
		return orgDesc;
	}
	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}
	public String getMonitorId() {
		return monitorId;
	}
	public void setMonitorId(String monitorId) {
		this.monitorId = monitorId;
	}
	public String getProcessDesc() {
		return processDesc;
	}
	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}
	public String getProcessModelJson() {
		return processModelJson;
	}
	public void setProcessModelJson(String processModelJson) {
		this.processModelJson = processModelJson;
	}
	public String getMonitorDesc() {
		return monitorDesc;
	}
	public void setMonitorDesc(String monitorDesc) {
		this.monitorDesc = monitorDesc;
	}
	
}
