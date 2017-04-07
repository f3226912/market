package cn.gdeng.market.dto.lease.settings;

import java.util.List;

import com.google.common.base.Ticker;
import com.sun.star.deployment.thePackageManagerFactory;

import cn.gdeng.market.entity.lease.settings.SysContractRemindSettingEntity;
import cn.gdeng.market.entity.lease.settings.SysWorkflowSettingEntity;

/**
 * 
 * @author DJB
 *  租约管理参数设置DTO
 */

public class SysContractRemindSettingInteractionDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1118907073158235492L;

	private Integer marketId;// 市场id

	private Integer orgId;// 公司ID
	/**  合同到期前 */
	private Integer deadlineTime; 

	private Integer rentTime;// 合同收取租金前

	private Integer checkWorkType;// 合同审批

	private Integer changeWorkType;// 变更审批模式

	private Integer settlementWorkType;// 结算审批
	
	private Integer updateUserId;//修改人
	
	
	

	public SysContractRemindSettingInteractionDTO() {
		super();
	}
	
	
	public SysContractRemindSettingInteractionDTO(List<SysContractRemindSettingEntity> sysContractRemindSettingEntities,List<SysWorkflowSettingEntity> sysWorkflowSettingEntities) {
		if (sysWorkflowSettingEntities!=null&&sysWorkflowSettingEntities.size()>0) {
			for (SysWorkflowSettingEntity sysWorkflowSettingEntity : sysWorkflowSettingEntities) {
				if (sysWorkflowSettingEntity.getType().compareTo(1)==0) {
					this.checkWorkType=sysWorkflowSettingEntity.getWorkType();
				}
				if (sysWorkflowSettingEntity.getType().compareTo(2)==0) {
					this.changeWorkType=sysWorkflowSettingEntity.getWorkType();
				}
				if (sysWorkflowSettingEntity.getType().compareTo(3)==0) {
					this.settlementWorkType=sysWorkflowSettingEntity.getWorkType();
				}
			 
			}
			this.marketId=sysWorkflowSettingEntities.get(0).getMarketId();
			this.orgId=sysWorkflowSettingEntities.get(0).getOrgId();
		}
		if (sysContractRemindSettingEntities!=null&&sysContractRemindSettingEntities.size()>0) {
			for (SysContractRemindSettingEntity sysContractRemindSettingEntity : sysContractRemindSettingEntities) {
				if (sysContractRemindSettingEntity.getType().compareTo(1)==0) {
					this.deadlineTime=sysContractRemindSettingEntity.getRemindTime();
					
				}
				if (sysContractRemindSettingEntity.getType().compareTo(2)==0) {
					this.rentTime=sysContractRemindSettingEntity.getRemindTime();
					
				}
			}
			this.marketId=sysContractRemindSettingEntities.get(0).getMarketId();
			this.orgId=sysContractRemindSettingEntities.get(0).getOrgId();
		}
	}
	

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getDeadlineTime() {
		return deadlineTime;
	}

	public void setDeadlineTime(Integer deadlineTime) {
		this.deadlineTime = deadlineTime;
	}

	public Integer getRentTime() {
		return rentTime;
	}

	public void setRentTime(Integer rentTime) {
		this.rentTime = rentTime;
	}

	

	public Integer getCheckWorkType() {
		return checkWorkType;
	}


	public void setCheckWorkType(Integer checkWorkType) {
		this.checkWorkType = checkWorkType;
	}


	public Integer getChangeWorkType() {
		return changeWorkType;
	}


	public void setChangeWorkType(Integer changeWorkType) {
		this.changeWorkType = changeWorkType;
	}


	public Integer getSettlementWorkType() {
		return settlementWorkType;
	}


	public void setSettlementWorkType(Integer settlementWorkType) {
		this.settlementWorkType = settlementWorkType;
	}


	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	

}
