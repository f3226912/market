package cn.gdeng.market.dto.lease.finance;

import cn.gdeng.market.entity.lease.finance.FinanceFeeReceivedEntity;

public class FinanceFeeReceivedDTO extends FinanceFeeReceivedEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4804980215068277237L;

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
     * 金额
     */
    private Double amount ;
    
    private String timeLimitString ;
    
    private String startTimeString ;
    
    private String endTimeString ;
    
    private String createTimeString ;
    
    private String updateTimeString ;
    
    private String accountPayableString ;
    
    private String accountPayedString ;
    
    private String accountRebateString ;
    
    private String amountString ;

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

	public String getFeeItemName() {
		return feeItemName;
	}

	public void setFeeItemName(String feeItemName) {
		this.feeItemName = feeItemName;
	}

	public String getFeeItemTypeName() {
		return feeItemTypeName;
	}

	public void setFeeItemTypeName(String feeItemTypeName) {
		this.feeItemTypeName = feeItemTypeName;
	}

	public String getResourceNames() {
		return resourceNames;
	}

	public void setResourceNames(String resourceNames) {
		this.resourceNames = resourceNames;
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

	public String getAccountRebateString() {
		return accountRebateString;
	}

	public void setAccountRebateString(String accountRebateString) {
		this.accountRebateString = accountRebateString;
	}
    
}
