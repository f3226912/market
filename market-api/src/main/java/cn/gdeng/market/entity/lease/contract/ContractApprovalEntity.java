package cn.gdeng.market.entity.lease.contract;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "contract_approval")
public class ContractApprovalEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1996289296840786582L;

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
     *审核类型 0 合同初次审核 1 合同变更审核 2合同结算审核
     */
    private Byte approvalType;
    /**
     *经办人
     */
    private String trustees;
    /**
     *审核时间
     */
    private Date reviewedTime;
    /**
     *审核意见
     */
    private String info;
    /**
     *审核结果 0 通过 1 驳回
     */
    private Byte approvalStatus;
    /**
     *审批方式 1 工作流审批 2 线下审批
     */
    private Byte approvalMethod;
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
    @Id
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
    @Column(name = "approvalType")
    public Byte getApprovalType(){
        return this.approvalType;
    }
    public void setApprovalType(Byte approvalType){
        this.approvalType = approvalType;
    }
    @Column(name = "trustees")
    public String getTrustees(){
        return this.trustees;
    }
    public void setTrustees(String trustees){
        this.trustees = trustees;
    }
    @Column(name = "reviewedTime")
    public Date getReviewedTime(){
        return this.reviewedTime;
    }
    public void setReviewedTime(Date reviewedTime){
        this.reviewedTime = reviewedTime;
    }
    @Column(name = "info")
    public String getInfo(){
        return this.info;
    }
    public void setInfo(String info){
        this.info = info;
    }
    @Column(name = "approvalStatus")
    public Byte getApprovalStatus(){
        return this.approvalStatus;
    }
    public void setApprovalStatus(Byte approvalStatus){
        this.approvalStatus = approvalStatus;
    }
    @Column(name = "approvalMethod")
    public Byte getApprovalMethod(){
        return this.approvalMethod;
    }
    public void setApprovalMethod(Byte approvalMethod){
        this.approvalMethod = approvalMethod;
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
