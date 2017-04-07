package cn.gdeng.market.entity.lease.resources;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "market_area_building")
public class MarketAreaBuildingEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4143588035759007116L;

	/**
     *
     */
    private Integer id;
    /**
     *区域id
     */
    private Integer areaId;
    /**
     *楼栋名称
     */
    private String name;
    /**
     *楼栋编号
     */
    private String code;
    /**
     *楼栋性质 1 底层  2  多层  3 小高层  4 高层
     */
    private Integer nature;
    /**
     *楼栋图片
     */
    private String buildingImage;
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
    @Column(name = "areaId")
    public Integer getAreaId(){
        return this.areaId;
    }
    public void setAreaId(Integer areaId){
        this.areaId = areaId;
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
    @Column(name = "nature")
    public Integer getNature(){
        return this.nature;
    }
    public void setNature(Integer nature){
        this.nature = nature;
    }
    @Column(name = "buildingImage")
    public String getBuildingImage(){
        return this.buildingImage;
    }
    public void setBuildingImage(String buildingImage){
        this.buildingImage = buildingImage;
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
