package cn.gdeng.market.lease.controller.lease.finance.querybean;

public class PrintParamBean {

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
     * 费项名称
     */
    private String feeItemName ;
    
    /**
     * 本次实收金额
     */
    private String accountPayed ;
    
    /**
     * 交款人
     */
    private String reciever ;
    
    /**
     * 手机号码
     */
    private String phone ;
    
    /**
     * 经办人
     */
    private String agent ;

    /**
     * 经办时间
     */
    private String agentTime ;
    
    /**
     * 收款说明
     */
    private String remark ;

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

	public String getFeeItemName() {
		return feeItemName;
	}

	public void setFeeItemName(String feeItemName) {
		this.feeItemName = feeItemName;
	}

	public String getAccountPayed() {
		return accountPayed;
	}

	public void setAccountPayed(String accountPayed) {
		this.accountPayed = accountPayed;
	}

	public String getReciever() {
		return reciever;
	}

	public void setReciever(String reciever) {
		this.reciever = reciever;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getAgentTime() {
		return agentTime;
	}

	public void setAgentTime(String agentTime) {
		this.agentTime = agentTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
    

}
