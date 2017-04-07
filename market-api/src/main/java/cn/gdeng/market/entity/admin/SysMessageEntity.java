package cn.gdeng.market.entity.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**系统消息实体
 * 
 * @author wjguo
 *
 * datetime:2016年10月13日 上午11:41:01
 */
@Entity(name = "sys_message")
public class SysMessageEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -9063726526832265924L;

	/**
     *
     */
    private Integer id;

    /**
     *消息类型
     */
    private String messageType;

    /**
     *消息发送者
     */
    private String sender;

    /**
     *消息接收者
     */
    private String receiver;

    /**
     *发送时间
     */
    private Date sendTime;

    /**
     *接收时间
     */
    private Date receiveTime;

    /**
     *消息内容
     */
    private String messageContent;

    /**
     *消息标题
     */
    private String messageTitile;

    /**
     *是否已经读  1  已读  0  未读  默认
     */
    private Integer hasRead;

    /**
     *业务id
     */
    private Integer businessId;

    /**
     *业务json数据
     */
    private String businessJson;

    /**
     *消息有效截止日期  公告用
     */
    private Date validEndTime;

    /**
     *消息有效开始时间  公告用
     */
    private Date validBeginTime;

    /**
     * 状态:默认  1 正常  0 删除
     */
    private Integer status;

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

    /**
     *跳转的url地址，如果无需跳转，则为null即可。跳转地址会带上当前消息的id作为请求参数。
     */
    private String forwardUrl;

    @Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "messageType")
    public String getMessageType(){

        return this.messageType;
    }
    public void setMessageType(String messageType){

        this.messageType = messageType;
    }
    @Column(name = "sender")
    public String getSender(){

        return this.sender;
    }
    public void setSender(String sender){

        this.sender = sender;
    }
    @Column(name = "receiver")
    public String getReceiver(){

        return this.receiver;
    }
    public void setReceiver(String receiver){

        this.receiver = receiver;
    }
    @Column(name = "sendTime")
    public Date getSendTime(){

        return this.sendTime;
    }
    public void setSendTime(Date sendTime){

        this.sendTime = sendTime;
    }
    @Column(name = "receiveTime")
    public Date getReceiveTime(){

        return this.receiveTime;
    }
    public void setReceiveTime(Date receiveTime){

        this.receiveTime = receiveTime;
    }
    @Column(name = "messageContent")
    public String getMessageContent(){

        return this.messageContent;
    }
    public void setMessageContent(String messageContent){

        this.messageContent = messageContent;
    }
    @Column(name = "messageTitile")
    public String getMessageTitile(){

        return this.messageTitile;
    }
    public void setMessageTitile(String messageTitile){

        this.messageTitile = messageTitile;
    }
    @Column(name = "hasRead")
    public Integer getHasRead(){

        return this.hasRead;
    }
    public void setHasRead(Integer hasRead){

        this.hasRead = hasRead;
    }
    @Column(name = "businessId")
    public Integer getBusinessId(){

        return this.businessId;
    }
    public void setBusinessId(Integer businessId){

        this.businessId = businessId;
    }
    @Column(name = "businessJson")
    public String getBusinessJson(){

        return this.businessJson;
    }
    public void setBusinessJson(String businessJson){

        this.businessJson = businessJson;
    }
    @Column(name = "validEndTime")
    public Date getValidEndTime(){

        return this.validEndTime;
    }
    public void setValidEndTime(Date validEndTime){

        this.validEndTime = validEndTime;
    }
    @Column(name = "validBeginTime")
    public Date getValidBeginTime(){

        return this.validBeginTime;
    }
    public void setValidBeginTime(Date validBeginTime){

        this.validBeginTime = validBeginTime;
    }
    @Column(name = "status")
    public Integer getStatus(){

        return this.status;
    }
    public void setStatus(Integer status){

        this.status = status;
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
    @Column(name = "forwardUrl")
    public String getForwardUrl(){

        return this.forwardUrl;
    }
    public void setForwardUrl(String forwardUrl){

        this.forwardUrl = forwardUrl;
    }
}

