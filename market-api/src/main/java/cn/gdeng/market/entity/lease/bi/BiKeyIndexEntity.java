package cn.gdeng.market.entity.lease.bi;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "bi_key_index")
public class BiKeyIndexEntity  implements java.io.Serializable{

	private static final long serialVersionUID = 1733902468795770812L;

	/**
     *id
     */
    private Long id;

    /**
     *待租面积
     */
    private String stayArea;

    /**
     *已租面积
     */
    private String alreadyArea;

    /**
     *未放租面积
     */
    private String unrentableArea;

    /**
     *应付金额
     */
    private String accountPayable;

    /**
     *实付金额
     */
    private String accountPayed;

  
    /**
     *资源类型id
     */
    private Integer resourceTypeId;

    /**
     *时间
     */
    private Long keydataTime;

    /**
     *市场
     */
    private Integer marketId;

    /**
     *数据更新时间
     */
    private Date dataTime;

    /**
     *当月应付金额
     */
    private String accountPayableMon;

    /**
     *当月实付金额
     */
    private String accountPayedMon;

    @Id
    @Column(name = "id")
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "stayArea")
    public String getStayArea(){

        return this.stayArea;
    }
    public void setStayArea(String stayArea){

        this.stayArea = stayArea;
    }
    @Column(name = "alreadyArea")
    public String getAlreadyArea(){

        return this.alreadyArea;
    }
    public void setAlreadyArea(String alreadyArea){

        this.alreadyArea = alreadyArea;
    }
    @Column(name = "unrentableArea")
    public String getUnrentableArea(){

        return this.unrentableArea;
    }
    public void setUnrentableArea(String unrentableArea){

        this.unrentableArea = unrentableArea;
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
    
    @Column(name = "resourceTypeId")
    public Integer getResourceTypeId(){

        return this.resourceTypeId;
    }
    public void setResourceTypeId(Integer resourceTypeId){

        this.resourceTypeId = resourceTypeId;
    }
    @Column(name = "keydataTime")
    public Long getKeydataTime(){

        return this.keydataTime;
    }
    public void setKeydataTime(Long keydataTime){

        this.keydataTime = keydataTime;
    }
    @Column(name = "marketId")
    public Integer getMarketId(){

        return this.marketId;
    }
    public void setMarketId(Integer marketId){

        this.marketId = marketId;
    }
    @Column(name = "dataTime")
    public Date getDataTime(){

        return this.dataTime;
    }
    public void setDataTime(Date dataTime){

        this.dataTime = dataTime;
    }
    @Column(name = "accountPayableMon")
    public String getAccountPayableMon(){

        return this.accountPayableMon;
    }
    public void setAccountPayableMon(String accountPayableMon){

        this.accountPayableMon = accountPayableMon;
    }
    @Column(name = "accountPayedMon")
    public String getAccountPayedMon(){

        return this.accountPayedMon;
    }
    public void setAccountPayedMon(String accountPayedMon){

        this.accountPayedMon = accountPayedMon;
    }
}

