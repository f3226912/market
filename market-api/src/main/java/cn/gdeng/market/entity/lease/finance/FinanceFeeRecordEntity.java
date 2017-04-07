package cn.gdeng.market.entity.lease.finance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 *	财务应收款记录
 */
@Entity(name = "finance_fee_record")
public class FinanceFeeRecordEntity  implements java.io.Serializable{
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
    * 不要按照字段的单词含义去理解, 此处对应合同主键id
    */
    private Integer contractVersion;

    /**
    *合同编号
    */
    private String contractNo;

    /**
    * 收款状态
    */
    private String status;

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
//    @JsonSerialize(using=DateJsonSerializer.class)	
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
    * 金额
    */
    private Double accountPayable;


    /**
    * 优惠金额
    */
    private Double accountRebate ;
    

    /**
    *款项类型: 0-正常款项,1-临时款项,2-退款
    */
    private String fundType;

    /**
    *是否补交记录: 1-否, 2-是
    */
    private String isRemedy;

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
    
    /**
     * 是否删除
     */
    private String isDeteled ;

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
    @Column(name = "fundType")
    public String getFundType(){

        return this.fundType;
    }
    public void setFundType(String fundType){

        this.fundType = fundType;
    }
    @Column(name = "isRemedy")
    public String getIsRemedy(){

        return this.isRemedy;
    }
    public void setIsRemedy(String isRemedy){

        this.isRemedy = isRemedy;
    }
    
    @Column(name = "feeItemTypeId")
    public String getFeeItemTypeId() {
		return feeItemTypeId;
	}
	public void setFeeItemTypeId(String feeItemTypeId) {
		this.feeItemTypeId = feeItemTypeId;
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
    @Column(name = "isDeteled")
	public String getIsDeteled() {
		return isDeteled;
	}
	public void setIsDeteled(String isDeteled) {
		this.isDeteled = isDeteled;
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
	@Column(name = "status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
}