package cn.gdeng.market.entity.lease.settings;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 计费标准
 * @author jiangyanlis
 *
 */
@Entity(name = "market_expenditure_standard")
public class MarketExpenditureStandardEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2325581840469577321L;

	/**
     *
     */
    private Integer id;
    /**
     *费项id
            
     */
    private Integer expId;
    /**
     *计费标准名称
     */
    private String name;
    /**
     *计费标准编码
     */
    private String code;
    /**
     *收租模式  1 指定金额  2 手工录入  3 建筑面积  4  套内面积  5 可出租面积
     */
    private Integer rentMode;
    /**
     *收费金额
     */
    private Double chargeAmount;
    /**
     *计费单位  1 元/日  2 元/月  3 元/季  4  元/半季  5 元/年
     */
    private Integer chargeUnit;
    /**
     *计费单价
     */
    private Double chargeUnitPrice;
    /**
     *损耗率
     */
    private Double wastageRate;
    /**
     *是否分段计费  默认  1  是  0  否
     */
    private Integer sectionalCharge;
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
    @Column(name = "rentMode")
    public Integer getRentMode(){
        return this.rentMode;
    }
    public void setRentMode(Integer rentMode){
        this.rentMode = rentMode;
    }
    @Column(name = "chargeAmount")
    public Double getChargeAmount(){
        return this.chargeAmount;
    }
    public void setChargeAmount(Double chargeAmount){
        this.chargeAmount = chargeAmount;
    }
    @Column(name = "chargeUnit")
    public Integer getChargeUnit(){
        return this.chargeUnit;
    }
    public void setChargeUnit(Integer chargeUnit){
        this.chargeUnit = chargeUnit;
    }
    @Column(name = "chargeUnitPrice")
    public Double getChargeUnitPrice(){
        return this.chargeUnitPrice;
    }
    public void setChargeUnitPrice(Double chargeUnitPrice){
        this.chargeUnitPrice = chargeUnitPrice;
    }
    @Column(name = "wastageRate")
    public Double getWastageRate(){
        return this.wastageRate;
    }
    public void setWastageRate(Double wastageRate){
        this.wastageRate = wastageRate;
    }
    @Column(name = "sectionalCharge")
    public Integer getSectionalCharge(){
        return this.sectionalCharge;
    }
    public void setSectionalCharge(Integer sectionalCharge){
        this.sectionalCharge = sectionalCharge;
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
