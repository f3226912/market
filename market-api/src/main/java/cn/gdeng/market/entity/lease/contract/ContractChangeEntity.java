package cn.gdeng.market.entity.lease.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "contract_change")
public class ContractChangeEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4983040746614082003L;

	/**
     *主键id
     */
    private Integer id;
    /**
     *变更前合同ID
     */
    private Integer contractId;
    /**
     *市场id
     */
    private Integer marketId;
    
    /**
     *变更后合同ID
     */
    private Integer contractNewId;
    
    /**
     *原始合同ID
     */
    private Integer contractRootId;
    /**
     *合同编号
     */
    private String contractNo;
    /**
     *经办人
     */
    private String trustees;
    /**
     *经办时间
     */
    private Date reviewedTime;
    /**
     *变更说明
     */
    private String info;
    /**
     *变更原因 0 租金水平调整 1 费项增减 2 租金减免
     */
    private Byte changeReasion;
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
    
    @Column(name = "contractNewId")
    public Integer getContractNewId() {
		return contractNewId;
	}
	public void setContractNewId(Integer contractNewId) {
		this.contractNewId = contractNewId;
	}
	@Column(name = "contractRootId")
	public Integer getContractRootId() {
		return contractRootId;
	}
	public void setContractRootId(Integer contractRootId) {
		this.contractRootId = contractRootId;
	}
	@Column(name = "contractNo")
    public String getContractNo(){
        return this.contractNo;
    }
    public void setContractNo(String contractNo){
        this.contractNo = contractNo;
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
    @Column(name = "changeReasion")
    public Byte getChangeReasion(){
        return this.changeReasion;
    }
    public void setChangeReasion(Byte changeReasion){
        this.changeReasion = changeReasion;
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
    @Column(name = "marketId")
	public Integer getMarketId() {
		return marketId;
	}
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	
    
}
