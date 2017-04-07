package cn.gdeng.market.entity.lease.resources;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "market_area")
public class MarketAreaEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8992306699687362241L;

	/**
     *
     */
    private Integer id;
    /**
     *市场id
     */
    private Integer marketId;
    /**
     *区域名称
     */
    private String name;
    /**
     *区域编号
     */
    private String code;
    /**
     * 区域图片地址
     */
    private String areaImage;
    /**
     *状态: 默认 1  正常  0 禁用
     */
    private Integer status;
    /**
     *排序字段
     */
    private Integer sort;
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
    @Column(name = "code")
    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }
    @Column(name = "sort")
    public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
    @Column(name = "areaImage")
    public String getAreaImage(){
        return this.areaImage;
    }
    public void setAreaImage(String areaImage){
        this.areaImage = areaImage;
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
