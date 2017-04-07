package cn.gdeng.market.entity.lease.settings;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 费项
 * @author jiangyanli
 *
 */
@Entity(name = "market_expenditure")
public class MarketExpenditureEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6961756924504873666L;

	/**
     *
     */
    private Integer id;

    /**
     *市场id
     */
    private Integer marketId;

    /**
     *费项名称
     */
    private String name;

    /**
     *费项类型  1 周期性费项  2 走表类费项  3  一次性费项   4 临时性费项
     */
    private Integer expType;

    /**
     *费项说明
     */
    private String expDetail;

    /**
     *状态:  默认  1 正常  0  删除
     */
    private Integer status;

    /**
     *是否系统级类型  1 是  0 不是 默认
     */
    private Integer sysType;

    /**
     *
     */
    private String createUserId;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private String updateUserId;

    /**
     *
     */
    private Date updateTime;
    
    /**
    *系统费项Id
    */
   private Integer parentId;
   
   	@Column(name = "parentId")
    public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	@Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "marketId")
    public Integer getMarketId(){

        return this.marketId;
    }
    public void setMarketId(Integer marketId){

        this.marketId = marketId;
    }
    @Column(name = "name")
    public String getName(){

        return this.name;
    }
    public void setName(String name){

        this.name = name;
    }
    @Column(name = "expType")
    public Integer getExpType(){

        return this.expType;
    }
    public void setExpType(Integer expType){

        this.expType = expType;
    }
    @Column(name = "expDetail")
    public String getExpDetail(){

        return this.expDetail;
    }
    public void setExpDetail(String expDetail){

        this.expDetail = expDetail;
    }
    @Column(name = "status")
    public Integer getStatus(){

        return this.status;
    }
    public void setStatus(Integer status){

        this.status = status;
    }
    @Column(name = "sysType")
    public Integer getSysType(){

        return this.sysType;
    }
    public void setSysType(Integer sysType){

        this.sysType = sysType;
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
	@Override
	public String toString() {
		return "MarketExpenditureEntity [id=" + id + ", marketId=" + marketId + ", name=" + name + ", expType="
				+ expType + ", expDetail=" + expDetail + ", status=" + status + ", createUserId=" + createUserId
				+ ", createTime=" + createTime + ", updateUserId=" + updateUserId + ", updateTime=" + updateTime + "]";
	}
    
    
}

