package cn.gdeng.market.entity.lease.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * 租赁约定总金额 约定信息
 * @author Administrator
 *
 */
@Entity(name = "contract_payment")
public class ContractPaymentEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2668636876699934853L;

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
     *缴费期限
     */
    private Date paymentTime;
    /**
     *缴费金额
     */
    private Double paymentAmt;
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
     *缴费期限json 转换代替
     */
    private String paymentTimeStr;

	public String getPaymentTimeStr() {
		return paymentTimeStr;
	}

	public void setPaymentTimeStr(String paymentTimeStr) {
		this.paymentTimeStr = paymentTimeStr;
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
    @Column(name = "paymentTime")
    public Date getPaymentTime(){
        return this.paymentTime;
    }
    public void setPaymentTime(Date paymentTime){
        this.paymentTime = paymentTime;
    }
    @Column(name = "paymentAmt")
    public Double getPaymentAmt(){
        return this.paymentAmt;
    }
    public void setPaymentAmt(Double paymentAmt){
        this.paymentAmt = paymentAmt;
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
