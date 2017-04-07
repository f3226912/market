package cn.gdeng.market.entity.admin;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "sys_user_message")
public class SysUserMessageEntity implements java.io.Serializable {
	private static final long serialVersionUID = -4685091721240490127L;

	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 消息ID
	 */
	private Integer messageId;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 创建用户ID
	 */
	private String createUserID;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 是否读取(1未读2已读)
	 */
	private String isread;

	/**
	 * 是否删除(1未删除2已删除)
	 */
	private String isdel;

	@Id
	@Column(name = "id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "messageId")
	public Integer getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	@Column(name = "userId")
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "createUserID")
	public String getCreateUserID() {
		return this.createUserID;
	}

	public void setCreateUserID(String createUserID) {
		this.createUserID = createUserID;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "isread")
	public String getIsread() {
		return this.isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}

	@Column(name = "isdel")
	public String getIsdel() {
		return this.isdel;
	}

	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}

}
