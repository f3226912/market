 package cn.gdeng.market.entity.lease.resources;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 市场楼栋单元表
 * */
@Entity(name = "market_building_unit")
public class MarketBuildingUnitEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7654946592398595999L;

	/**
     *
     */
    private Integer id;
    /**
     *楼栋id
     */
    private Integer buildingId;
    /**
     *单元序号：比如1，2，3
     */
    private Integer unitNo;
	/**
     *单元名称
     */
    private String name;
    /**
     *楼层图
     */
    private String unitImage;
    /**
     *状态: 默认 1 正常  0 删除
     */
    private Integer status;
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
    @Id
    @Column(name = "id")
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    @Column(name = "buildingId")
    public Integer getBuildingId(){
        return this.buildingId;
    }
    public void setBuildingId(Integer buildingId){
        this.buildingId = buildingId;
    }
    @Column(name = "unitNo")
    public Integer getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(Integer unitNo) {
		this.unitNo = unitNo;
	}
    @Column(name = "name")
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    @Column(name = "unitImage")
    public String getUnitImage(){
        return this.unitImage;
    }
    public void setUnitImage(String unitImage){
        this.unitImage = unitImage;
    }
    @Column(name = "status")
    public Integer getStatus(){
        return this.status;
    }
    public void setStatus(Integer status){
        this.status = status;
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
