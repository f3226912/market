package cn.gdeng.market.entity.lease.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * 租赁周期 约定信息
 * @author Administrator
 *
 */
@Entity(name = "contract_lease")
public class ContractLeaseEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4076601095113077597L;

	/**
     *主键id
     */
    private Integer id;
    /**
     *合同ID
     */
    private Integer contractId;
    /**
     *合同编号
     */
    private String contractNo;
    /**
     *起始日期
     */
    private Date startDay;
    /**
     *截止日期
     */
    private Date endDay;
    /**
     *计费单位 0 元/月/平 1 元/季/平 2 元/半年/平 3 元/年/平
     */
    private Byte billingUnit;
    /**
     *计费面积
     */
    private Double billingArea;
    /**
     *租金单价
     */
    private Double unitPrice;
    /**
     *租金
     */
    private Double rental;
    /**
     *是否删除(0:未删除;1:已删除)
     */
    private Byte isDeleted;
    /**
     *创建人员ID
     */
    private String createUserId;
    /**
     *创建时间
     */
    private Date createTime;
    /**
     *修改人员ID
     */
    private String updateUserId;
    /**
     *修改时间
     */
    private Date updateTime;
    /**
     *起始日期 json 转换代替
     */
    private String startDayStr;
    /**
     *截止日期json 转换代替
     */
    private String endDayStr;
	public String getStartDayStr() {
		return startDayStr;
	}
	public void setStartDayStr(String startDayStr) {
		this.startDayStr = startDayStr;
	}
	public String getEndDayStr() {
		return endDayStr;
	}
	public void setEndDayStr(String endDayStr) {
		this.endDayStr = endDayStr;
	}    @Id
    @Column(name = "id")
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    @Column(name = "contractId")
    public Integer getContractId(){
        return this.contractId;
    }
    public void setContractId(Integer contractId){
        this.contractId = contractId;
    }
    @Column(name = "contractNo")
    public String getContractNo(){
        return this.contractNo;
    }
    public void setContractNo(String contractNo){
        this.contractNo = contractNo;
    }
    @Column(name = "startDay")
    public Date getStartDay(){
        return this.startDay;
    }
    public void setStartDay(Date startDay){
        this.startDay = startDay;
    }
    @Column(name = "endDay")
    public Date getEndDay(){
        return this.endDay;
    }
    public void setEndDay(Date endDay){
        this.endDay = endDay;
    }
    @Column(name = "billingUnit")
    public Byte getBillingUnit(){
        return this.billingUnit;
    }
    public void setBillingUnit(Byte billingUnit){
        this.billingUnit = billingUnit;
    }
    @Column(name = "billingArea")
    public Double getBillingArea(){
        return this.billingArea;
    }
    public void setBillingArea(Double billingArea){
        this.billingArea = billingArea;
    }
    @Column(name = "unitPrice")
    public Double getUnitPrice(){
        return this.unitPrice;
    }
    public void setUnitPrice(Double unitPrice){
        this.unitPrice = unitPrice;
    }
    @Column(name = "rental")
    public Double getRental(){
        return this.rental;
    }
    public void setRental(Double rental){
        this.rental = rental;
    }
    @Column(name = "isDeleted")
    public Byte getIsDeleted(){
        return this.isDeleted;
    }
    public void setIsDeleted(Byte isDeleted){
        this.isDeleted = isDeleted;
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
}
