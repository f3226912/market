package cn.gdeng.market.entity.lease.settings;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * 合同提醒设置
 * @author kwang
 * 2016-10-17
 */
@Entity(name = "sys_contract_remind_setting")
public class SysContractRemindSettingEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6015840305320970153L;

	/**
     *
     */
    private Integer id;

    /**
     *市场id
     */
    private Integer marketId;

    /**
     *公司id
     */
    private Integer orgId;

    /**
     *提醒类型: 1 合同到期提醒  2  合同收取租金提醒
     */
    private Integer type;

    /**
     *提醒提前的时间
     */
    private Integer remindTime;

    /**
     *提醒时间类型  1  天  2  月
     */
    private Integer remindTimeType;

    /**
     *提醒人
     */
    private String remindPerson;

    /**
     *提醒岗位
     */
    private String remindPost;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private String createUserId;

    /**
     *
     */
    private Date updateTime;

    /**
     *
     */
    private String updateUserId;

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
    @Column(name = "orgId")
    public Integer getOrgId(){

        return this.orgId;
    }
    public void setOrgId(Integer orgId){

        this.orgId = orgId;
    }
    @Column(name = "type")
    public Integer getType(){

        return this.type;
    }
    public void setType(Integer type){

        this.type = type;
    }
    @Column(name = "remindTime")
    public Integer getRemindTime(){

        return this.remindTime;
    }
    public void setRemindTime(Integer remindTime){

        this.remindTime = remindTime;
    }
    @Column(name = "remindTimeType")
    public Integer getRemindTimeType(){

        return this.remindTimeType;
    }
    public void setRemindTimeType(Integer remindTimeType){

        this.remindTimeType = remindTimeType;
    }
    @Column(name = "remindPerson")
    public String getRemindPerson(){

        return this.remindPerson;
    }
    public void setRemindPerson(String remindPerson){

        this.remindPerson = remindPerson;
    }
    @Column(name = "remindPost")
    public String getRemindPost(){

        return this.remindPost;
    }
    public void setRemindPost(String remindPost){

        this.remindPost = remindPost;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "createUserId")
    public String getCreateUserId(){

        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){

        this.createUserId = createUserId;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){

        this.updateUserId = updateUserId;
    }
}

