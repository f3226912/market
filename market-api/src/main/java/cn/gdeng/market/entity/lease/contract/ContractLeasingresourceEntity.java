package cn.gdeng.market.entity.lease.contract;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "contract_leasingresource")
public class ContractLeasingresourceEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8603720309091545670L;

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
     *资源名称
     */
    private String leasingResource;
    /**
     *租赁资源ID
     */
    private Integer leasingResourceId;
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
    @Column(name = "leasingResource")
    public String getLeasingResource(){
        return this.leasingResource;
    }
    public void setLeasingResource(String leasingResource){
        this.leasingResource = leasingResource;
    }
    @Column(name = "leasingResourceId")
    public Integer getLeasingResourceId(){
        return this.leasingResourceId;
    }
    public void setLeasingResourceId(Integer leasingResourceId){
        this.leasingResourceId = leasingResourceId;
    }
}
