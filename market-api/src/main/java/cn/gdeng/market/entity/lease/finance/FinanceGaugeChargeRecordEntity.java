package cn.gdeng.market.entity.lease.finance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *	
 *	计量表抄表记录
 */
@Entity(name = "finance_gauge_charge_record")
public class FinanceGaugeChargeRecordEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5754239615274655831L;

	/**
    *
    */
    private Long id;
    
    /**
    * 市场id
    */
    private Integer marketId;
    
    /**
    *计量表id
    */
    private Integer measureId;

    /**
    *合同版本号
    */
    private Integer contractVersion;

    /**
    *合同ID
    */
    private String contractNo;

    /**
    *抄表日期
    */
    private Date noteDate;

    /**
    *上次抄表日期
    */
    private Date lastNoteDate;

    /**
    *本次读数
    */
    private Double current;

    /**
    *上次读数
    */
    private Double last;

    /**
    *损耗用量
    */
    private Double wastage;

    /**
    *计费单价
    */
    private Double price;

    /**
    *收费金额
    */
    private Double amount;

    /**
    *收款状态(1已收款0未收款)
    */
    private String status;

    /**
    *客户名称
    */
    private String customerName;

    /**
    *乙方
    */
    private String partyB;

    /**
    *资源名称
    */
    private Long resourceName;

    /**
    *收款人
    */
    private String receiver;

    /**
    *收款日期
    */
    private Date receiveDate;

    /**
    *抄表人
    */
    private String noteUser;

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
     * 备注
     */
    private String remark ;

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
	@Column(name = "measureId")
    public Integer getMeasureId(){

        return this.measureId;
    }
    public void setMeasureId(Integer measureId){

        this.measureId = measureId;
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
    @Column(name = "noteDate")
    public Date getNoteDate(){

        return this.noteDate;
    }
    public void setNoteDate(Date noteDate){

        this.noteDate = noteDate;
    }
    @Column(name = "lastNoteDate")
    public Date getLastNoteDate(){

        return this.lastNoteDate;
    }
    public void setLastNoteDate(Date lastNoteDate){

        this.lastNoteDate = lastNoteDate;
    }
    @Column(name = "current")
    public Double getCurrent(){

        return this.current;
    }
    public void setCurrent(Double current){

        this.current = current;
    }
    @Column(name = "last")
    public Double getLast(){

        return this.last;
    }
    public void setLast(Double last){

        this.last = last;
    }
    @Column(name = "wastage")
    public Double getWastage(){

        return this.wastage;
    }
    public void setWastage(Double wastage){

        this.wastage = wastage;
    }
    @Column(name = "price")
    public Double getPrice(){

        return this.price;
    }
    public void setPrice(Double price){

        this.price = price;
    }
    @Column(name = "amount")
    public Double getAmount(){

        return this.amount;
    }
    public void setAmount(Double amount){

        this.amount = amount;
    }
    @Column(name = "status")
    public String getStatus(){

        return this.status;
    }
    public void setStatus(String status){

        this.status = status;
    }
    @Column(name = "customerName")
    public String getCustomerName(){

        return this.customerName;
    }
    public void setCustomerName(String customerName){

        this.customerName = customerName;
    }
    @Column(name = "partyB")
    public String getPartyB(){

        return this.partyB;
    }
    public void setPartyB(String partyB){

        this.partyB = partyB;
    }
    @Column(name = "resourceName")
    public Long getResourceName(){

        return this.resourceName;
    }
    public void setResourceName(Long resourceName){

        this.resourceName = resourceName;
    }
    @Column(name = "receiver")
    public String getReceiver(){

        return this.receiver;
    }
    public void setReceiver(String receiver){

        this.receiver = receiver;
    }
    @Column(name = "receiveDate")
    public Date getReceiveDate(){

        return this.receiveDate;
    }
    public void setReceiveDate(Date receiveDate){

        this.receiveDate = receiveDate;
    }
    @Column(name = "noteUser")
    public String getNoteUser(){

        return this.noteUser;
    }
    public void setNoteUser(String noteUser){

        this.noteUser = noteUser;
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
    
    @Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
    
}