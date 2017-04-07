package cn.gdeng.market.dto.workflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 显示合同审批流程的DTO
 * @author wjguo
 *
 * datetime:2016年10月19日 下午3:54:18
 */
public class ShowContractApproFlowDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7458463698110386283L;
	private List<ApproFlowInfo> approFlowInfos;
	
	public List<ApproFlowInfo> getApproFlowInfos() {
		return approFlowInfos;
	}

	public void setApproFlowInfos(List<ApproFlowInfo> approFlowInfos) {
		this.approFlowInfos = approFlowInfos;
	}
	
	/**
	 * @param initialCapacity 存储的集合元素初始化容量
	 */
	public ShowContractApproFlowDTO(int initialCapacity) {
		approFlowInfos = new ArrayList<ApproFlowInfo>(initialCapacity);
	}
	
	public ShowContractApproFlowDTO() {
		this(5);
	}

	/**添加审批流信息
	 * @param approFlowInfo
	 */
	public void addApproFlowInfo(ApproFlowInfo approFlowInfo) {
		this.approFlowInfos.add(approFlowInfo);
	}
	
	
	/**合同审批流信息
	 * 
	 * @author wjguo
	 *
	 * datetime:2016年10月19日 下午4:16:29
	 */
	public static class ApproFlowInfo implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 63987061253967324L;

		/**
		 * 任务显示名称
		 */
		private String taskDisplayName;
		
		/**
		 * 操作人基本信息
		 */
		private String operateBaseInfo;
		
		
		/**完成时间
		 * 
		 */
		private String finshTime;
		
		/**意见
		 * 
		 */
		private String opinion;
		
		
		public String getOpinion() {
			return opinion;
		}

		public void setOpinion(String opinion) {
			this.opinion = opinion;
		}

		public String getTaskDisplayName() {
			return taskDisplayName;
		}


		public void setTaskDisplayName(String taskDisplayName) {
			this.taskDisplayName = taskDisplayName;
		}


		public String getOperateBaseInfo() {
			return operateBaseInfo;
		}


		public void setOperateBaseInfo(String operateBaseInfo) {
			this.operateBaseInfo = operateBaseInfo;
		}


		public String getFinshTime() {
			return finshTime;
		}


		public void setFinshTime(String finshTime) {
			this.finshTime = finshTime;
		}
		
	}
	
	
	
}
