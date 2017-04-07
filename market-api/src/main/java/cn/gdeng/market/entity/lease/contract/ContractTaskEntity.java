package cn.gdeng.market.entity.lease.contract;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "contract_task")
public class ContractTaskEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6633319091533203576L;

	/**
     *主键id
     */
    private Integer id;

    /**
     *合同ID
     */
    private Integer contractId;

    /**
     *定时任务处理状态 1 处理成功 2 处理失败
     */
    private Byte taskStatus;

    /**
     *备注
     */
    private String remark;

    /**
     *创建时间
     */
    private Date createTime;

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
    @Column(name = "taskStatus")
    public Byte getTaskStatus(){

        return this.taskStatus;
    }
    public void setTaskStatus(Byte taskStatus){

        this.taskStatus = taskStatus;
    }
    @Column(name = "remark")
    public String getRemark(){

        return this.remark;
    }
    public void setRemark(String remark){

        this.remark = remark;
    }
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
}

