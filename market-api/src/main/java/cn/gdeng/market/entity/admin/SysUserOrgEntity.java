package cn.gdeng.market.entity.admin;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "sys_user_org")
public class SysUserOrgEntity implements java.io.Serializable {
	private static final long serialVersionUID = 3022901645565419376L;

	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 组织ID
	 */
	private Integer orgId;

	/**
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * 组织类型 1公司 2部门 3市场 4 集团
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

	
	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Id
	@Column(name = "id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "orgId")
	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "userId")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
