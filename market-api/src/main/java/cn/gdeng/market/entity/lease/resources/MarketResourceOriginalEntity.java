package cn.gdeng.market.entity.lease.resources;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "market_resource_original")
public class MarketResourceOriginalEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4273755034615092803L;

	/**
     *
     */
    private Integer id;
    /**
     *资源编码
     */
    private String code;
    /**
     *资源名称
     */
    private String name;
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
    private Integer budingId;
    /**
     *楼层id
     */
    private Integer floorId;
    /**
     *单元id
     */
    private Integer unitId;
    /**
     *资源类型Id
     */
    private Integer resourceTypeId;
    /**
     *建筑面积
     */
    private Double builtArea;
    /**
     *套内面积
     */
    private Double innerArea;
    /**
     *可出租面积
     */
    private Double leaseArea;
    /**
     *租赁状态:  默认 1 未生效  2 已租  3  待租  4 预租  5 签约
     */
    private Integer leaseStatus;
    /**
     *收租模式  1 指定金额  2 手工录入  3 建筑面积  4  套内面积  5 可出租面积
     */
    private String rentMode;
    /**
     *生效时间
     */
    private Date leaseValidateTime;
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
    @Column(name = "code")
    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }
    @Column(name = "name")
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
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
    @Column(name = "budingId")
    public Integer getBudingId(){
        return this.budingId;
    }
    public void setBudingId(Integer budingId){
        this.budingId = budingId;
    }
    @Column(name = "floorId")
    public Integer getFloorId(){
        return this.floorId;
    }
    public void setFloorId(Integer floorId){
        this.floorId = floorId;
    }
    @Column(name = "unitId")
    public Integer getUnitId(){
        return this.unitId;
    }
    public void setUnitId(Integer unitId){
        this.unitId = unitId;
    }
    @Column(name = "resourceTypeId")
    public Integer getResourceTypeId(){
        return this.resourceTypeId;
    }
    public void setResourceTypeId(Integer resourceTypeId){
        this.resourceTypeId = resourceTypeId;
    }
    @Column(name = "builtArea")
    public Double getBuiltArea(){
        return this.builtArea;
    }
    public void setBuiltArea(Double builtArea){
        this.builtArea = builtArea;
    }
    @Column(name = "innerArea")
    public Double getInnerArea(){
        return this.innerArea;
    }
    public void setInnerArea(Double innerArea){
        this.innerArea = innerArea;
    }
    @Column(name = "leaseArea")
    public Double getLeaseArea(){
        return this.leaseArea;
    }
    public void setLeaseArea(Double leaseArea){
        this.leaseArea = leaseArea;
    }
    @Column(name = "leaseStatus")
    public Integer getLeaseStatus(){
        return this.leaseStatus;
    }
    public void setLeaseStatus(Integer leaseStatus){
        this.leaseStatus = leaseStatus;
    }
    @Column(name = "rentMode")
    public String getRentMode(){
        return this.rentMode;
    }
    public void setRentMode(String rentMode){
        this.rentMode = rentMode;
    }
    @Column(name = "leaseValidateTime")
    public Date getLeaseValidateTime(){
        return this.leaseValidateTime;
    }
    public void setLeaseValidateTime(Date leaseValidateTime){
        this.leaseValidateTime = leaseValidateTime;
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
