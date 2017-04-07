package cn.gdeng.market.lease.controller.lease.finance.querybean;

import java.util.Date;

public class FinanceFeeParamBean {

    private String id;

    /**
    *合同版本号
    */
    private Integer contractVersion;

    /**
    *合同编号
    */
    private String contractNo;

    /**
    *费项id
    */
    private Integer feeItemId;
    
    /**
     * 费项类型id
     */
    private String feeItemTypeId ;

    /**
    *缴费期限
    */
    private Date timeLimit;

    /**
    *计费起始日期
    */
    private String startTime;

    /**
    *计费结束日期
    */
    private String endTime;

    /**
    *	款项类型: 0-正常款项,1-临时款项,2-退款
    */
    private String fundType;
    
    private String isEarlyWarning ;
    
    private String conditionType ;
    
    private String conditionValue ;
    
    private String queryType ;
    
	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public String getConditionValue() {
		return conditionValue;
	}

	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

	public String getIsEarlyWarning() {
		return isEarlyWarning;
	}

	public void setIsEarlyWarning(String isEarlyWarning) {
		this.isEarlyWarning = isEarlyWarning;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(Integer contractVersion) {
		this.contractVersion = contractVersion;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Integer getFeeItemId() {
		return feeItemId;
	}

	public void setFeeItemId(Integer feeItemId) {
		this.feeItemId = feeItemId;
	}

	public String getFeeItemTypeId() {
		return feeItemTypeId;
	}

	public void setFeeItemTypeId(String feeItemTypeId) {
		this.feeItemTypeId = feeItemTypeId;
	}

	public Date getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Date timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	@Override
	public String toString() {
		return "FinanceFeeParamBean [id=" + id + ", contractVersion=" + contractVersion + ", contractNo=" + contractNo
				+ ", feeItemId=" + feeItemId + ", feeItemTypeId=" + feeItemTypeId + ", timeLimit=" + timeLimit
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", fundType=" + fundType + "]";
	}

}
