package cn.gdeng.market.dto.lease.finance;

import cn.gdeng.market.entity.lease.finance.FinanceFeeRecordEntity;

public class FinanceFeeRecordDTO extends FinanceFeeRecordEntity{

    /**
     * 费项名称
     */
    private String feeItemName ;
    
    /**
     * 费项类型名称
     */
    private String feeItemTypeName ;
    
    /**
     * 资源名称
     */
    private String resourceNames ;
    
    /**
     * 客户名称
     */
    private String customerName ;
    
    /**
     * 客户手机号码
     */
    private String customerMobile ;
    
    /**
     * 乙方
     */
    private String partyB ;
    
    /**
     * 收款后的应收款对应的实收金额
     */
    private Double accountPayed ;
    
    /**
     * 金额
     */
    private Double amount ;
    
    private String payer;
    
    private String phone ;
    
    private String remark ;
    
    private String timeLimitString ;
    
    private String startTimeString ;
    
    private String endTimeString ;
    
    private String createTimeString ;
    
    private String updateTimeString ;
    
    private String accountPayableString ;
    
    private String accountPayedString ;
    
    private String accountRebateString ;
    
    private String amountString ;
    
    
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
	public String getPartyB() {
		return partyB;
	}
	public void setPartyB(String partyB) {
		this.partyB = partyB;
	}
	public String getTimeLimitString() {
		return timeLimitString;
	}
	public void setTimeLimitString(String timeLimitString) {
		this.timeLimitString = timeLimitString;
	}
	public String getStartTimeString() {
		return startTimeString;
	}
	public void setStartTimeString(String startTimeString) {
		this.startTimeString = startTimeString;
	}
	public String getEndTimeString() {
		return endTimeString;
	}
	public void setEndTimeString(String endTimeString) {
		this.endTimeString = endTimeString;
	}
	public String getCreateTimeString() {
		return createTimeString;
	}
	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}
	public String getUpdateTimeString() {
		return updateTimeString;
	}
	public void setUpdateTimeString(String updateTimeString) {
		this.updateTimeString = updateTimeString;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -1015805297148069826L;
	
	public String getFeeItemTypeName() {
		return feeItemTypeName;
	}
	public void setFeeItemTypeName(String feeItemTypeName) {
		this.feeItemTypeName = feeItemTypeName;
	}
	public String getFeeItemName() {
		return feeItemName;
	}
	public void setFeeItemName(String feeItemName) {
		this.feeItemName = feeItemName;
	}
	public String getResourceNames() {
		return resourceNames;
	}
	public void setResourceNames(String resourceNames) {
		this.resourceNames = resourceNames;
	}
	public Double getAccountPayed() {
		return accountPayed;
	}
	public void setAccountPayed(Double accountPayed) {
		this.accountPayed = accountPayed;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAccountRebateString() {
		return accountRebateString;
	}
	public void setAccountRebateString(String accountRebateString) {
		this.accountRebateString = accountRebateString;
	}
	public String getAccountPayableString() {
		return accountPayableString;
	}
	public void setAccountPayableString(String accountPayableString) {
		this.accountPayableString = accountPayableString;
	}
	public String getAccountPayedString() {
		return accountPayedString;
	}
	public void setAccountPayedString(String accountPayedString) {
		this.accountPayedString = accountPayedString;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getAmountString() {
		return amountString;
	}
	public void setAmountString(String amountString) {
		this.amountString = amountString;
	}
	
}
