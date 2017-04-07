package cn.gdeng.market.entity.lease.finance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 *	财务实收款记录
 */
@Entity(name = "finance_fee_received")
public class FinanceFeeReceivedEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4877237345857867602L;

	/**
    *
    */
    private Long id;
    
    /**
    * 市场id
    */
    private Integer marketId;

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
    *	计费标准id
    */
    private Integer freightBasisId;

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
    private Date startTime;

    /**
    *计费结束日期
    */
    private Date endTime;

    /**
    *应付金额
    */
    private Double accountPayable;

    /**
    *实付金额
    */
    private Double accountPayed;

    /**
    * 优惠金额
    */
    private Double accountRebate ;
    
    /**
    *收款说明
    */
    private String remark;

    /**
    *交款人
    */
    private String payer;

    /**
    *手机号
    */
    private String phone;

    /**
    *经办人
    */
    private String agent;

    /**
    *款项类型: 0-正常款项,1-临时款项,2-退款
    */
    private String fundType;

    /**
    *收款日期
    */
    private Date receiveDate;


    /**
     * 实收款对应的应收款id
     */
    private Integer needReceiveId ;
    
    /**
    *创建用户
    */
    private String createUserId;

    /**
    *创建时间
    */
    private Date createTime;

    /**
    *更新用户
    */
    private String updateUserId;

    /**
    *更新时间
    */
    private Date updateTime;
    

    @Id
    @Column(name = "id")
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    
    @Column(name = "marketId")
    public Integer getMarketId() {
		return marketId;
	}
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}
	@Column(name = "contractVersion")
    public Integer getContractVersion(){

        return this.contractVersion;
    }
    public void setContractVersion(Integer contractVersion){

        this.contractVersion = contractVersion;
    }
    @Column(name = "contractNo")
    public String getContractNo(){

        return this.contractNo;
    }
    public void setContractNo(String contractNo){

        this.contractNo = contractNo;
    }
    @Column(name = "feeItemId")
    public Integer getFeeItemId(){

        return this.feeItemId;
    }
    public void setFeeItemId(Integer feeItemId){

        this.feeItemId = feeItemId;
    }
    
    @Column(name = "feeItemTypeId")
    public String getFeeItemTypeId() {
		return feeItemTypeId;
	}
	public void setFeeItemTypeId(String feeItemTypeId) {
		this.feeItemTypeId = feeItemTypeId;
	}
    @Column(name = "timeLimit")
    public Date getTimeLimit(){

        return this.timeLimit;
    }
    public void setTimeLimit(Date timeLimit){

        this.timeLimit = timeLimit;
    }
    @Column(name = "startTime")
    public Date getStartTime(){

        return this.startTime;
    }
    public void setStartTime(Date startTime){

        this.startTime = startTime;
    }
    @Column(name = "endTime")
    public Date getEndTime(){

        return this.endTime;
    }
    public void setEndTime(Date endTime){

        this.endTime = endTime;
    }
    @Column(name = "accountPayable")
    public Double getAccountPayable(){

        return this.accountPayable;
    }
    public void setAccountPayable(Double accountPayable){

        this.accountPayable = accountPayable;
    }
    @Column(name = "accountPayed")
    public Double getAccountPayed(){

        return this.accountPayed;
    }
    public void setAccountPayed(Double accountPayed){

        this.accountPayed = accountPayed;
    }
    @Column(name = "remark")
    public String getRemark(){

        return this.remark;
    }
    public void setRemark(String remark){

        this.remark = remark;
    }
    @Column(name = "payer")
    public String getPayer(){

        return this.payer;
    }
    public void setPayer(String payer){

        this.payer = payer;
    }
    @Column(name = "phone")
    public String getPhone(){

        return this.phone;
    }
    public void setPhone(String phone){

        this.phone = phone;
    }
    @Column(name = "agent")
    public String getAgent(){

        return this.agent;
    }
    public void setAgent(String agent){

        this.agent = agent;
    }
    @Column(name = "fundType")
    public String getFundType(){

        return this.fundType;
    }
    public void setFundType(String fundType){

        this.fundType = fundType;
    }
    @Column(name = "receiveDate")
    public Date getReceiveDate(){

        return this.receiveDate;
    }
    public void setReceiveDate(Date receiveDate){

        this.receiveDate = receiveDate;
    }
    @Column(name = "createUserId")
    public String getCreateUserId(){

        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){

        this.createUserId = createUserId;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){

        this.updateUserId = updateUserId;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
	@Column(name = "accountRebate")
	public Double getAccountRebate() {
		return accountRebate;
	}
	public void setAccountRebate(Double accountRebate) {
		this.accountRebate = accountRebate;
	}
	@Column(name = "freightBasisId")
	public Integer getFreightBasisId() {
		return freightBasisId;
	}
	public void setFreightBasisId(Integer freightBasisId) {
		this.freightBasisId = freightBasisId;
	}
	@Column(name = "needReceiveId")
	public Integer getNeedReceiveId() {
		return needReceiveId;
	}
	public void setNeedReceiveId(Integer needReceiveId) {
		this.needReceiveId = needReceiveId;
	}
	
}