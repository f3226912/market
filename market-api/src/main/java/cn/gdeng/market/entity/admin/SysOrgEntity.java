package cn.gdeng.market.entity.admin;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "sys_org")
public class SysOrgEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1895378733136471806L;

	/**
	 * 组织ID
	 */
	private Integer id;

	/**
	 * 组织类型 1公司 2部门 3市场
	 */
	private String type;

	/**
	 * 组织简称
	 */
	private String shortName;

	/**
	 * 组织全称
	 */
	private String fullName;

	/**
	 * 上级组织ID
	 */
	private Integer parentId;
	
	/**
	 * 集团ID
	 */
	private Integer topId;

	/**
	 * 级别
	 */
	private Integer level;

	/**
	 * 状态 1 正常 2 禁用
	 */
	private String status;

	/**
	 * 备注
	 */
	private String remark;

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
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "type")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "shortName")
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "fullName")
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "parentId")
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name = "topId")
	public Integer getTopId() {
		return topId;
	}

	public void setTopId(Integer topId) {
		this.topId = topId;
	}
	
	

}
