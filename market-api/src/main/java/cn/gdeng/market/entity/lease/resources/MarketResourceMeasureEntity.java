package cn.gdeng.market.entity.lease.resources;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "market_resource_measure")
public class MarketResourceMeasureEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6239602291204980202L;

	/**
     *
     */
    private Integer id;
    /**
     *计量表id
     */
    private Integer measureId;
    /**
     *资源id
     */
    private Integer resourceId;
    /**
     *状态:  默认  1  正常   0 删除
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
    @Column(name = "measureId")
    public Integer getMeasureId(){
        return this.measureId;
    }
    public void setMeasureId(Integer measureId){
        this.measureId = measureId;
    }
    @Column(name = "resourceId")
    public Integer getResourceId(){
        return this.resourceId;
    }
    public void setResourceId(Integer resourceId){
        this.resourceId = resourceId;
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
