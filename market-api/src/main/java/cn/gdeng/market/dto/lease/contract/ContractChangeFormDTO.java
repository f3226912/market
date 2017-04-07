package cn.gdeng.market.dto.lease.contract;

import java.util.Date;
import java.util.List;

public class ContractChangeFormDTO {
	/**
	 * 合同ID
	 */
	private String contractId;
	/**
	 * 缴费周期
	 */
	private int paymentCycle;
	/**
	 * 缴费日期类型
	 */
	private int paymentDayType;
	/**
	 * 缴费天数
	 */
	private int paymentDays;	
	/**
	 * 按周期收费-租赁约定记录
	 */
	private List<ContractLeaseDTO> leaseList;
	/**
	 * 按周期收费-免租约定记录
	 */
	private List<ContractFreeDTO> freeList;
	/**
	 * 按周期收费-其他费约定记录
	 */
	private List<ContractOthersFeeDTO> othersFeeList;
	/////////////////////////////////////////////按约定总金额//////////////////////////////////////////
	/**
	 * 按约定总金额-合同总金额
	 */
	private double totalAmt;
	/**
	 * 按约定总金额-缴费约定记录
	 */
	private List<ContractPaymentDTO> paymentList;
	//////////////////////////////////////////////变更原因////////////////////////////////////////
	/**
	 * 经办人
	 */
	private String trustees;
	/**
	 * 经办时间
	 */
	private Date reviewedTime;
	/**
	 * 变更原因
	 */
	private int changeReasion;
	/**
	 * 变更说明
	 */
	private String info;
	
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getTrustees() {
		return trustees;
	}
	public void setTrustees(String trustees) {
		this.trustees = trustees;
	}
	public Date getReviewedTime() {
		return reviewedTime;
	}
	public void setReviewedTime(Date reviewedTime) {
		this.reviewedTime = reviewedTime;
	}
	public int getPaymentCycle() {
		return paymentCycle;
	}
	public void setPaymentCycle(int paymentCycle) {
		this.paymentCycle = paymentCycle;
	}
	public int getPaymentDayType() {
		return paymentDayType;
	}
	public void setPaymentDayType(int paymentDayType) {
		this.paymentDayType = paymentDayType;
	}
	public int getPaymentDays() {
		return paymentDays;
	}
	public void setPaymentDays(int paymentDays) {
		this.paymentDays = paymentDays;
	}
	public List<ContractLeaseDTO> getLeaseList() {
		return leaseList;
	}
	public void setLeaseList(List<ContractLeaseDTO> leaseList) {
		this.leaseList = leaseList;
	}
	public List<ContractFreeDTO> getFreeList() {
		return freeList;
	}
	public void setFreeList(List<ContractFreeDTO> freeList) {
		this.freeList = freeList;
	}
	public List<ContractOthersFeeDTO> getOthersFeeList() {
		return othersFeeList;
	}
	public void setOthersFeeList(List<ContractOthersFeeDTO> othersFeeList) {
		this.othersFeeList = othersFeeList;
	}
	public double getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(double totalAmt) {
		this.totalAmt = totalAmt;
	}
	public List<ContractPaymentDTO> getPaymentList() {
		return paymentList;
	}
	public void setPaymentList(List<ContractPaymentDTO> paymentList) {
		this.paymentList = paymentList;
	}
	public int getChangeReasion() {
		return changeReasion;
	}
	public void setChangeReasion(int changeReasion) {
		this.changeReasion = changeReasion;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
}
