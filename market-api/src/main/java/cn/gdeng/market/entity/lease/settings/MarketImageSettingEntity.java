package cn.gdeng.market.entity.lease.settings;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "market_image_setting")
public class MarketImageSettingEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -963122678661357583L;

	/**
     *id
     */
    private Integer id;
    /**
     *市场id
     */
    private Integer marketId;
    /**
     *区域id
     */
    private Integer areaId;
    /**
     *楼栋id
     */
    private Integer buildingId;
    /**
     *单元id
     */
    private Integer unitId;
    /**
     *楼层id
     */
    private Integer floorId;
    /**
     *资源id
     */
    private Integer resourceId;
    /**
     *图片类型  1 市场  2  区域   3  楼层
     */
    private Integer imageType;
    /**
     *坐标
     */
    private String coordinate;
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
    @Column(name = "marketId")
    public Integer getMarketId(){
        return this.marketId;
    }
    public void setMarketId(Integer marketId){
        this.marketId = marketId;
    }
    @Column(name = "areaId")
    public Integer getAreaId(){
        return this.areaId;
    }
    public void setAreaId(Integer areaId){
        this.areaId = areaId;
    }
    @Column(name = "buildingId")
    public Integer getBuildingId(){
        return this.buildingId;
    }
    public void setBuildingId(Integer buildingId){
        this.buildingId = buildingId;
    }
    @Column(name = "unitId")
    public Integer getUnitId(){
        return this.unitId;
    }
    public void setUnitId(Integer unitId){
        this.unitId = unitId;
    }
    @Column(name = "floorId")
    public Integer getFloorId(){
        return this.floorId;
    }
    public void setFloorId(Integer floorId){
        this.floorId = floorId;
    }
    @Column(name = "resourceId")
    public Integer getResourceId(){
        return this.resourceId;
    }
    public void setResourceId(Integer resourceId){
        this.resourceId = resourceId;
    }
    @Column(name = "imageType")
    public Integer getImageType(){
        return this.imageType;
    }
    public void setImageType(Integer imageType){
        this.imageType = imageType;
    }
    @Column(name = "coordinate")
    public String getCoordinate(){
        return this.coordinate;
    }
    public void setCoordinate(String coordinate){
        this.coordinate = coordinate;
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
