package cn.gdeng.market.entity.lease.settings;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "market")
public class MarketEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 9040722704658314100L;

	/**
     *
     */
    private Integer id;
    /**
     *组织id
     */
    private Integer orgId;
    /**
     *市场名称
     */
    private String name;
    /**
     *市场编号
     */
    private String code;
    /**
     *市场名称简写
     */
    private String nameShort;
    /**
     *开业时间
     */
    private Date openTime;
    /**
     *市场状态:  默认 1 运营中 2 筹备中 3 关闭
     */
    private Integer marketStatus;
    /**
     *省id
     */
    private Integer provinceId;
    /**
     *市id
     */
    private Integer cityId;
    /**
     *区id
     */
    private Integer areaId;
    /**
     *省市区（用/隔开）
     */
    private String pca;
    /**
     *地址
     */
    private String address;
    /**
     *状态:  默认 1 正常  0 删除
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
    
    /**
    *
    */
    private String marketImage;
    @Id
    @Column(name = "id")
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    @Column(name = "orgId")
    public Integer getOrgId(){
        return this.orgId;
    }
    public void setOrgId(Integer orgId){
        this.orgId = orgId;
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
    @Column(name = "nameShort")
    public String getNameShort(){
        return this.nameShort;
    }
    public void setNameShort(String nameShort){
        this.nameShort = nameShort;
    }
    @Column(name = "openTime")
    public Date getOpenTime(){
        return this.openTime;
    }
    public void setOpenTime(Date openTime){
        this.openTime = openTime;
    }
    @Column(name = "marketStatus")
    public Integer getMarketStatus(){
        return this.marketStatus;
    }
    public void setMarketStatus(Integer marketStatus){
        this.marketStatus = marketStatus;
    }
    @Column(name = "provinceId")
    public Integer getProvinceId(){
        return this.provinceId;
    }
    public void setProvinceId(Integer provinceId){
        this.provinceId = provinceId;
    }
    @Column(name = "cityId")
    public Integer getCityId(){
        return this.cityId;
    }
    public void setCityId(Integer cityId){
        this.cityId = cityId;
    }
    @Column(name = "areaId")
    public Integer getAreaId(){
        return this.areaId;
    }
    public void setAreaId(Integer areaId){
        this.areaId = areaId;
    }
    @Column(name = "pca")
    public String getPca(){
        return this.pca;
    }
    public void setPca(String pca){
        this.pca = pca;
    }
    @Column(name = "address")
    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address = address;
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
	
    @Column(name = "marketImage")
    public String getMarketImage() {
		return marketImage;
	}
	public void setMarketImage(String marketImage) {
		this.marketImage = marketImage;
	}
    
}
