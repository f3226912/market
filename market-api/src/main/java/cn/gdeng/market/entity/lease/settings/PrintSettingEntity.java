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

     *市场ID
     */
    private Integer marketId;

     *套打名称
     */
    private String printName;

     *模板D
     */
    private Integer templateId;

     *应用业务 0 租赁合同 1 合同变更 2 合同结算 3 合同付款
     */
    private Byte bizType;

     *是否删除(0:未删除;1:已删除)
     */
    private Byte isDeleted;

     *创建人员ID
     */
    private String createUserId;

     *创建时间
     */
    private Date createTime;

     *修改人员ID
     */
    private String updateUserId;

     *修改时间
     */
    private Date updateTime;

    @Column(name = "id")
    public Integer getId(){

    }
    public void setId(Integer id){

    }
    @Column(name = "marketId")
    public Integer getMarketId(){

    }
    public void setMarketId(Integer marketId){

    }
    @Column(name = "printName")
    public String getPrintName(){

    }
    public void setPrintName(String printName){

    }
    @Column(name = "templateId")
    public Integer getTemplateId(){

    }
    public void setTemplateId(Integer templateId){

    }
    @Column(name = "bizType")
    public Byte getBizType(){

    }
    public void setBizType(Byte bizType){

    }
    @Column(name = "isDeleted")
    public Byte getIsDeleted(){

    }
    public void setIsDeleted(Byte isDeleted){

    }
    @Column(name = "createUserId")
    public String getCreateUserId(){

    }
    public void setCreateUserId(String createUserId){

    }
    @Column(name = "createTime")
    public Date getCreateTime(){

    }
    public void setCreateTime(Date createTime){

    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

    }
    public void setUpdateUserId(String updateUserId){

    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

    }
    public void setUpdateTime(Date updateTime){

    }
}