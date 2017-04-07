package cn.gdeng.market.dto.lease.contract;

import java.io.Serializable;
import java.util.List;

import cn.gdeng.market.entity.lease.contract.ContractAccessoriesEntity;
import cn.gdeng.market.entity.lease.contract.ContractChangeEntity;
import cn.gdeng.market.entity.lease.contract.ContractFreeEntity;
import cn.gdeng.market.entity.lease.contract.ContractLeaseEntity;
import cn.gdeng.market.entity.lease.contract.ContractMainEntity;
import cn.gdeng.market.entity.lease.contract.ContractOthersFeeEntity;
import cn.gdeng.market.entity.lease.contract.ContractPaymentEntity;

/**
 * 合同综合信息
 *
 */
public class ContractEntityDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1161458885845226810L;
	/**
	 * 合同基本信息
	 */
	private ContractMainEntity mainEntity;
	/**
	 * 合同基本信息
	 */
	private ContractChangeEntity changeEntity;
	/**
	 * 按周期收费-租赁约定记录
	 */
	private List<ContractLeaseEntity> leaseList;
	/**
	 * 按周期收费-免租约定记录
	 */
	private List<ContractFreeEntity> freeList;
	/**
	 * 按周期收费-其他费约定记录
	 */
	private List<ContractOthersFeeEntity> othersFeeList;
	/**
	 * 按约定总金额-缴费约定记录
	 */
	private List<ContractPaymentEntity> paymentList;
	/**
	 * 附件
	 */
	private List<ContractAccessoriesEntity> accessoriesList;
	public ContractMainEntity getMainEntity() {
		return mainEntity;
	}
	public void setMainEntity(ContractMainEntity mainEntity) {
		this.mainEntity = mainEntity;
	}
	public List<ContractLeaseEntity> getLeaseList() {
		return leaseList;
	}
	public void setLeaseList(List<ContractLeaseEntity> leaseList) {
		this.leaseList = leaseList;
	}
	public List<ContractFreeEntity> getFreeList() {
		return freeList;
	}
	public void setFreeList(List<ContractFreeEntity> freeList) {
		this.freeList = freeList;
	}
	public List<ContractOthersFeeEntity> getOthersFeeList() {
		return othersFeeList;
	}
	public void setOthersFeeList(List<ContractOthersFeeEntity> othersFeeList) {
		this.othersFeeList = othersFeeList;
	}
	public List<ContractPaymentEntity> getPaymentList() {
		return paymentList;
	}
	public void setPaymentList(List<ContractPaymentEntity> paymentList) {
		this.paymentList = paymentList;
	}
	public List<ContractAccessoriesEntity> getAccessoriesList() {
		return accessoriesList;
	}
	public void setAccessoriesList(List<ContractAccessoriesEntity> accessoriesList) {
		this.accessoriesList = accessoriesList;
	}
	public ContractChangeEntity getChangeEntity() {
		return changeEntity;
	}
	public void setChangeEntity(ContractChangeEntity changeEntity) {
		this.changeEntity = changeEntity;
	}

	
}
