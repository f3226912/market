package cn.gdeng.market.entity.lease.resources;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "market_sectionalcharge")
public class MarketSectionalChargeEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3390726083345907148L;

	/**
     *
     */
    private Integer id;
    /**
     *计费标准id
     */
    private Integer expStandardId;
    /**
     *计费单价
     */
    private Double chargeUnitPrice;
    /**
     *上限值
     */
    private Double upValue;
    /**
     *下限值
     */
    private Double downValue;
    /**
     *状态:  默认  1 正常  0  删除
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
    @Column(name = "expStandardId")
    public Integer getExpStandardId(){
        return this.expStandardId;
    }
    public void setExpStandardId(Integer expStandardId){
        this.expStandardId = expStandardId;
    }
    @Column(name = "chargeUnitPrice")
    public Double getChargeUnitPrice(){
        return this.chargeUnitPrice;
    }
    public void setChargeUnitPrice(Double chargeUnitPrice){
        this.chargeUnitPrice = chargeUnitPrice;
    }
    @Column(name = "upValue")
    public Double getUpValue(){
        return this.upValue;
    }
    public void setUpValue(Double upValue){
        this.upValue = upValue;
    }
    @Column(name = "downValue")
    public Double getDownValue(){
        return this.downValue;
    }
    public void setDownValue(Double downValue){
        this.downValue = downValue;
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
