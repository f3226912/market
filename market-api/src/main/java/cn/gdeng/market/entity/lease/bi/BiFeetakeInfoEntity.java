package cn.gdeng.market.entity.lease.bi;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "bi_feetake_info")
public class BiFeetakeInfoEntity  implements java.io.Serializable{

	private static final long serialVersionUID = -7154303605836894428L;

	/**
     *id
     */
    private Long id;

    /**
     *资源id
     */
    private String resourceTypeId;

    /**
     *市场id
     */
    private Integer marketId;

    /**
     *区域id
     */
    private Integer areaId;

    /**
     *费项id
     */
    private String expid;

    /**
     *应收款
     */
    private String accountPayable;

    /**
     *已收款
     */
    private String accountPayed;

    /**
     *优惠金额
     */
    private String DiscountAmount;

    /**
     *统计日期
     */
    private Integer dataDate;

    /**
     *数据时间
     */
    private Date dataTime;

    @Id
    @Column(name = "id")
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "resourceTypeId")
    public String getResourceTypeId(){

        return this.resourceTypeId;
    }
    public void setResourceTypeId(String resourceTypeId){

        this.resourceTypeId = resourceTypeId;
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
    @Column(name = "expid")
    public String getExpid(){

        return this.expid;
    }
    public void setExpid(String expid){

        this.expid = expid;
    }
    @Column(name = "accountPayable")
    public String getAccountPayable(){

        return this.accountPayable;
    }
    public void setAccountPayable(String accountPayable){

        this.accountPayable = accountPayable;
    }
    @Column(name = "accountPayed")
    public String getAccountPayed(){

        return this.accountPayed;
    }
    public void setAccountPayed(String accountPayed){

        this.accountPayed = accountPayed;
    }
    @Column(name = "DiscountAmount")
    public String getDiscountAmount(){

        return this.DiscountAmount;
    }
    public void setDiscountAmount(String DiscountAmount){

        this.DiscountAmount = DiscountAmount;
    }
    @Column(name = "dataDate")
    public Integer getDataDate(){

        return this.dataDate;
    }
    public void setDataDate(Integer dataDate){

        this.dataDate = dataDate;
    }
    @Column(name = "dataTime")
    public Date getDataTime(){

        return this.dataTime;
    }
    public void setDataTime(Date dataTime){

        this.dataTime = dataTime;
    }
}

