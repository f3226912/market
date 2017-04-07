package cn.gdeng.market.entity.lease.settings;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * 审批模式设置表
 * @author kwang
 *
 */
@Entity(name = "sys_workflow_setting")
public class SysWorkflowSettingEntity  implements java.io.Serializable{
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
     *提醒类型: 1 合同审批模式  2  合同变更模式  3 合同结算审批模式
     */
    private Integer type;

    /**
     *审批模式  1  工作流审批   2 线下审批
     */
    private Integer workType;

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
    @Column(name = "workType")
    public Integer getWorkType(){

        return this.workType;
    }
    public void setWorkType(Integer workType){

        this.workType = workType;
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

