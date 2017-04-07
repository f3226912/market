package cn.gdeng.market.entity.lease.settings;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "market_measure_type")
public class MarketMeasureTypeEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1263858142603167629L;

	/**
     *
     */
    private Integer id;

    /**
     *市场id
     */
    private Integer marketId;

    /**
     *费项id
     */
    private Integer expId;

    /**
     *计量分类名称
     */
    private String name;

    /**
     *计量分类编码
     */
    private String code;

    /**
     *状态: 默认  1 正常  0 删除
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
    *系统计量表分类Id
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
    @Column(name = "expId")
    public Integer getExpId(){

        return this.expId;
    }
    public void setExpId(Integer expId){

        this.expId = expId;
    }
    @Column(name = "name")
    public String getName(){

        return this.name;
    }
    public void setName(String name){

        this.name = name;
    }
    @Column(name = "code")
    public String getCode(){

        return this.code;
    }
    public void setCode(String code){

        this.code = code;
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
}

