package cn.gdeng.market.entity.admin;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "sys_post_function")
public class SysPostFunctionEntity implements java.io.Serializable {
	private static final long serialVersionUID = -5784814821003409901L;

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 岗位ID
	 */
	private Long postId;

	/**
	 * 权限ID
	 */
	private Long authId;

	/**
	 * 权限类型 1菜单 2按钮
	 */
	private String type;

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
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "postId")
	public Long getPostId() {
		return this.postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	@Column(name = "authId")
	public Long getAuthId() {
		return this.authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	@Column(name = "type")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

}
