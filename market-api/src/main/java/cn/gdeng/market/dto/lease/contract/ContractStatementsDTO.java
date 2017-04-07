package cn.gdeng.market.dto.lease.contract;

import java.io.Serializable;
import java.util.Date;

import cn.gdeng.market.entity.lease.contract.ContractStatementsEntity;
import cn.gdeng.market.enums.ApprovalStatusEnum;
import cn.gdeng.market.enums.ContractStatusEnum;
import cn.gdeng.market.enums.StatementsTypeEnum;

public class ContractStatementsDTO extends ContractStatementsEntity implements Serializable{

	private static final long serialVersionUID = -294220833881650738L;

	/**
     *市场id
     */
    private Integer marketId;

    /**
     *租赁资源(中文描述)
     */
    private String leasingResource;

    /**
     *租赁资源ID(,逗号隔开)
     */
    private String leasingResourceIds;

    /**
     *市场资源类型表ID
     */
    private Integer marketResourceTypeId;

    /**
     *客户名称
     */
    private String customerName;

    /**
     *客户手机号
     */
    private String customerMobile;


    /**
     *甲方
     */
    private String partyA;

    /**
     *乙方
     */
    private String partyB;

    /**
     *起租日期
     */
    private Date startLeasingDay;

    /**
     *结束日期
     */
    private Date endLeasingDay;

    /**
     *延迟交租罚金(元/日)
     */
    private Double leasingForfeit;

    /**
     *延迟还铺罚金(元/日)
     */
    private Double shopForfeit;

    /**
     *商铺名称
     */
    private String shopName;

    /**
     *经营范围 (产品分类表)
     */
    private Long categoryId;

    /**
     *可租面积
     */
    private Double freeArea;

    /**
     *建筑面积
     */
    private Double floorArea;

    /**
     *套内面积
     */
    private Double innerArea;


    /**
     *签约日期
     */
    private Date dateOfContract;

    /**
     *收费方式 0 按周期收费 1 约定总金额
     */
    private Byte chargingWays;

    /**
     *合同总金额
     */
    private Double totalAmt;

    /**
     *计费面积 0 可租面积 1 建筑面积 2 套内面积
     */
    private Byte billingArea;

    /**
     *已租面积(BI统计使用,根据计费面积同步)
     */
    private Double countArea;

    /**
     *缴费周期 0 月 1 季度 2 半年 3 年
     */
    private Byte paymentCycle;

    /**
     *缴费日期 0 缴费期初 1 缴费期未 2 下一个缴费期初
     */
    private Byte paymentDayType;

    /**
     *缴费日期(天)
     */
    private Byte paymentDays;

    /**
     *附加条款
     */
    private String additionalTerms;

    /**
     *合同状态 0 待执行 1 执行中 2 已结算
     */
    private Byte contractStatus;

  

    /**
     *审批类型 1 合同初次审批 2 合同变更审批 3 合同结算审批
     */
    private Byte approvalType;

    /**
     *审批方式 1 工作流审批 2 线下审批
     */
    private Byte approvalMethod;

    /**
     *合同初次审批时间
     */
    private Date approvalTimeA;

    /**
     *合同变更审批时间
     */
    private Date approvalTimeB;

    /**
     *合同结算审批时间
     */
    private Date approvalTimeC;

    /**
     *是否作废(0:未作废;1:已作废)
     */
    private Byte isCancel;

    /**
     *是否删除(0:未删除;1:已删除)
     */
    private Byte isDeleted;

    /**
     *版本控制
     */
    private Integer version;

    /**
	 * 审核结果状态
	 */
	private String approvalStatusValue;

	/**
	 * 合同状态
	 */
	private String contractStatusValue;
	
	/**
	 * 结算类型
	 */
	private String statementsTypeValue;
	
	/**
	 * 
	 * @作者 KWANG
	 * 根据审核状态码 返回审核结果
	 * @return String
	 */
	public String getStatementsTypeValue() {
		return StatementsTypeEnum.getNameByCode(getStatementsType());
	}

	public void setStatementsTypeValue(String statementsTypeValue) {
		this.statementsTypeValue = statementsTypeValue;
	}

	/**
	 * 
	 * @作者 KWANG
	 * 根据审核状态码 返回审核结果
	 * @return String
	 */
	public String getApprovalStatusValue() {
		return ApprovalStatusEnum.getNameByCode(getApprovalStatus());
	}

	public void setApprovalStatusValue(String approvalStatusValue) {
		this.approvalStatusValue = approvalStatusValue;
	}

	/**
	 * @author KWANG
	 * 根据合同状态码 返回合同状态结果
	 * 根据合同状态码 返回合同状态结果
	 * @return String
	 */
	public String getContractStatusValue() {
		return ContractStatusEnum.getNameByCode(getContractStatus());
	}

	
	/**
     *修改人员
     */
	private String updateUserName;
	

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public String getLeasingResource() {
		return leasingResource;
	}

	public void setLeasingResource(String leasingResource) {
		this.leasingResource = leasingResource;
	}

	public String getLeasingResourceIds() {
		return leasingResourceIds;
	}

	public void setLeasingResourceIds(String leasingResourceIds) {
		this.leasingResourceIds = leasingResourceIds;
	}

	public Integer getMarketResourceTypeId() {
		return marketResourceTypeId;
	}

	public void setMarketResourceTypeId(Integer marketResourceTypeId) {
		this.marketResourceTypeId = marketResourceTypeId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getPartyA() {
		return partyA;
	}

	public void setPartyA(String partyA) {
		this.partyA = partyA;
	}

	public String getPartyB() {
		return partyB;
	}

	public void setPartyB(String partyB) {
		this.partyB = partyB;
	}

	public Date getStartLeasingDay() {
		return startLeasingDay;
	}

	public void setStartLeasingDay(Date startLeasingDay) {
		this.startLeasingDay = startLeasingDay;
	}

	public Date getEndLeasingDay() {
		return endLeasingDay;
	}

	public void setEndLeasingDay(Date endLeasingDay) {
		this.endLeasingDay = endLeasingDay;
	}

	public Double getLeasingForfeit() {
		return leasingForfeit;
	}

	public void setLeasingForfeit(Double leasingForfeit) {
		this.leasingForfeit = leasingForfeit;
	}

	public Double getShopForfeit() {
		return shopForfeit;
	}

	public void setShopForfeit(Double shopForfeit) {
		this.shopForfeit = shopForfeit;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Double getFreeArea() {
		return freeArea;
	}

	public void setFreeArea(Double freeArea) {
		this.freeArea = freeArea;
	}

	public Double getFloorArea() {
		return floorArea;
	}

	public void setFloorArea(Double floorArea) {
		this.floorArea = floorArea;
	}

	public Double getInnerArea() {
		return innerArea;
	}

	public void setInnerArea(Double innerArea) {
		this.innerArea = innerArea;
	}

	public Date getDateOfContract() {
		return dateOfContract;
	}

	public void setDateOfContract(Date dateOfContract) {
		this.dateOfContract = dateOfContract;
	}

	public Byte getChargingWays() {
		return chargingWays;
	}

	public void setChargingWays(Byte chargingWays) {
		this.chargingWays = chargingWays;
	}

	public Double getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(Double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public Byte getBillingArea() {
		return billingArea;
	}

	public void setBillingArea(Byte billingArea) {
		this.billingArea = billingArea;
	}

	public Double getCountArea() {
		return countArea;
	}

	public void setCountArea(Double countArea) {
		this.countArea = countArea;
	}

	public Byte getPaymentCycle() {
		return paymentCycle;
	}

	public void setPaymentCycle(Byte paymentCycle) {
		this.paymentCycle = paymentCycle;
	}

	public Byte getPaymentDayType() {
		return paymentDayType;
	}

	public void setPaymentDayType(Byte paymentDayType) {
		this.paymentDayType = paymentDayType;
	}

	public Byte getPaymentDays() {
		return paymentDays;
	}

	public void setPaymentDays(Byte paymentDays) {
		this.paymentDays = paymentDays;
	}

	public String getAdditionalTerms() {
		return additionalTerms;
	}

	public void setAdditionalTerms(String additionalTerms) {
		this.additionalTerms = additionalTerms;
	}

	public Byte getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(Byte contractStatus) {
		this.contractStatus = contractStatus;
	}


	public Byte getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(Byte approvalType) {
		this.approvalType = approvalType;
	}

	public Byte getApprovalMethod() {
		return approvalMethod;
	}

	public void setApprovalMethod(Byte approvalMethod) {
		this.approvalMethod = approvalMethod;
	}

	public Date getApprovalTimeA() {
		return approvalTimeA;
	}

	public void setApprovalTimeA(Date approvalTimeA) {
		this.approvalTimeA = approvalTimeA;
	}

	public Date getApprovalTimeB() {
		return approvalTimeB;
	}

	public void setApprovalTimeB(Date approvalTimeB) {
		this.approvalTimeB = approvalTimeB;
	}

	public Date getApprovalTimeC() {
		return approvalTimeC;
	}

	public void setApprovalTimeC(Date approvalTimeC) {
		this.approvalTimeC = approvalTimeC;
	}

	public Byte getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Byte isCancel) {
		this.isCancel = isCancel;
	}

	public Byte getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void setContractStatusValue(String contractStatusValue) {
		this.contractStatusValue = contractStatusValue;
	}

	
    
    
    

}
