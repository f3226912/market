package cn.gdeng.market.entity.lease.settings;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "print_setting")
public class PrintSettingEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4032972265852220380L;

	/**
     *主键id
     */
    private Integer id;
    /**
     *市场ID
     */
    private Integer marketId;
    /**
     *套打名称
     */
    private String printName;
    /**
     *模板D
     */
    private Integer templateId;
    /**
     *应用业务 0 租赁合同 1 合同变更 2 合同结算 3 合同付款
     */
    private Byte bizType;
    /**
     *是否删除(0:未删除;1:已删除)
     */
    private Byte isDeleted;
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
    @Column(name = "printName")
    public String getPrintName(){
        return this.printName;
    }
    public void setPrintName(String printName){
        this.printName = printName;
    }
    @Column(name = "templateId")
    public Integer getTemplateId(){
        return this.templateId;
    }
    public void setTemplateId(Integer templateId){
        this.templateId = templateId;
    }
    @Column(name = "bizType")
    public Byte getBizType(){
        return this.bizType;
    }
    public void setBizType(Byte bizType){
        this.bizType = bizType;
    }
    @Column(name = "isDeleted")
    public Byte getIsDeleted(){
        return this.isDeleted;
    }
    public void setIsDeleted(Byte isDeleted){
        this.isDeleted = isDeleted;
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
